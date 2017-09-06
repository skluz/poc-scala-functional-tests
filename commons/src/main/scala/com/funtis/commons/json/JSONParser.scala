package com.funtis.commons.json

/**
  * Created by Sławomir Kluz on 06/09/2017.
  */
trait JSONParser {
  def toJSON(any: Any): String
  def fromJSON[T](json: String, cls: Class[T]): T
}

object JSONParser {
  val default = jackson
  lazy val jackson = new JSONJacksonParser
}
