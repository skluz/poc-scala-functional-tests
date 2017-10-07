package com.funtis.commons.http

import java.net.URI

import com.funtis.commons.http.Method.Method
import com.funtis.commons.http.apache.ApacheRestClient
import com.funtis.commons.http.okhttp.OkHttpRestClient
import com.funtis.commons.random.Randomizer
import com.typesafe.scalalogging.LazyLogging

import scala.collection.mutable

/**
  * Created by Sławomir Kluz on 04/10/2017.
  */
trait RestClient {
  def execute(request: Request, context: Context): Response
}

object RestClient extends LazyLogging {
  private lazy val default = apache
  private lazy val okHttp = new OkHttpRestClient
  private lazy val apache = new ApacheRestClient

  class Builder(specification: Specification = Specification.default, context: Context = Context.default) {

    private val restClient: RestClient = default

    private var baseUri: String = specification.baseUri
    private var path: String = _
    private var name: String = Randomizer.id()
    private var body: Any = _

    private val pathParams = mutable.Map[String, Any]()
    private val queryParams = mutable.Buffer[(String, Any)]()
    private val headers = mutable.Buffer[Header]()

    def baseUri(uri: String): Builder = {
      this.baseUri = uri
      this
    }

    def pathParam(name: String, value: Any): Builder = {
      pathParams += name -> value
      this
    }

    def queryParam(name: String, value: Any): Builder = {
      queryParams += name -> value
      this
    }

    def queryParam(name: String, value: Option[_]): Builder = {
      if(value.isDefined) queryParams += name -> value.get
      this
    }

    def queryParam(params: Seq[(String, Any)]): Builder = {
      queryParams ++= params
      this
    }

    def path(path: String): Builder = {
      this.path = path
      this
    }

    def body(body: Any): Builder = {
      this.body = body
      this
    }

    def name(name: String): Builder = {
      this.name = name
      this
    }

    def GET(): Response = execute(Method.GET)
    def GET(path: String): Response = execute(Method.GET, path)

    def buildRequest(method: Method): Request = {
      try {
        Request(method, buildUrl(), buildHeaders(), buildBody(), Some(name))
      } catch {
        case t: Throwable => throw new RequestValidationException("Cannot build request", t)
      }
    }

    def execute(method: Method): Response = {
      restClient.execute(buildRequest(method), context)
    }

    def execute(method: Method, path: String): Response = {
      this.path = path
      execute(method)
    }

    def resolveQuery(baseQuery: String, queryParams: mutable.Buffer[(String, Any)]): String = {
      val fixedQuery = if(queryParams.isEmpty) null else queryParams.map(_.productIterator.filter(_ != null).mkString("=")).mkString("&")
      val result = new mutable.StringBuilder()
      if(baseQuery != null) result.append(baseQuery)
      if(baseQuery != null && fixedQuery != null) result.append("&")
      if(fixedQuery != null) result.append(fixedQuery)
      if(baseQuery != null && baseQuery.isEmpty) return ""
      if(result.isEmpty) null else result.toString()
    }

    private def buildUrl(): String = {
      val uri = URI.create(this.baseUri)
      val pathUpdatedUri = new URI(uri.getScheme, uri.getRawAuthority, resolvePath(uri.getRawPath, this.path), resolveQuery(uri.getRawQuery, queryParams), uri.getRawFragment)
      pathUpdatedUri.toString
    }

    private def buildBody(): Any = {
      body
    }

    private def buildHeaders(): Headers = {
      Headers(specification.headers.headers ++ headers)
    }

    private def resolvePath(basePath: String, path: String): String = {
      val fixedBasePath = if(basePath == null || basePath.isEmpty) "/" else basePath
      val fixedPath = if(path == null || path.isEmpty) "" else {
        val keys = """\{([A-Za-z]+)\}""".r.findAllMatchIn(path).map(m => m.group(1)).toSeq
        if(!keys.toSet.equals(pathParams.keys.toSet)) throw new IllegalArgumentException(s"Inconsistent path parameters: $path, params: [${pathParams.keySet.mkString(", ")}]")
        (path.replaceFirst("^([^\\/])","/$1") /: pathParams) { (t, kv) => t.replace("{" + kv._1 + "}", kv._2.toString)}
      }
      if(fixedBasePath.endsWith("/") && fixedPath.startsWith("/")) fixedBasePath.dropRight(1) + fixedPath else fixedBasePath + fixedPath
    }

  }

}