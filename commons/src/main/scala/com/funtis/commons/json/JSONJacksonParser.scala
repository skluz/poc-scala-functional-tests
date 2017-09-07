package com.funtis.commons.json

import com.fasterxml.jackson.annotation.JsonInclude.Include
import com.fasterxml.jackson.core.`type`.TypeReference
import com.fasterxml.jackson.databind.{DeserializationFeature, ObjectMapper, SerializationFeature}
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper

import scala.reflect.{classTag, _}

/**
  * Created by Sławomir Kluz on 06/09/2017.
  */
class JSONJacksonParser extends JSONParser {

  private lazy val mapper = new ObjectMapper()
  mapper.findAndRegisterModules()

  // dates will be serialized to ISO-8601 formatted string
  mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
  // deserialization will fail if new property (not defined in model) occurs
  mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true)
  // deserialization will fail if model property is missing
  mapper.configure(DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES, true)

  // nulls or Nones will be skipped in serialized string
  mapper.setSerializationInclusion(Include.NON_ABSENT)

  override def toJSON(any: Any): String = {
    mapper.writeValueAsString(any)
  }
  override def fromJSON[T](json: String, cls: Class[T]): T = {
    mapper.readValue(json, cls)
  }

  override def fromJSON[T: ClassTag](json: String): T = {
    fromJSON(json, classTag[T].runtimeClass).asInstanceOf[T]
  }

  def fromJSON[T](json: String, parametrized: Class[_], parameterClasses: Class[_]): T = {
    mapper.readValue[T](json, mapper.getTypeFactory.constructParametricType(parametrized, parameterClasses))
  }
}