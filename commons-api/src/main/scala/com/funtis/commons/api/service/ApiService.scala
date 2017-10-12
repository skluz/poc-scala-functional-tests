package com.funtis.commons.api.service

import com.funtis.commons.oldhttp.RestClient
import com.typesafe.scalalogging.LazyLogging

/**
  * Created by Sławomir Kluz on 04/10/2017.
  */
trait ApiService extends LazyLogging {

  lazy val restClient = new RestClient()

}
