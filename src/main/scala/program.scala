import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
import spray.json._
import DefaultJsonProtocol._

object program extends App {
  val conf = new SparkConf().setAppName("bristol-analytica")
  val sc = new SparkContext(conf)
  val files = sc.textFile("s3a://orcid-throwaway-20180424/json")

//  val output = files.map(x => x.parseJson)
//      .filter(x => JsonParsing.hasWorks(x))

  val output = files.map(x => x.parseJson)
      .map(x => OrcidJvalue.getUserDetails(x))
      .sortBy(x => x.workCount, ascending = false)

  output.saveAsTextFile("s3a://orcid-throwaway-20180424/output.txt")
}
