package com.funtis.petstore.mock.model

/**
  * Created by Sławomir Kluz on 13/10/2017.
  */
object Status extends Enumeration {
  type StatusType = Value
  val available, pending, sold = Value
}