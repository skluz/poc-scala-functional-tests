package com.funtis.commons.http

import scala.collection.mutable

/**
  * Created by Sławomir Kluz on 11/09/2017.
  */
case class Headers(headers: mutable.HashMap[String, String]) {
  def this(a: Header*) = this(mutable.HashMap(a.map(h => h.name -> h.value): _*))
  //def this(a: Seq[Header]) = this(mutable.HashMap(a.map(h => h.name -> h.value): _*))
  def asList(): Seq[Header] = headers.map(h => Header(h._1, h._2)).toSeq
}