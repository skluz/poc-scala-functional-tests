package com.funtis.commons.perf

import io.gatling.http.protocol.{HttpProtocol, HttpProtocolBuilder}

/**
  * Created by Sławomir Kluz on 13/10/2017.
  */
trait BaseHttpRequests {
  val protocol: HttpProtocolBuilder
}
