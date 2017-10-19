package com.funtis.commons.http.model

/**
  * Created by Sławomir Kluz on 05/10/2017.
  */
case class HttpBinResponse(
  args: Map[String, String],
  data: Option[String],
  files: Option[Any],
  form: Option[Any],
  headers: Map[String, String],
  json: Option[Any],
  method: Option[String],
  origin: String,
  url: String
)