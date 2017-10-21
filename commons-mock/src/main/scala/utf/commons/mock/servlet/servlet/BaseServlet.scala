package utf.commons.mock.servlet.servlet

import com.typesafe.scalalogging.LazyLogging
import org.json4s.{DefaultFormats, Formats, Serializer}
import org.scalatra.ScalatraServlet
import org.scalatra.json.JacksonJsonSupport

/**
  * Created by Sławomir Kluz on 13/10/2017.
  */
abstract class BaseServlet(urlPattern: String = "/*", state: MockState = EmptyState()) extends ScalatraServlet with JacksonJsonSupport with LazyLogging {

  protected val customSerializers = Seq.empty[Serializer[_]]
  protected implicit lazy val jsonFormats: Formats = DefaultFormats ++ customSerializers

  def getUrlPattern() = urlPattern

  before() {
    contentType = formats("json")
    var ipAddress = request.getHeader("X-FORWARDED-FOR")
    if (ipAddress == null) ipAddress = request.getRemoteAddr
    logger.info(s"Request: [$ipAddress] ${request.getMethod} ${request.getRequestURI} ${request.body}")
  }

}
