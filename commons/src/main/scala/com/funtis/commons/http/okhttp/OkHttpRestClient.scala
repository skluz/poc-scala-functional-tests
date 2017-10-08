package com.funtis.commons.http.okhttp

import java.io.File

import com.funtis.commons.http._
import com.funtis.commons.oldhttp.interceptors.LoggingInterceptor
import okhttp3.{OkHttpClient, RequestBody}

import scala.collection.JavaConverters._

/**
  * Created by Sławomir Kluz on 04/10/2017.
  */
class OkHttpRestClient extends RestClient {

  private lazy val client = buildClient()
  private lazy val interceptors = Seq(new LoggingInterceptor)

  override def execute(request: Request, context: Context): Response = {
    val requestBuilder = new okhttp3.Request.Builder()
      .url(request.url)
      .method(request.method.toString, buildBody(request))
      .headers(buildHeaders(request))
      .removeHeader("User-Agent")
    if(request.name.isDefined) requestBuilder.tag(request.name.get)
    val clientRequest = requestBuilder.build()
    val clientResponse = client.newCall(clientRequest).execute()
    buildResponse(clientResponse)
  }

  def buildBody(request: Request): okhttp3.RequestBody = {
    request.body match {
      case null => null
      case s: String => RequestBody.create(null, s)
      case f: File => RequestBody.create(null, f)
      case _ => throw new IllegalArgumentException("Body type not supported")
    }
  }

  private def buildClient(): OkHttpClient = {
    val builder = new OkHttpClient.Builder()
    interceptors.foreach(i => builder.addNetworkInterceptor(i))
    builder.build()
  }

  private def buildHeaders(request: Request): okhttp3.Headers = {
    //okhttp3.Headers.of(request.headers.headers.asJava)
    null
  }

  private def buildResponse(clientResponse: okhttp3.Response): Response = {
    val headers = new Headers(clientResponse.headers().names().asScala.map(n => Header(n, clientResponse.headers().get(n))).toSeq: _*)
    val rawBody = clientResponse.body()
    val response = Response(clientResponse.code(), headers, new ResponseBody(rawBody.string()))
    rawBody.close()
    response
  }

}
