package utf.commons.http

/**
  * Created by Sławomir Kluz 04/10/2017.
  */
case class Configuration(
  connectionTimeout: Int = 5000,
  socketTimeout: Int = 5000,
  followRedirects: Boolean = true
)

object Configuration {
  val default: Configuration = Configuration()
}
