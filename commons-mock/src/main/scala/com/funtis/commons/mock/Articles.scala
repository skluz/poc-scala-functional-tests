package com.funtis.commons.mock

import org.scalatra.ScalatraServlet

class Articles extends ScalatraServlet {

  get("/articles/:id") {
    <b>OK</b>
  }

}
