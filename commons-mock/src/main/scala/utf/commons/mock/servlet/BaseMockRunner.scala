package utf.commons.mock.servlet

import com.typesafe.scalalogging.LazyLogging
import utf.commons.mock.servlet.servlet.{BaseServlet, StaticFilesServlet}

/**
  * Created by Sławomir Kluz on 13/10/2017.
  */
abstract class BaseMockRunner extends LazyLogging {

  val servlets: Seq[BaseServlet]
  val port: Int = 8080
  val interface: String = "0.0.0.0"
  val resourceBase: String = "./src/main/webapp"

  def main(args: Array[String]) {
    new MockServer(port, interface, resourceBase, servlets :+ new StaticFilesServlet()).start()
  }

}
