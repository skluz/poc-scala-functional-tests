package utf.petstore.providers

import utf.petstore.samplers.PetSamplers
import utf.petstore.services.PetService

/**
  * Created by Sławomir Kluz on 19/10/2017.
  */
object DataProvider {

  trait SomePet {
    private val input = PetSamplers.garfield()
    private val createdPet = PetService.createPet(input)
    val somePet = PetService.getPet(createdPet.id)
  }

}
