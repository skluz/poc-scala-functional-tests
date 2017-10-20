package com.funtis.petstore.providers

import com.funtis.petstore.samplers.PetSamplers
import com.funtis.petstore.services.PetService

/**
  * Created by skluz@g2a.com on 19/10/2017.
  */
object DataProvider {

  trait SomePet {
    private val input = PetSamplers.garfield()
    private val createdPet = PetService.createPet(input)
    val somePet = PetService.getPet(createdPet.id)
  }

}
