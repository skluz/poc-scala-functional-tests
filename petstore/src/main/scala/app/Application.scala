package app

import java.time.{Instant, ZoneOffset}
import java.time.format.DateTimeFormatter

import com.funtis.commons.json.{JSONJsoniterParser, JSONLogansquareParser, JSONParser}
import com.squareup.moshi.{JsonAdapter, Moshi, ToJson}
import com.typesafe.scalalogging.LazyLogging
import model.{Address, Person}
import okhttp3.{HttpUrl, MediaType, Request, RequestBody}

/**
  * Created by Sławomir Kluz on 04/09/2017.
  */
object Application extends App with LazyLogging {

//  val url = HttpUrl.parse("https://httpbin.org/post").newBuilder().addPathSegment("api").build()
//  val request = new Request.Builder().url(url).post(JsonBody.create(Person("Stefan", 10))).build()

  val any = Person("Stefan", None, null, 10, Instant.now(), Seq("a", "b", "c"), Address(false, Map("a" -> "b", 3 -> "a")))
  val str = JSONParser.default.toJSON(any)

  logger.info(str)

  val person = JSONParser.default.fromJSON("{\"firstName\":\"Stefan\",\"birthDate\":\"2017-09-06T21:53:02.502Z\",\"list\":[\"a\",\"b\",\"c\"],\"address\":{\"active\":false,\"data\":{\"a\":\"b\",\"3\":\"a\"}}}", classOf[Person])
  logger.info(person.toString)

}

object JsonBody {
  lazy val moshi = new Moshi.Builder().add(new InstantAdapter()).build()
  def create(any: Any): RequestBody = {
    RequestBody.create(MediaType.parse("application/json; charset=utf-8"), moshi.adapter(classOf[Any]).toJson(any))
  }
}
