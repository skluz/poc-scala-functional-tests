package app

import java.time.Instant

import com.funtis.commons.json.JSONParser
import com.typesafe.scalalogging.LazyLogging
import model.{Address, Person}
import okhttp3._
import okio.Buffer

import scala.collection.JavaConverters._

/**
  * Created by Sławomir Kluz on 04/09/2017.
  */
object Application extends App with LazyLogging {

  val url = HttpUrl.parse("https://httpbin.org/").newBuilder().addPathSegment("post").build()
  val any = Person("Stefan", None, null, 10, Instant.now(), Seq("a", "b", "c"), Address(false, Map("a" -> "b", 3 -> "a")))
  val request = new Request.Builder().url(url).post(JsonBody.create(any)).header("Accept", "application/json").build()

  val client = new OkHttpClient.Builder().addNetworkInterceptor(new LoggingInterceptor()).build()
  val response = client.newCall(request).execute()

}

object JsonBody {
  lazy val parser: JSONParser = JSONParser.default
  def create(any: Any): RequestBody = {
    RequestBody.create(MediaType.parse("application/json; charset=utf-8"), parser.toJSON(any))
  }
}

class LoggingInterceptor extends Interceptor with LazyLogging {
  override def intercept(chain: Interceptor.Chain) = {
    val request = chain.request()
    logRequest(request)
    val response = chain.proceed(request)
    logResponse(response)
    response
  }

  private def logResponse(response: Response) = {
    val sb = new StringBuilder
    sb.append(System.getProperty("line.separator")).append(response.protocol().toString.toUpperCase()).append(" ").append(response.code()).append(" ").append(response.message()).append(System.getProperty("line.separator"))
    response.headers().names().asScala.foreach(name => {
      sb.append(name).append(": ").append(response.headers().get(name)).append(System.getProperty("line.separator"))
    })
    sb.append(System.getProperty("line.separator"))
    if(response.body().contentLength() > 0) {
      sb.append(response.body().string())
    }
    logger.info(sb.toString())
  }

  private def logRequest(request: Request) = {
    val sb = new StringBuilder
    sb.append("curl -i ").append("-X ").append(request.method().toUpperCase()).append(" '").append(request.url().toString).append("' ")
    request.headers().names().asScala.filterNot(name => Seq("content-length", "transfer-encoding", "host", "connection").contains(name.toLowerCase()))foreach(name => {
      sb.append("-H '").append(name).append(": ").append(request.header(name)).append("' ")
    })
    if(request.body().contentLength() > 0) {
      val buffer = new Buffer
      val bodyString = request.body().writeTo(buffer)
      sb.append("-d '").append(buffer.readString(request.body().contentType().charset())).append("'")
    }
    logger.info(sb.toString())
  }
}