package chipyard.example


import chisel3._
import chisel3.internal.sourceinfo.SourceInfo
import chisel3.stage.ChiselStage

import freechips.rocketchip.config.{Config, Parameters}
import freechips.rocketchip.amba.axis._
import freechips.rocketchip.diplomacy.{SimpleNodeImp, ValName, SourceNode, NexusNode, 
                                       SinkNode, LazyModule, LazyModuleImp, TransferSizes,
                                       SimpleDevice, AddressSet}

class MyAxisController(implicit p: Parameters) extends LazyModule {
  val device = new SimpleDevice("my-device", Seq("tutorial,my-device0"))
  val axisParams = AXISSlaveParameters.v1(name = "axisSlave", supportsSizes = TransferSizes(8,8))
  val axisPortParams = AXISSlavePortParameters.v1(slaves = Seq(axisParams))
  val node = AXISSlaveNode(portParams = Seq(axisPortParams))

  lazy val module = new LazyModuleImp(this) {
      val ins = node.in.unzip._1
      val register = RegInit(UInt(8.W), 0.U)
      register := register + ins(0).bits.data
  }
}

class AXISMaster()(implicit p: Parameters) extends LazyModule {
  val axisMasterParams = AXISMasterParameters.v1(
    name = "axisMaster", emitsSizes = TransferSizes(8, 8)
  )

  val axisMasterPortParams = AXISMasterPortParameters.v1(
    masters = Seq(axisMasterParams),
    beatBytes = Option(8)
  )

  val node = AXISMasterNode(
    portParams = Seq(axisMasterPortParams)
  )

  
  lazy val module = new LazyModuleImp(this) {
    //The dontTouch here preserves the interface so logic is generated
    dontTouch(node.out.head._1)
  }
}


class MyAxisWrapper()(implicit p: Parameters) extends LazyModule {
  val master = LazyModule(new AXISMaster)
  val slave  = LazyModule(new MyAxisController()(Parameters.empty))

  slave.node := master.node 

  lazy val module = new LazyModuleImp(this) {
    //nothing???
  }
}
