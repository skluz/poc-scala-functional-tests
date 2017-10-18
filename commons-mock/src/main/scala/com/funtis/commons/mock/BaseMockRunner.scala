package com.funtis.commons.mock

import com.funtis.commons.mock.servlet.{BaseServlet, StaticFilesServlet}
import com.typesafe.scalalogging.LazyLogging

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
