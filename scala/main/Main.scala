/*
 * HEADER: TODO
 *
 * This is the main entry point of the generator. This file read in the Keras 
 * model in the form of a HDF file. Following that it parses the JSON model data
 * given in the HDF attribute.
 */
package chipyard.example

import chisel3._
import freechips.rocketchip.config.Parameters
import freechips.rocketchip.diplomacy.LazyModule
/**
 * An object extending App to generate the Verilog code.
 */
object Main {
  def main(args: Array[String]): Unit = {
    println("asdasdasdAASDASDA")
    implicit val p: Parameters = Parameters.empty
    val lazyModule = LazyModule(new StreamingPassthroughTop)
    (new chisel3.stage.ChiselStage).emitVerilog(lazyModule.module, Array("-td","gen/"))  
  }
}

