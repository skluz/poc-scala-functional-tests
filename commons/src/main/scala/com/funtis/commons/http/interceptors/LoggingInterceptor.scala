package com.funtis.commons.http.interceptors

import java.nio.charset.Charset

import com.typesafe.scalalogging.LazyLogging
import okhttp3.internal.http.HttpHeaders
import okhttp3.{Interceptor, Request, Response}
import okio.Buffer

import scala.collection.JavaConverters._

/**
  * Created by Sławomir Kluz on 11/09/2017.
  */
class LoggingInterceptor extends Interceptor with LazyLogging {

  private val UTF8 = Charset.forName("UTF-8")
  private val NEW_LINE = System.getProperty("line.separator")

  override def intercept(chain: Interceptor.Chain) = {
    val request = chain.request()
    val tag = request.tag().toString
    logRequest(request, tag)
    logResponse(chain.proceed(request), tag)
  }

  private def logRequest(request: Request, tag: String) = {
    val sb = new StringBuilder(NEW_LINE)
    sb.append(s">>>> Request [$tag] ").append(">" * (tag.length + 80 - 16)).append(NEW_LINE)
    sb.append("curl -i ").append("-X ").append(request.method().toUpperCase()).append(" '").append(request.url().toString).append("' ")
    request.headers().names().asScala.filterNot(name => Seq("content-length", "transfer-encoding", "host", "connection").contains(name.toLowerCase()))foreach(name => {
      sb.append("-H '").append(name).append(": ").append(request.header(name)).append("' ")
    })
    if(request.body() != null && request.body().contentLength() > 0) {
      val buffer = new Buffer
      val bodyString = request.body().writeTo(buffer)
      sb.append("-d '").append(buffer.readString(request.body().contentType().charset())).append("'")
    }
    sb.append(NEW_LINE).append(s"<<<< Request [$tag] ").append("<" * (tag.length + 80 - 16)).append(NEW_LINE)
    logger.info(sb.toString())
  }

  private def logResponse(response: Response, tag: String) = {
    val sb = new StringBuilder(NEW_LINE)
    sb.append(s">>>> Response [$tag] ").append(">" * (tag.length + 80 - 17)).append(NEW_LINE)
    sb.append(response.protocol().toString.toUpperCase()).append(" ").append(response.code()).append(" ").append(response.message()).append(NEW_LINE)
    response.headers().names().asScala.foreach(name => {
      sb.append(name).append(": ").append(response.headers().get(name)).append(NEW_LINE)
    })
    sb.append(NEW_LINE)
    if(HttpHeaders.hasBody(response)) {
      val responseBody = response.body
      val source = responseBody.source
      source.request(Long.MaxValue)
      val buffer = source.buffer
      var charset = UTF8
      val contentType = responseBody.contentType
      val contentLength = responseBody.contentLength
      if (contentType != null) charset = contentType.charset(UTF8)
      if (contentLength != 0)
        sb.append(buffer.clone().readString(charset))
    }
    sb.append(NEW_LINE).append(s"<<<< Response [$tag] ").append("<" * (tag.length + 80 - 17)).append(NEW_LINE)
    logger.info(sb.toString())
    response
  }

}
