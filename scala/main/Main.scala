package chipyard.example

import chisel3._
import freechips.rocketchip.config.Parameters
import freechips.rocketchip.diplomacy._

import java.io.File
import java.io.FileWriter


/**
 * An object extending App to generate the Verilog code.
 */
object Main {
  def main(args: Array[String]): Unit = {
    //(new chisel3.stage.ChiselStage).execute(args, Seq(ChiselGeneratorAnnotation(() => LazyModule(new MyWrapper()(Parameters.empty)).module)))

    val verilog = (new chisel3.stage.ChiselStage).emitVerilog(
                   LazyModule(new MyAxisWrapper()(Parameters.empty)).module
    )
    //println(s"```verilog\n$verilog```")

    val fileWriter = new FileWriter(new File("./gen/gen.v"))
    fileWriter.write(verilog)
    fileWriter.close()

  }
}

