package com.funtis.petstore.perf.tests

import com.funtis.commons.perf.runners.BaseGatlingIDERunner
import com.funtis.petstore.perf.tests.simulations.GetSinglePetSimulation

/**
  * Created by Sławomir Kluz on 13/10/2017.
  */
object PetStoreGatlingIDERunner extends BaseGatlingIDERunner {

  val simulationClass = classOf[GetSinglePetSimulation]

}
