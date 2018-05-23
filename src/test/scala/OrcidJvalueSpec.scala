import org.scalatest.FunSpec
import spray.json._
import DefaultJsonProtocol._

class OrcidJvalueSpec extends FunSpec {
  describe("A JsonParsing object") {
    describe("given a valid json object") {
      it("correctly extracts user details") {
        val fileName = "src/test/resources/example.json"
        val fileText = scala.io.Source.fromFile(fileName).mkString
        val json = fileText.parseJson

        val user = OrcidJvalue.getUserDetails(json)

        assert(user.path == "0000-0001-7024-3727")
        assert(user.name == "Jonathan Schuite")
        assert(user.workCount == 0)
      }

      describe("that has no works") {
        it("correctly returns false for 'hasWorks'") {
          val fileName = "src/test/resources/example.json"
          val fileText = scala.io.Source.fromFile(fileName).mkString
          val json = fileText.parseJson

          val result = OrcidJvalue.hasWorks(json)

          assert(!result)
        }
      }
    }
  }
}
