package com.funtis.commons.http.apache

import com.funtis.commons.http._
import org.apache.http.HttpRequest
import org.apache.http.client.HttpClient
import org.apache.http.client.methods.RequestBuilder
import org.apache.http.impl.client.HttpClients

/**
  * Created by Sławomir Kluz on 05/10/2017.
  */
class ApacheRestClient extends RestClient {

  override def execute(request: Request, context: Context): Response = {
    val client = HttpClients.createDefault()
    val clientRequestBuilder = RequestBuilder.create(request.method.toString).setUri(request.url)
    request.headers.asList().foreach(h => clientRequestBuilder.addHeader(h.name, h.value))
    val clientRequest = clientRequestBuilder.build()
    val clientResponse = client.execute(clientRequest)
    val responseHeaders = new Headers(clientResponse.getAllHeaders.map(h => Header(h.getName, h.getValue)): _*)
    Response(clientResponse.getStatusLine.getStatusCode, responseHeaders, null)
  }
}
