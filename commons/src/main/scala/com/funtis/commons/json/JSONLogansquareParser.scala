package com.funtis.commons.json

import com.bluelinelabs.logansquare.LoganSquare

/**
  * Created by Sławomir Kluz on 06/09/2017.
  */
class JSONLogansquareParser extends JSONParser {

  override def toJSON(any: Any): String = {
    LoganSquare.serialize(any)
  }
  override def fromJSON[T](json: String, cls: Class[T]) = {
    LoganSquare.parse(json, cls)
  }
}
