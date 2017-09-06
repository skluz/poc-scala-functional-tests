package com.funtis.commons.json

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include
import com.fasterxml.jackson.databind.{DeserializationFeature, ObjectMapper, SerializationFeature}

/**
  * Created by Sławomir Kluz on 06/09/2017.
  */
class JSONJacksonParser extends JSONParser {

  private lazy val mapper = new ObjectMapper()
  mapper.findAndRegisterModules()
  mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
  mapper.configure(DeserializationFeature.FAIL_ON_NULL_CREATOR_PROPERTIES, true)

  // skip null
  mapper.setSerializationInclusion(Include.NON_ABSENT)

  override def toJSON(any: Any): String = {
    mapper.writeValueAsString(any)
  }
  override def fromJSON[T](json: String, cls: Class[T]) = {
    mapper.readValue(json, cls)
  }
}