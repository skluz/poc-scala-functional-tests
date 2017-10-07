//package com.funtis.commons.oldhttp
//
//import java.util.UUID
//
//import com.funtis.commons.http.{Header, Headers, Response, Specification}
//import com.typesafe.scalalogging.LazyLogging
//import okhttp3.{HttpUrl, Request, RequestBody}
//
//import scala.collection.mutable
//import scala.collection.JavaConverters._
//
///**
//  * Created by Sławomir Kluz on 11/09/2017.
//  */
//class RestClient(
//  specification: Specification = Specification.default,
//  configuration: Configuration = Configuration.default) extends LazyLogging {
//
//
//  private def execute(method: String): Response = {
//    execute(method, path)
//  }
//
//  private def execute(method: String, path: String): Response = {
//    val request = new Request.Builder()
//      .url(buildUrl(path))
//      .method(method, buildBody(body))
//      .headers(buildHeaders())
//      .tag(tag)
//      .build()
//    val rawResponse: okhttp3.Response = configuration.client.newCall(request).execute()
//    buildResponse(rawResponse)
//  }
//
//  private def buildResponse(rawResponse: okhttp3.Response): Response = {
//    val headers = Headers(rawResponse.headers().names().asScala.map(n => Header(n, rawResponse.headers().get(n))).toSeq)
//    val rawBody = rawResponse.body()
//    val response = Response(rawResponse.code(), headers, new ResponseBody(rawBody.string(), configuration.parser))
//    rawBody.close()
//    response
//  }
//
//  private def buildHeaders(): okhttp3.Headers = {
//    okhttp3.Headers.of(specification.headers.asMap().asJava)
//  }
//
//  private def buildBody(body: Any): RequestBody = {
//    if(body == null) null else RequestBody.create(null, configuration.parser.toJSON(body))
//  }
//
//  private def buildUrl(path: String) = {
//    HttpUrl.parse(uri).newBuilder()
//      .addPathSegments(resolvePath(path))
//      .build()
//  }
//
//  private def resolvePath(path: String): String = {
//    val keys = """\{([A-Za-z]+)\}""".r.findAllMatchIn(path).map(m => m.group(1)).toSeq
//    if(!keys.sameElements(pathParams.keys)) throw new IllegalArgumentException(s"Inconsistent path parameters: $path, params: ${pathParams.keySet.mkString(", ")}")
//    (path.replaceFirst("^/","") /: pathParams) { (t, kv) => t.replace("{" + kv._1 + "}", kv._2.toString)}
//  }
//
//}
