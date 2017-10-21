package utf.commons.http

import utf.commons.http.Method.Method

/**
  * Created by Sławomir Kluz on 04/10/2017.
  */
case class Request(method: Method, url: String, headers: Headers, body: Body, name: String) {

  private val NEW_LINE = System.getProperty("line.separator")
  private val SKIPPED_REQUEST_HEADERS = Seq("content-length", "transfer-encoding", "host", "connection")

  def asCurlRequest(): String = {
    val sb = new StringBuilder()
    val uri = url
    //sb.append(s">>>> Request [$name] ").append(">" * (name.length + 80 - 16)).append(NEW_LINE)
    sb.append(s">>>> Request [$name] ").append(NEW_LINE)
    sb.append("curl -i ").append("-X ").append(method.toString.toUpperCase).append(" '").append(uri).append("' ")
    headers.asList().filterNot(h => SKIPPED_REQUEST_HEADERS.contains(h.name.toLowerCase)).foreach(h => {
      sb.append("-H '").append(h.name).append(": ").append(h.value).append("' ")
    })
    body match {
      case null =>
      case body: Body => sb.append("-d '").append(body.asString()).append("'")
    }
    //sb.append(NEW_LINE).append(s"<<<< Request [$name] ").append("<" * (name.length + 80 - 16)).append(NEW_LINE)
    sb.append(NEW_LINE)
    sb.toString()
  }
}
