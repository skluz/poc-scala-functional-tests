package com.funtis.commons.http

import com.funtis.commons.http.Method.Method

/**
  * Created by Sławomir Kluz on 04/10/2017.
  */
case class Request(method: Method, url: String, headers: Headers, body: Any, name: Option[String] = None)
