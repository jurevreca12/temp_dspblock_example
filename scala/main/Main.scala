/*
 * HEADER: TODO
 *
 * This is the main entry point of the generator. This file read in the Keras 
 * model in the form of a HDF file. Following that it parses the JSON model data
 * given in the HDF attribute.
 */
package chipyard.example

import chisel3._
import dspblocks._
import freechips.rocketchip.config.Parameters
import freechips.rocketchip.diplomacy._
import java.io.File
import java.io.FileWriter

/**
 * An object extending App to generate the Verilog code.
 */
object Main {
  def main(args: Array[String]): Unit = {
    //println("asdasdasdAASDASDA")
    //implicit val valName = ValName("Axi4Test")
    //println((new chisel3.stage.ChiselStage).emitVerilog(() => LazyModule(new AXI4FIRBlock(1, 8)(Parameters.empty) with AXI4StandaloneBlock).module))
    //(new chisel3.stage.ChiselStage).emitVerilog(LazyModule(new AXI4FIRBlock(1, 8)(Parameters.empty) with AXI4StandaloneBlock).module, 
    //                                            Array("-td","gen/"))
    
    //(new chisel3.stage.ChiselStage).emitVerilog(LazyModule(new Adder()), Array("-td", "gen/"))

    //implicit val p: Parameters = Parameters.empty
    //val lazyModule = LazyModule(new StreamingPassthroughTop)
    //(new chisel3.stage.ChiselStage).emitVerilog(lazyModule.module, Array("-td","gen/"))  
    
    val verilog = (new chisel3.stage.ChiselStage).emitVerilog(
                   LazyModule(new AxiExampleTop()(Parameters.empty)).module
    )
    //println(s"```verilog\n$verilog```")
    //
    /*val fileWriter = new FileWriter(new File("./gen/gen.v"))
    fileWriter.write(verilog)
    fileWriter.close()*/
  }
}

