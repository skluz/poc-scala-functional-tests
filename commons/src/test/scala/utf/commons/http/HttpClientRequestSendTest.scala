package utf.commons.http

import java.lang.AssertionError

import utf.commons.http
import utf.commons.http.model.HttpBinResponse
import com.typesafe.scalalogging.LazyLogging
import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by Sławomir Kluz on 09/10/2017.
  */
class HttpClientRequestSendTest extends FlatSpec with Matchers with LazyLogging {

  private val specification = Specification.default.copy("https://httpbin.org/")

  behavior of "methods"

  it should "send GET method" in {
    new HttpClient.Builder(specification).GET("/anything").body().as[HttpBinResponse].method shouldBe Some("GET")
  }

  it should "send POST method" in {
    new HttpClient.Builder(specification).POST("/anything").body().as[HttpBinResponse].method shouldBe Some("POST")
  }

  it should "send PUT method" in {
    new HttpClient.Builder(specification).PUT("/anything").body().as[HttpBinResponse].method shouldBe Some("PUT")
  }

  it should "send DELETE method" in {
    new HttpClient.Builder(specification).DELETE("/anything").body().as[HttpBinResponse].method shouldBe Some("DELETE")
  }

  behavior of "url"

  it should "handle query and path params" in {
    val specification = Specification.default.copy("https://httpbin.org/anything")
    new HttpClient.Builder(specification).pathParam("id", 10).queryParam("sort" -> "id").GET("/path/{id}").body().as[HttpBinResponse].url shouldBe "https://httpbin.org/anything/path/10?sort=id"
  }

  behavior of "headers"

  it should "send defined headers" in {
    new HttpClient.Builder(specification).header("Some-Header" -> "Hello").GET("/anything").body().as[HttpBinResponse].headers should contain ("Some-Header" -> "Hello")
  }

  behavior of "response code"

  it should "assert single code" in {
    new HttpClient.Builder(specification).GET("/anything").assertCode(200)
  }

  it should "assert multiple codes" in {
    new HttpClient.Builder(specification).GET("/status/201").assertCode(200, 201)
  }

  it should "fail when wrong code received" in {
    an[AssertionError] should be thrownBy new HttpClient.Builder(specification).GET("/status/400").assertCode(200, 201)
  }

//  behavior of "response duration"
//
//  it should "calculated duration" in {
//    new HttpClient.Builder(specification).GET("/delay/2").duration should be > 2000L
//  }
//
//  it should "handle default timeout" in {
//    an[Exception] should be thrownBy new HttpClient.Builder(specification).GET("/delay/6")
//  }
//
//  it should "handle custom timeout" in {
//    an[Exception] should be thrownBy new HttpClient.Builder(specification, Configuration(1000, 1000)).GET("/delay/2")
//  }

  behavior of "redirect"

  it should "follow redirect by default" in {
    val response = new HttpClient.Builder(specification).GET("/redirect/1")
    response.body().as[HttpBinResponse].url shouldBe "https://httpbin.org/get"
    response.headers.contains("Location") shouldBe false
  }

  it should "does not follow redirect when " in {
    val configuration = Configuration.default.copy(followRedirects = false)
    new HttpClient.Builder(specification, configuration).GET("/redirect/1").headers.valueOf("Location") shouldBe "/get"
  }

}