package com.funtis.commons.json.jackson

import java.time.Instant

import com.fasterxml.jackson.core.`type`.TypeReference
import com.fasterxml.jackson.module.scala.JsonScalaEnumeration
import com.funtis.commons.json._
import com.typesafe.scalalogging.LazyLogging
import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by Sławomir Kluz on 07/09/2017.
  */
class JSONParserTest extends FlatSpec with Matchers with LazyLogging {

  val parser: JSONParser = JSONParser.jackson

  behavior of "serialization"

  it should "format Instant to ISO-8601 string" in {
    parser.toJSON(InstantCase(Instant.parse("2014-12-03T10:15:30.00Z"))) shouldBe """{"time":"2014-12-03T10:15:30Z"}"""
  }

  it should "does not serialize null values" in {
    parser.toJSON(NullCase(1, null)) shouldBe """{"number":1}"""
    parser.toJSON(NullOptionCase(1, null)) shouldBe """{"number":1}"""
  }

  it should "serialize None to null" in {
    parser.toJSON(NullOptionCase(1, None)) shouldBe """{"number":1,"option":null}"""
  }

  it should "serialize Option" in {
    parser.toJSON(NullOptionCase(1, Some("hello"))) shouldBe """{"number":1,"option":"hello"}"""
  }

  it should "handle sequences" in {
    parser.toJSON(SequenceCase(1, Seq("A", "B", "C"))) shouldBe """{"number":1,"seq":["A","B","C"]}"""
    parser.toJSON(SequenceCase(1, Seq())) shouldBe """{"number":1,"seq":[]}"""
  }

  it should "handle maps" in {
    parser.toJSON(MapCase(1, Map("A" -> "first", "B" -> "second"))) shouldBe """{"number":1,"map":{"A":"first","B":"second"}}"""
    parser.toJSON(MapCase(1, Map())) shouldBe """{"number":1,"map":{}}"""
  }

  it should "handle nested objects" in {
    parser.toJSON(NestedCase(1, NullCase(1, "hello"))) shouldBe """{"number":1,"nested":{"number":1,"string":"hello"}}"""
  }

  it should "handle direct types" in {
    parser.toJSON(1) shouldBe """1"""
    parser.toJSON("hello") shouldBe """"hello""""
    parser.toJSON(true) shouldBe """true"""
    parser.toJSON(Seq(1, 2)) shouldBe """[1,2]"""
    parser.toJSON(Map(1 -> "ONE")) shouldBe """{"1":"ONE"}"""
  }

  it should "handle generic sequences" in {
    parser.toJSON(GenericSequenceCase[SimpleCase](Seq(SimpleCase(1), SimpleCase(2)))) shouldBe """{"data":[{"number":1},{"number":2}]}"""
  }

  it should "handle enums" in {
    parser.toJSON(EnumCase(YesNo.No)) shouldBe """{"value":"No"}"""
  }

  behavior of "deserialization"

  it should "handle ISO-8601 as Instants" in {
    parser.fromJSON("""{"time":"2014-12-03T10:15:30Z"}""", classOf[InstantCase]) shouldBe InstantCase(Instant.parse("2014-12-03T10:15:30.00Z"))
  }

  it should "handle nulls" in {
    parser.fromJSON("""{"number":1,"string":null}""", classOf[NullCase]) shouldBe NullCase(1, null)
  }

  it should "handle null Options property" in {
    parser.fromJSON("""{"number":1,"option":null}""", classOf[NullOptionCase]) shouldBe NullOptionCase(1, None)
  }

  it should "handle missing Options property" in {
    parser.fromJSON("""{"number":1}""", classOf[NullOptionCase]) shouldBe NullOptionCase(1, None)
  }

  it should "handle missing properties" in {
    parser.fromJSON("""{}""", classOf[NullOptionCase]) shouldBe NullOptionCase(null, None)
  }

  it should "fail on unknown properties" in {
    assertThrows[Exception] {
      parser.fromJSON("""{"number":1,"string":"hello","new":true}""", classOf[NullCase])
    }
  }

  it should "handle generic sequences" in {
    parser.fromJSON[GenericSequenceCase[SimpleCase]]("""{"data":[{"number":1},{"number":2}]}""", classOf[GenericSequenceCase[_]], classOf[SimpleCase]) shouldBe GenericSequenceCase[SimpleCase](Seq(SimpleCase(1), SimpleCase(2)))
  }

  it should "handle enums" in {
    parser.fromJSON("""{"value":"No"}""", classOf[EnumCase]) shouldBe EnumCase(YesNo.No)
  }

}

case class InstantCase(time: Instant)
case class SimpleCase(number: Int)
case class NullCase(number: Integer, string: String)
case class NullOptionCase(number: Integer, option: Option[String])
case class SequenceCase(number: Int, seq: Seq[Any])
case class MapCase(number: Int, map: Map[Any, Any])
case class NestedCase(number: Int, nested: NullCase)
case class GenericSequenceCase[T](data: Seq[T])
case class EnumCase(
  @JsonScalaEnumeration(classOf[YesNoTypeReference]) value: YesNo.YesNoType
)

object YesNo extends Enumeration {
  type YesNoType = Value
  val Yes, No = Value
}

private class YesNoTypeReference extends TypeReference[YesNo.type]