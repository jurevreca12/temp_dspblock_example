package chipyard.example

import chipsalliance.rocketchip.config.{Config, Parameters}
import chisel3._
import chisel3.internal.sourceinfo.SourceInfo
import chisel3.stage.ChiselStage
import chisel3.experimental.BundleLiterals._
import freechips.rocketchip.amba.axis._
import freechips.rocketchip.diplomacy.{SimpleNodeImp, ValName, SourceNode, NexusNode, 
                                       SinkNode, LazyModule, LazyModuleImp, TransferSizes}


class AxiSource(implicit p: Parameters) extends LazyModule {
  val axiMasterParams = AXISMasterParameters.v1(name = "axiMaster", emitsSizes=TransferSizes(8,8))
  val axiMasterPortParams = AXISMasterPortParameters.v1(masters = Seq(axiMasterParams), beatBytes = Some(8))
  val streamNode = AXISMasterNode(portParams = Seq(axiMasterPortParams))



  lazy val module = new LazyModuleImp(this) {
    val (outs, _) = streamNode.out.unzip
    outs := (new AXISBundle(new AXISBundleParameters)).Lit()
    // outs.foreach { case(out) => out := 0.U }
  }
}

class AxiStreamIdentity(implicit p: Parameters) extends LazyModule {
  val streamNode = AXISIdentityNode()

  lazy val module = new LazyModuleImp(this) {
    val (ins, _) = streamNode.in.unzip
    val (outs, _) = streamNode.out.unzip
    ins.zip(outs).foreach { case(in, out) => out <> in }
  }
}



class AxiSink(implicit p: Parameters) extends LazyModule {
 val axiSlaveParams = AXISSlaveParameters.v1(name = "axiSlave", supportsSizes=TransferSizes(4,8))
 val axiSlavePortParams = AXISSlavePortParameters.v1(slaves = Seq(axiSlaveParams))
 val streamNode = AXISSlaveNode(portParams = Seq(axiSlavePortParams))

 lazy val module = new LazyModuleImp(this) {
  // empty
 }
}


class AxiExampleTop(implicit p: Parameters) extends LazyModule {
  val master = LazyModule(new AxiSource()(Parameters.empty))
  val identity = LazyModule(new AxiStreamIdentity()(Parameters.empty))
  val slave = LazyModule(new AxiSink()(Parameters.empty))

  identity.streamNode := master.streamNode
  slave.streamNode := identity.streamNode
  
  lazy val module = new LazyModuleImp(this) {
    /*val io = IO(new Bundle{
      val busy = Output(Bool())
    })

    io.busy := true.B*/
  }
}
