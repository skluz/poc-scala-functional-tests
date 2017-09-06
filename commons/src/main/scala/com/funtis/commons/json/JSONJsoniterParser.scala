package com.funtis.commons.json

import com.jsoniter.JsonIterator
import com.jsoniter.output.JsonStream

/**
  * Created by Sławomir Kluz on 06/09/2017.
  */
class JSONJsoniterParser extends JSONParser {

  override def toJSON(any: Any): String = {
    JsonStream.serialize(any)
  }
  override def fromJSON[T](json: String, cls: Class[T]) = {
    JsonIterator.deserialize(json).as[T](cls)
  }
}
