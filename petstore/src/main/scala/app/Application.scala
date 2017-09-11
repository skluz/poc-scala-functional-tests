package app

import java.time.Instant

import com.funtis.commons.http.RestClient
import com.typesafe.scalalogging.LazyLogging
import model.{Address, Person}

/**
  * Created by Sławomir Kluz on 04/09/2017.
  */
object Application extends App with LazyLogging {

  val body = Person("Stefan", None, null, 10, Instant.now(), Seq("a", "b", "c"), Address(false, Map("a" -> "b", 3 -> "a")))
  val restClient = new RestClient().tag("test").pathParam("gameId", 1).Get("/games/{gameId}").assertCode(200).body.as(classOf[Person])

}