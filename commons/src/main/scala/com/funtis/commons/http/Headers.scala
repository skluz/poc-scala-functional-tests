package com.funtis.commons.http

/**
  * Created by Sławomir Kluz on 11/09/2017.
  */
case class Headers(headers: Header*) {
  def asMap(): Map[String, String] = headers.map(h => h.name -> h.value).toMap
}
