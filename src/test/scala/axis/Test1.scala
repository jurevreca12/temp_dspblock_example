package axis

import _root_.chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec
import chiseltest.simulator.WriteVcdAnnotation
import freechips.rocketchip.config.Parameters
import freechips.rocketchip.diplomacy._

class UtilityFunctionsTests extends AnyFlatSpec with ChiselScalatestTester {
    behavior.of("diplomacy")

    it should s"Test diplomatic design" in {
        test(LazyModule(new MyAxisWrapper()(Parameters.empty)).module).withAnnotations(Seq(WriteVcdAnnotation)) { dut =>
            dut.clock.step(10)
        }
    }
}