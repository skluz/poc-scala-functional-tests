package com.funtis.commons.http

/**
  * Created by Sławomir Kluz on 05/10/2017.
  */
class RequestValidationException(msg: String = null, cause: Throwable = null) extends RuntimeException(msg, cause)