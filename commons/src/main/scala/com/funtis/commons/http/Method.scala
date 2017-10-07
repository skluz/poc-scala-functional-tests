package com.funtis.commons.http

/**
  * Created by Sławomir Kluz on 04/10/2017.
  */
object Method extends Enumeration {
  type Method = Value
  val GET, POST, PUT, PATCH, DELETE = Value
}
