package utf.petstore.perf.tests.simulations

import utf.petstore.perf.tests.requests.PetRequests
import io.gatling.core.Predef._
import io.gatling.http.Predef.jsonPath
import utf.commons.perf.runners.BaseSimulation

/**
  * Created by Sławomir Kluz on 13/10/2017.
  */
class GetSinglePetSimulation extends BaseSimulation with PetRequests {

  val simulation = scenario("list products")
    .exec(listPets(jsonPath("$[*].id").ofType[Int].saveAs("petId")))
    .exec(getPet())

  setUp(simulation.inject(singleUser()).protocols(protocol))

}
