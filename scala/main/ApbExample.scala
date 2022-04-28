package chipyard.example


import chisel3._
import chisel3.internal.sourceinfo.SourceInfo
import chisel3.stage.ChiselStage
import chisel3.experimental.BundleLiterals._

import freechips.rocketchip.config.{Config, Parameters}
import freechips.rocketchip.regmapper._
import freechips.rocketchip.amba.apb._
import freechips.rocketchip.diplomacy.{SimpleNodeImp, ValName, SourceNode, NexusNode, 
                                       SinkNode, LazyModule, LazyModuleImp, TransferSizes,
                                       SimpleDevice, AddressSet}

class MyDeviceController(implicit p: Parameters) extends LazyModule {
  val device = new SimpleDevice("my-device", Seq("tutorial,my-device0"))
  val node = APBRegisterNode(
    //address = Seq(AddressSet(0x10028000, 0xfff)), (Modified since not in APBRegisterNode)
    address = AddressSet(0x002000, 0xfff),
    //device = device,  (Removed since not in APBRegisterNode)
    beatBytes = 8)

  lazy val module = new LazyModuleImp(this) {
    
    val bigReg = RegInit(0.U(64.W))
    val mediumReg = RegInit(0.U(32.W))
    val smallReg = RegInit(0.U(16.W))

    val tinyReg0 = RegInit(0.U(4.W))
    val tinyReg1 = RegInit(0.U(4.W))

    node.regmap(
      0x00 -> Seq(RegField(64, bigReg)),
      0x08 -> Seq(RegField(32, mediumReg)),
      0x0C -> Seq(RegField(16, smallReg)),
      0x0E -> Seq(
        RegField(4, tinyReg0),
        RegField(4, tinyReg1)))
  }
}

class APBMaster()(implicit p: Parameters) extends LazyModule {
  val apbMasterParameters = APBMasterParameters(
    name = "apbMaster"
  )

  val apbMasterPortParameters = APBMasterPortParameters(
    masters = Seq(apbMasterParameters)
  )

  val node = APBMasterNode(
    portParams = Seq(apbMasterPortParameters)
  )

  
  lazy val module = new LazyModuleImp(this) {
    //The dontTouch here preserves the interface so logic is generated
    dontTouch(node.out.head._1)
  }
}


class MyWrapper()(implicit p: Parameters) extends LazyModule {
  val master = LazyModule(new APBMaster)
  val slave  = LazyModule(new MyDeviceController()(Parameters.empty))

  slave.node := master.node 

  lazy val module = new LazyModuleImp(this) {
    //nothing???
  }
}



/*
class APBMaster()(implicit p: Parameters) extends LazyModule {
  val apbMasterParameters = APBMasterParameters(
    name = "apbMaster"
  )

  val apbMasterPortParameters = APBMasterPortParameters(
    masters = Seq(apbMasterParameters)
  )

  val node = APBMasterNode(
    portParams = Seq(apbMasterPortParameters)
  )

  lazy val module = new LazyModuleImp(this) {
    val io = IO(new Bundle {
      val wtf   = Output(Bool())
      val start = Input(Bool())
    })
        
    val myreg = RegInit(0.U(16.W))
    myreg := myreg + 1.U
    
    val prdata = Wire(UInt(64.W))
    prdata := node.out.head._1.prdata
    //seems to need these things to generate the logic
    io.wtf := node.out.head._1.pready && !(node.out.head._1.prdata === 0.U)

    node.out.head._1.pstrb    := 63.U
    node.out.head._1.pprot    := 0.U
        
    when(myreg(3,0) === 8.U && io.start) {
      node.out.head._1.paddr    := myreg
      node.out.head._1.psel     := true.B
      node.out.head._1.penable  := false.B
      node.out.head._1.pwrite   := true.B
      node.out.head._1.pwdata   := myreg + 1.U
    } .elsewhen(myreg(3,0) === 9.U) {
      node.out.head._1.paddr    := myreg
      node.out.head._1.psel     := true.B
      node.out.head._1.penable  := true.B
      node.out.head._1.pwrite   := true.B
      node.out.head._1.pwdata   := myreg
    } otherwise {
      node.out.head._1.paddr    := 0.U
      node.out.head._1.psel     := false.B
      node.out.head._1.penable  := false.B
      node.out.head._1.pwrite   := false.B
      node.out.head._1.pwdata   := 0.U
    }
    
  }
}

class APBTop()(implicit p: Parameters) extends LazyModule {
  val master = LazyModule(new APBMaster)
  val slave  = LazyModule(new MyDeviceController()(Parameters.empty))

  slave.node := master.node 

  lazy val module = new LazyModuleImp(this) {
    val io = IO(new Bundle {
      val busy = Output(Bool())
      val wtf  = Output(Bool())
      val start = Input(Bool())
    })
    
    io.busy := true.B
    io.wtf  := master.module.io.wtf
    master.module.io.start := io.start
    
  }
}
*/
