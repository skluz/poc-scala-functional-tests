package com.funtis.commons.json

import com.jayway.jsonpath.{DocumentContext, JsonPath}

/**
  * Created by Sławomir Kluz on 12/10/2017.
  */
class JsonWrapper(obj: Any) {

  private lazy val parser = JSONParser.default
  private lazy val document: DocumentContext = JsonPath.parse(parser.toJSON(obj))

  def setField(path: String, value: Any): JsonWrapper = {
    document.set(path, value)
    this
  }

  def deleteField(path: String): JsonWrapper = {
    document.delete(path)
    this
  }

  def putValue(root: String, key: String, value: AnyRef): JsonWrapper = {
    document.put(root, key, value)
    this
  }

  def asJsonString(): String = document.jsonString()

}
