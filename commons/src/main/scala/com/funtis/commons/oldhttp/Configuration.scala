package com.funtis.commons.oldhttp

import com.funtis.commons.oldhttp.interceptors.LoggingInterceptor
import com.funtis.commons.json.JSONParser
import okhttp3.{Interceptor, OkHttpClient}

/**
  * Created by Sławomir Kluz on 11/09/2017.
  */
class Configuration(
  interceptors: Seq[Interceptor] = Seq(new LoggingInterceptor),
  val parser: JSONParser = JSONParser.default
) {
  lazy val client: OkHttpClient = buildClient()
  def buildClient(): OkHttpClient =  {
    val builder = new OkHttpClient.Builder()
    interceptors.foreach(i => builder.addNetworkInterceptor(i))
    builder.build()
  }
}

object Configuration {
  def default = new Configuration()
}
