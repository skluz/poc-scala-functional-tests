package com.funtis.commons.mock.servlet

/**
  * Created by Sławomir Kluz on 13/10/2017.
  */
class StaticFilesServlet extends BaseServlet {

  notFound {
    contentType = null
    serveStaticResource() getOrElse halt(404, "Not found.")
  }

}