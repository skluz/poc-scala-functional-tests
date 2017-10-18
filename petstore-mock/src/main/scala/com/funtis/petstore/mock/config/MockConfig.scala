package com.funtis.petstore.mock.config

import com.typesafe.config.ConfigFactory

/**
  * Created by Sławomir Kluz on 13/10/2017.
  */
object MockConfig {

  private lazy val config = ConfigFactory.load("reference-mock.conf")

  def environment: String = config.getString("environment")
  private val env = config.getConfig("environments").getConfig(environment)

  val interface: String = env.getString("interface")
  val port: Int = env.getInt("port")

}
