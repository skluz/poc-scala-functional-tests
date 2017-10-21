package utf.petstore.perf.tests

import utf.commons.perf.runners.runners.BaseGatlingIDERunner
import utf.petstore.perf.tests.simulations.GetSinglePetSimulation

/**
  * Created by Sławomir Kluz on 13/10/2017.
  */
object PetStoreGatlingIDERunner extends BaseGatlingIDERunner {

  val simulationClass = classOf[GetSinglePetSimulation]

}
