package com.funtis.commons.http

import com.funtis.commons.json.JSONParser

import scala.reflect.ClassTag

/**
  * Created by Sławomir Kluz on 11/09/2017.
  */
case class Response(code: Int, headers: Headers, body: ResponseBody) {

  def assertCode(expectedCode: Int): Response = {
    assert(this.code == expectedCode, s"Status code expected: $expectedCode, actual: $code")
    this
  }

}

class ResponseBody(body: String) {

  private var parser: JSONParser = _
  def setParser(jSONParser: JSONParser) = {
    this.parser = jSONParser
  }

  def as[T: ClassTag] = parser.fromJSON[T](body)
  def as[T](cls: Class[T]) = parser.fromJSON[T](body, cls)
  def as(parametrize: Class[_], parameterClasses: Class[_]) = parser.fromJSON(body, parametrize, parameterClasses)
}