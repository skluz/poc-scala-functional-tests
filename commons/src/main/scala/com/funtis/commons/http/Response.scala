package com.funtis.commons.http

import com.fasterxml.jackson.core.JsonParseException
import com.funtis.commons.json.JSONParser

import scala.reflect.ClassTag

/**
  * Created by Sławomir Kluz on 11/09/2017.
  */
case class Response(code: Int, status: String, protocol: String, headers: Headers, responseBody: Body, duration: Long, name: String) {

  private val NEW_LINE = System.getProperty("line.separator")

  private var parser: JSONParser = _

  def withParser(parser: JSONParser) = {
    this.parser = parser
    this
  }

  def assertCode(expectedCode: Int*): Response = {
    assert(expectedCode.contains(this.code), s"Status code expected: $expectedCode, actual: $code")
    this
  }

  def body() = new SerializableBody()

  class SerializableBody() {
    def as[T: ClassTag]: T = parser.fromJSON[T](responseBody.bytes)
    def asSeqOf[T: ClassTag]: Seq[T] = parser.fromJSON[Array[T]](responseBody.bytes).toSeq
    def as[T](cls: Class[T]): T = parser.fromJSON[T](responseBody.bytes, cls)
    def as(parametrize: Class[_], parameterClasses: Class[_]) = parser.fromJSON(responseBody.bytes, parametrize, parameterClasses)
  }

  def asCurlResponse(): String = {
    val sb = new StringBuilder()
    val millis = duration.toString
    //sb.append(s">>>> Response [$name] [$millis] ").append(">" * (name.length + millis.length  + 80 - 17 - 3)).append(NEW_LINE)
    sb.append(s"<<<< Response [$name] [$millis ms] ").append(NEW_LINE)
    sb.append(protocol).append(" ").append(code).append(" ").append(status).append(NEW_LINE)
    headers.asList().foreach(header => {
      sb.append(header.name).append(": ").append(header.value).append(NEW_LINE)
    })
    sb.append(bodyToString())
    sb.append(NEW_LINE)
    //sb.append(NEW_LINE).append(s"<<<< Response [$name] [$millis] ").append("<" * (name.length + millis.length  + 80 - 17 - 3)).append(NEW_LINE)
    sb.append(NEW_LINE)
    sb.toString()
  }

  private def bodyToString(): String = {
    responseBody match {
      case null => "[null]"
      case body: Body => {
        try {
          parser.toPrettyJSON(body.asString())
        } catch {
          case e: JsonParseException => body.asString()
          case o => s"[not supported: ${o.getCause}]"
        }
      }
    }
  }

}

