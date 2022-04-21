// Top level lazy module
package chipyard.example

import chisel3._
import freechips.rocketchip.diplomacy._
import freechips.rocketchip.config.Parameters

class StreamingPassthroughTop(implicit p: Parameters) extends LazyModule {
  val m1 = LazyModule(new StreamingPassthroughBlock(UInt(4.W)))

  lazy val module = new LazyModuleImp(this) {
    m1.module.in = 3.U
  }
}
