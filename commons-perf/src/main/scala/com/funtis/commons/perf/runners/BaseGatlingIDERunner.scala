package com.funtis.commons.perf.runners

import com.funtis.commons.perf.BaseSimulation
import com.typesafe.scalalogging.LazyLogging
import io.gatling.app.Gatling
import io.gatling.core.config.GatlingPropertiesBuilder

/**
  * Created by Sławomir Kluz on 13/10/2017.
  */
abstract class BaseGatlingIDERunner extends LazyLogging {

  val simulationClass: Class[_ <: BaseSimulation]

  def main(args: Array[String]) {
    logger.info("Starting simulation: " + simulationClass.getName)
    val props = new GatlingPropertiesBuilder()
    props.sourcesDirectory("./src/main/scala")
    props.binariesDirectory("./target/scala-2.12/classes")
    props.simulationClass(simulationClass.getName)
    Gatling.fromMap(props.build)
  }

}
