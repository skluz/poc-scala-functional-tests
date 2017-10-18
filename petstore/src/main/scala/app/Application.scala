package app

import java.net.URI
import java.time.Instant

import com.funtis.commons
import com.funtis.commons.http
import com.funtis.commons.http.HttpClient
import com.typesafe.scalalogging.LazyLogging
import model.{Address, Person}

/**
  * Created by Sławomir Kluz on 04/09/2017.
  */
object Application extends App with LazyLogging {

  val response = new HttpClient.Builder().GET()
  logger.info(response.toString)

//
//  val restClient = new RestClient()
//    .tag("test")
//    .pathParam("gameId", 1)
//    .Get("/games/{gameId}")
//    .assertCode(200)
//      .body.as(classOf[Person])

}