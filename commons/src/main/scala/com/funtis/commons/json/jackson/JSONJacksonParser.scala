package com.funtis.commons.json.jackson

import com.fasterxml.jackson.annotation.JsonInclude.Include
import com.fasterxml.jackson.core.PrettyPrinter
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter
import com.fasterxml.jackson.databind._
import com.funtis.commons.json.JSONParser

import scala.reflect.{classTag, _}

/**
  * Created by Sławomir Kluz on 06/09/2017.
  */
class JSONJacksonParser extends JSONParser {

  private val prettyWriter = new DefaultPrettyPrinter()
  private val mapper = new ObjectMapper()
  mapper.findAndRegisterModules()

  // dates will be serialized to ISO-8601 formatted string
  mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
  // nulls or Nones will be skipped in serialized string
  mapper.setSerializationInclusion(Include.NON_NULL)

  // deserialization will fail if new property (not defined in class) occurs
  mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true)
  // deserialization will not fail if model property is missing
  mapper.configure(DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES, false)



  override def toJSON(any: Any): String = {
    mapper.writeValueAsString(any)
  }

  override def toPrettyJSON(any: String): String = {
    val anyValue = mapper.readValue(any, classOf[Any])
    mapper.writer(prettyWriter).writeValueAsString(anyValue)
  }

  override def fromJSON[T](json: String, cls: Class[T]): T = {
    mapper.readValue(json, cls)
  }

  override def fromJSON[T](json: Array[Byte], cls: Class[T]): T = {
    mapper.readValue(json, cls)
  }

  override def fromJSON[T: ClassTag](json: String): T = {
    fromJSON(json, classTag[T].runtimeClass).asInstanceOf[T]
  }

  override def fromJSON[T: ClassTag](json: Array[Byte]): T = {
    fromJSON(json, classTag[T].runtimeClass).asInstanceOf[T]
  }

  override def fromJSON[T](json: String, parametrized: Class[_], parameterClasses: Class[_]): T = {
    mapper.readValue[T](json, mapper.getTypeFactory.constructParametricType(parametrized, parameterClasses))
  }

  override def fromJSON[T](json: Array[Byte], parametrized: Class[_], parameterClasses: Class[_]): T = {
    mapper.readValue[T](json, mapper.getTypeFactory.constructParametricType(parametrized, parameterClasses))
  }

}