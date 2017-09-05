package app

import java.time.{Instant, ZoneOffset}
import java.time.format.DateTimeFormatter

import com.squareup.moshi.{JsonAdapter, Moshi, ToJson}
import com.typesafe.scalalogging.LazyLogging
import model.Person
import okhttp3.{HttpUrl, MediaType, Request, RequestBody}

/**
  * Created by Sławomir Kluz on 04/09/2017.
  */
object Application extends App with LazyLogging {

//  val url = HttpUrl.parse("https://httpbin.org/post").newBuilder().addPathSegment("api").build()
//  val request = new Request.Builder().url(url).post(JsonBody.create(Person("Stefan", 10))).build()

  val any = Person("Stefan", 10, Instant.now())
  val moshi = new Moshi.Builder().build()
  val str = moshi.adapter(classOf[Any]).toJson(any)

  logger.info(str)

}

object JsonBody {
  lazy val moshi = new Moshi.Builder().add(new InstantAdapter()).build()
  def create(any: Any): RequestBody = {
    RequestBody.create(MediaType.parse("application/json; charset=utf-8"), moshi.adapter(classOf[Any]).toJson(any))
  }
}
