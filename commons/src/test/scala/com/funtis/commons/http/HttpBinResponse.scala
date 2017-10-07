package com.funtis.commons.http

/**
  * Created by Sławomir Kluz on 05/10/2017.
  */
case class HttpBinResponse(
  args: Map[String, String],
  headers: Map[String, String],
  origin: String, url: String
)