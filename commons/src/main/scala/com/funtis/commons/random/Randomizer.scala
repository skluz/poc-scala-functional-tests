package com.funtis.commons.random

import java.util.{Random, UUID}

import com.github.javafaker.Faker
import com.typesafe.scalalogging.LazyLogging

/**
  * Created by Sławomir Kluz on 11/09/2017.
  */
object Randomizer extends LazyLogging {

  private lazy val faker = new Faker()
  private lazy val random = new Random()

  def id(): String = UUID.randomUUID().toString

  object String {
    def firstName() = faker.address().firstName()
    def lastName() = faker.address().lastName()
    def emailAddress() = faker.internet().safeEmailAddress()
  }

}
