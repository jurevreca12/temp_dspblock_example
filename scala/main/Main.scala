package chipyard.example

import chisel3._
import freechips.rocketchip.config.Parameters
import freechips.rocketchip.diplomacy._

/**
 * An object extending App to generate the Verilog code.
 */
object Main {
  def main(args: Array[String]): Unit = {
    val verilog = (new chisel3.stage.ChiselStage).emitVerilog(
                   LazyModule(new AxiExampleTop()(Parameters.empty)).module
    )
    println(s"```verilog\n$verilog```")
  }
}

