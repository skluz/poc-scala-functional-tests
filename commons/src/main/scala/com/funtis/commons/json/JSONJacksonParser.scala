package com.funtis.commons.json

import com.fasterxml.jackson.annotation.JsonInclude.Include
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor
import com.fasterxml.jackson.databind.node.ObjectNode
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider
import com.fasterxml.jackson.databind.ser.{PropertyFilter, PropertyWriter}
import com.fasterxml.jackson.databind.{DeserializationFeature, ObjectMapper, SerializationFeature, SerializerProvider}

/**
  * Created by Sławomir Kluz on 06/09/2017.
  */
class JSONJacksonParser extends JSONParser {

  private lazy val mapper = new ObjectMapper()
  mapper.findAndRegisterModules()

  // dates will be serialized to ISO-8601 formatted string
  mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
  // deserialization will fail if property not defined in model occurs
  mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true)

  // nulls or Nones will be skipped
  mapper.setSerializationInclusion(Include.CUSTOM)

  val filterProvider = new SimpleFilterProvider().addFilter("NullFilter", new NullFilter())
  mapper.setFilterProvider(filterProvider)

  override def toJSON(any: Any): String = {
    mapper.writeValueAsString(any)
  }
  override def fromJSON[T](json: String, cls: Class[T]) = {
    mapper.readValue(json, cls)
  }
}

class NullFilter extends PropertyFilter {
  @Deprecated
  override def depositSchemaProperty(writer: PropertyWriter, propertiesNode: ObjectNode, provider: SerializerProvider) = writer.depositSchemaProperty(propertiesNode, provider)
  override def serializeAsField(pojo: scala.Any, gen: JsonGenerator, prov: SerializerProvider, writer: PropertyWriter) = {
    print(pojo.getClass)
    if(pojo.isInstanceOf[Option[_]]) {
    } else {
      writer.serializeAsField(pojo, gen, prov)
    }
  }
  override def depositSchemaProperty(writer: PropertyWriter, objectVisitor: JsonObjectFormatVisitor, provider: SerializerProvider) = writer.depositSchemaProperty(objectVisitor, provider)
  override def serializeAsElement(elementValue: scala.Any, gen: JsonGenerator, prov: SerializerProvider, writer: PropertyWriter) = writer.serializeAsElement(elementValue, gen, prov)
}