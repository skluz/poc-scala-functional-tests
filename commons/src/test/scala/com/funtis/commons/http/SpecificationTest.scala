package com.funtis.commons.http

import com.typesafe.scalalogging.LazyLogging
import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by Sławomir Kluz on 09/10/2017.
  */
class SpecificationTest extends FlatSpec with Matchers with LazyLogging {

  behavior of "rest client"

  it should "use default specification" in {
    val request = new HttpClient.Builder().buildRequest()
    request.url shouldBe "http://localhost/"
    request.method shouldBe Method.GET
    request.headers shouldBe new Headers(Header("Content-Type", "application/json; charset=utf-8"), Header("Accept", "application/json"))
  }

  it should "handle custom specification" in {
    val specification = Specification("https://httpbin.org/", Method.POST, new Headers(Header("User-Agent", "Something")))
    val request = new HttpClient.Builder(specification).buildRequest()
    request.url shouldBe "https://httpbin.org/"
    request.method shouldBe Method.POST
    request.headers shouldBe new Headers(Header("User-Agent", "Something"))
  }

  it should "allows to overwrite specification values" in {
    val specification = Specification("https://httpbin.org/", Method.POST, new Headers(Header("User-Agent", "Something")))
    val request = new HttpClient.Builder(specification).baseUri("http://www.wp.pl").header("User-Agent", "New").buildRequest(Method.GET)
    request.url shouldBe "http://www.wp.pl/"
    request.method shouldBe Method.GET
    request.headers shouldBe new Headers(Header("User-Agent", "New"))
  }

  it should "allows to add headers to specification" in {
    val specification = Specification("https://httpbin.org/", Method.POST, new Headers(Header("HeaderA", "A")))
    val request = new HttpClient.Builder(specification).header("HeaderB", "B").buildRequest()
    request.headers shouldBe new Headers(Header("HeaderA", "A"), Header("HeaderB", "B"))
  }

}
