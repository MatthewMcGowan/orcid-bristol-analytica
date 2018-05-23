import spray.json.JsValue
import spray.json._
import DefaultJsonProtocol._

object OrcidJvalue {
  def hasWorks(json: JsValue): Boolean = json
    .asJsObject.fields("orcid-profile")
    .asJsObject.fields("orcid-activities")
    .asJsObject.fields("orcid-works").toString() != "null"

  def getUserDetails(json: JsValue): UserDetails = {
    val profile = json.asJsObject.fields("orcid-profile")

    val path = profile
      .asJsObject.fields("orcid-identifier")
      .asJsObject.fields("path").convertTo[String]

    val name = {
      val personalDetails = profile
        .asJsObject.fields("orcid-bio")
        .asJsObject.fields("personal-details")

      val first = personalDetails
        .asJsObject.fields("given-names")
        .asJsObject.fields("value").convertTo[String]

      val second = personalDetails
        .asJsObject.fields("family-name")
        .asJsObject.fields("value").convertTo[String]

      first + " " + second
    }

    val workCount = profile
      .asJsObject.fields("orcid-activities")
      .asJsObject.fields("orcid-works")
      .convertTo[Array[String]].length

    UserDetails(path, name, workCount)
  }
}
