package utf.petstore.perf.tests.simulations

import io.gatling.core.Predef._
import utf.commons.perf.runners.BaseSimulation
import utf.petstore.perf.tests.requests.PetRequests
import utf.petstore.samplers.PetSamplers
import utf.petstore.services.PetService

import scala.util.Random

/**
  * Created by Sławomir Kluz on 13/10/2017.
  */
class GetSinglePetSimulation extends BaseSimulation with PetRequests {

  val pets = (1 to 10).map(_ => PetService.createPet(PetSamplers.garfield()))

  val petsFeed = Iterator.continually(Map(
    "petId" -> Random.shuffle(pets).head.id
  ))


  val simulation = scenario("list products")
    .feed(petsFeed)
    .exec(getPet())

  setUp(simulation.inject(atOnce(5)).protocols(protocol))

}
