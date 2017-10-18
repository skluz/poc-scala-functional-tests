package com.funtis.commons.json

import com.funtis.commons.json.jackson.JSONJacksonParser
import scala.reflect.ClassTag

/**
  * Created by Sławomir Kluz on 06/09/2017.
  */
trait JSONParser {

  def toJSON(any: Any): String

  def fromJSON[T](json: String, cls: Class[T]): T
  def fromJSON[T](json: Array[Byte], cls: Class[T]): T

  def fromJSON[T: ClassTag](json: String): T
  def fromJSON[T: ClassTag](json: Array[Byte]): T

  def fromJSON[T](json: String, parametrized: Class[_], parameterClasses: Class[_]): T
  def fromJSON[T](json: Array[Byte], parametrized: Class[_], parameterClasses: Class[_]): T
}

object JSONParser {
  lazy val default: JSONParser = jackson
  lazy val jackson: JSONParser = new JSONJacksonParser
}
