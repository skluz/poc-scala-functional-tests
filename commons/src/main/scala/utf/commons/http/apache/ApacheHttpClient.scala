package utf.commons.http.apache

import utf.commons.http._
import org.apache.http._
import org.apache.http.client.HttpClient
import org.apache.http.client.config.RequestConfig
import org.apache.http.client.entity.EntityBuilder
import org.apache.http.client.methods.RequestBuilder
import org.apache.http.impl.client.HttpClientBuilder
import org.apache.http.util.EntityUtils

/**
  * Created by Sławomir Kluz on 05/10/2017.
  */
class ApacheHttpClient extends utf.commons.http.HttpClient {

  override def execute(request: Request, configuration: Configuration): Response = {
    val client = ApacheHttpClient.client
    val clientRequestBuilder = RequestBuilder.create(request.method.toString).setUri(request.url)
    request.headers.asList().foreach(h => clientRequestBuilder.addHeader(h.name, h.value))
    clientRequestBuilder.setEntity(createEntity(request))
    val clientRequest = clientRequestBuilder.setConfig(createRequestConfig(request, configuration)).build()
    val start = System.currentTimeMillis()
    val clientResponse = client.execute(clientRequest)
    val responseHeaders = new Headers(clientResponse.getAllHeaders.map(h => utf.commons.http.Header(h.getName, h.getValue)): _*)
    val statusLine = clientResponse.getStatusLine
    Response(
      statusLine.getStatusCode,
      statusLine.getReasonPhrase,
      statusLine.getProtocolVersion.toString,
      responseHeaders,
      new Body(EntityUtils.toByteArray(clientResponse.getEntity)),
      System.currentTimeMillis() - start,
      request.name
    )
  }

  private def createRequestConfig(request: Request, configuration: Configuration): RequestConfig = {
    RequestConfig.custom()
      .setSocketTimeout(configuration.socketTimeout)
      .setConnectTimeout(configuration.connectionTimeout)
      .setConnectionRequestTimeout(configuration.connectionTimeout)
      .setRedirectsEnabled(configuration.followRedirects)
      .build()
  }

  private def createEntity(request: Request): HttpEntity = {
    request.body match {
      case null => null
      case _ => EntityBuilder.create().setBinary(request.body.bytes).build()
    }
  }

}

object ApacheHttpClient {

  lazy val client: HttpClient = createClient()

  def createClient(): HttpClient = {
    HttpClientBuilder.create().build()
  }

}