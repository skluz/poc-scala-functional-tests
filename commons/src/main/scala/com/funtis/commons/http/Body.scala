package com.funtis.commons.http

import java.util

import com.funtis.commons.json.JSONParser

/**
  * Created by Sławomir Kluz on 11/10/2017.
  */
case class Body(bytes: Array[Byte]) {
  def this(string: String) = this(string.getBytes)
  def asString() = new String(bytes)

  override def equals(that: Any) = if(canEqual(that)) java.util.Arrays.equals(bytes, that.asInstanceOf[Body].bytes) else false
}

object Body {

  def createRequestBody(any: Any, jsonParser: JSONParser): Body = {
    any match {
      case null => null
      case any: Any => Body(jsonParser.toJSON(any).getBytes)
    }
  }

}