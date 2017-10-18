package com.funtis.commons.mock

import javax.servlet.ServletContext

import com.funtis.commons.mock.servlet.BaseServlet
import com.typesafe.scalalogging.LazyLogging
import org.eclipse.jetty.server.{Server, ServerConnector}
import org.eclipse.jetty.servlet.DefaultServlet
import org.eclipse.jetty.webapp.WebAppContext
import org.scalatra.LifeCycle
import org.scalatra.servlet.ScalatraListener

class MockServer(port: Int, interface: String, resourceBase: String, servlets: Seq[BaseServlet]) extends LazyLogging {

  private var server: Server = _
  private var realPort: Int = _

  def start() = {
    server = new Server()
    val connector = new ServerConnector(server)
    connector.setPort(port)
    connector.setHost(interface)
    server.addConnector(connector)
    val context = new WebAppContext()
    context.setContextPath("/")
    context.addEventListener(new ScalatraListener())
    context.addServlet(classOf[DefaultServlet], "/")
    context.setResourceBase(resourceBase)
    context.setAttribute("servlets", servlets)
    context.setInitParameter(ScalatraListener.LifeCycleKey, "com.funtis.commons.mock.Bootstrap")
    server.setHandler(context)
    server.start()
    realPort = connector.getLocalPort
    server.getConnectors.foreach(c => {
      logger.info(s"MockServer started: http://${c.asInstanceOf[ServerConnector].getHost}:${c.asInstanceOf[ServerConnector].getLocalPort}/")
    })
  }

  def stop(): Unit = {
    server.stop()
    logger.info(s"MockServer stopped")
  }

  def getRealPort() = realPort

}

class Bootstrap extends LifeCycle {

  override def init(context: ServletContext) {
    context.getAttribute("servlets").asInstanceOf[Seq[BaseServlet]].foreach(s => {
      context.mount(s, "/api" + s.getUrlPattern())
    })
  }

}