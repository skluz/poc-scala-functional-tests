package utf.commons.http

import java.net.URI

import utf.commons.http.apache.ApacheHttpClient
import utf.commons.json.JSONParser
import utf.commons.json.jackson.JSONJacksonParser
import utf.commons.random.Randomizer
import com.typesafe.scalalogging.LazyLogging

import scala.collection.mutable

/**
  * Created by Sławomir Kluz on 04/10/2017.
  */
trait HttpClient {
  def execute(request: Request, configuration: Configuration): Response
}

object HttpClient extends LazyLogging {

  lazy val default: HttpClient = new ApacheHttpClient

  class Builder(specification: Specification = Specification.default, configuration: Configuration = Configuration.default) {

    private val restClient: HttpClient = HttpClient.default
    private val jsonParser: JSONParser = JSONParser.default

    private var method: Method.Value = specification.method
    private var baseUri: String = specification.baseUri
    private var path: String = _
    private val pathParams = mutable.Map[String, Any]()
    private val queryParams = mutable.Buffer[(String, Any)]()
    private val headers = specification.headers
    private var requestBody: Any = _
    private var name: String = Randomizer.id()

    def baseUri(uri: String): Builder = {
      this.baseUri = uri
      this
    }

    def header(header: Header): Builder = {
      headers.add(header)
      this
    }

    def header(name: String, value: String): Builder = header(Header(name, value))

    def header(h: (String, String)): Builder = header(Header(h._1, h._2))

    def pathParam(name: String, value: Any): Builder = {
      pathParams += name -> value
      this
    }

    def pathParam(name: String, value: Option[_]): Builder = {
      if(value.isDefined) pathParams += name -> value.get
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

    def queryParam(params: (String, String)): Builder = {
      queryParams += params._1 -> params._2
      this
    }

    def path(path: String): Builder = {
      this.path = path
      this
    }

    def body(requestBody: Any): Builder = {
      this.requestBody = requestBody
      this
    }

    def body(): Builder = {
      this
    }

    def name(name: String): Builder = {
      this.name = name
      this
    }

    def method(method: Method.Value): Builder = {
      this.method = method
      this
    }

    def GET(): Response = execute(buildRequest(Method.GET))
    def GET(path: String): Response = execute(buildRequest(Method.GET, path))

    def POST(): Response = execute(buildRequest(Method.POST))
    def POST(path: String): Response = execute(buildRequest(Method.POST, path))

    def PUT(): Response = execute(buildRequest(Method.PUT))
    def PUT(path: String): Response = execute(buildRequest(Method.PUT, path))

    def DELETE(): Response = execute(buildRequest(Method.DELETE))
    def DELETE(path: String): Response = execute(buildRequest(Method.DELETE, path))

    def buildRequest(): Request = {
      try {
        Request(buildMethod(), buildUrl(), buildHeaders(), buildBody(), buildName())
      } catch {
        case e: Throwable => throw new RequestValidationException("Cannot build request", e)
      }
    }

    def buildRequest(method: Method.Value): Request = {
      this.method = method
      buildRequest()
    }
    def buildRequest(method: Method.Value, path: String): Request = {
      this.path(path)
      buildRequest(method)
    }

    private def execute(request: Request): Response = {
      logger.info(request.asCurlRequest())
      val response = restClient.execute(request, this.configuration).withParser(jsonParser)
      logger.info(response.asCurlResponse())
      response
    }

    private def buildMethod(): Method.Value = this.method

    private def buildUrl(): String = {
      val uri = URI.create(this.baseUri)
      val path = resolvePath(uri.getRawPath, this.path, this.pathParams)
      val query = resolveQuery(uri.getRawQuery, this.queryParams)
      val pathUpdatedUri = new URI(uri.getScheme, uri.getRawAuthority, path, query, uri.getRawFragment)
      pathUpdatedUri.toString
    }

    private def resolvePath(basePath: String, path: String, pathParams: mutable.Map[String, Any]): String = {
      val fixedBasePath = if(basePath == null || basePath.isEmpty) "/" else basePath
      val fixedPath = if(path == null || path.isEmpty) "" else {
        val keys = """\{([A-Za-z]+)\}""".r.findAllMatchIn(path).map(m => m.group(1)).toSeq
        if(!keys.toSet.equals(pathParams.keys.toSet)) throw new IllegalArgumentException(s"Inconsistent path parameters: $path, params: [${pathParams.keySet.mkString(", ")}]")
        (path.replaceFirst("^([^\\/])","/$1") /: pathParams) { (t, kv) => t.replace("{" + kv._1 + "}", kv._2.toString)}
      }
      if(fixedBasePath.endsWith("/") && fixedPath.startsWith("/")) fixedBasePath.dropRight(1) + fixedPath else fixedBasePath + fixedPath
    }

    private def resolveQuery(baseQuery: String, queryParams: mutable.Buffer[(String, Any)]): String = {
      val fixedQuery = if(queryParams.isEmpty) null else queryParams.map(_.productIterator.filter(_ != null).mkString("=")).mkString("&")
      val result = new mutable.StringBuilder()
      if(baseQuery != null) result.append(baseQuery)
      if(baseQuery != null && fixedQuery != null) result.append("&")
      if(fixedQuery != null) result.append(fixedQuery)
      if(baseQuery != null && baseQuery.isEmpty) return ""
      if(result.isEmpty) null else result.toString()
    }

    private def buildHeaders(): Headers = this.headers
    private def buildName(): String = this.name


    private def buildBody(): Body = Body.createRequestBody(requestBody, jsonParser)

  }

}