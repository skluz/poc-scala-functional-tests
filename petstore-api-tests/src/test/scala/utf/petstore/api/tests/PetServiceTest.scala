package utf.petstore.api.tests

import utf.commons.api.test.BaseApiSuite
import utf.commons.tags.WIP
import utf.petstore.model.PetStatus
import utf.petstore.providers.DataProvider._
import utf.petstore.samplers.PetSamplers
import utf.petstore.services.PetService

/**
  * Created by Sławomir Kluz on 12/08/2017.
  */
class PetServiceTest extends BaseApiSuite {

  behavior of "create pet"

  it should "allows to create pet" in {
    val input = PetSamplers.garfield()
    val createdPet = PetService.createPet(input)
    createdPet.name shouldBe input.name
    createdPet.category.id shouldBe input.category
    createdPet.tags.map(t => t.id) shouldBe input.tags
    createdPet.photoUrls shouldBe input.photoUrls
    createdPet.id should be > 0L
    createdPet.status shouldBe PetStatus.withName(input.status)
    val fetchedPet = PetService.getPet(createdPet.id)
    fetchedPet shouldBe createdPet
  }

  it should "fail when required fields is missing" taggedAs WIP in {
    val input = PetSamplers.garfield().asJsonDocument().deleteField("$.name")
    val response = PetService.Raw.createPet(input.asJsonString())
    response.code shouldBe 400
  }

  behavior of "get pet"

  it should "return single pet" in new SomePet {
    PetService.getPet(somePet.id) shouldBe somePet
  }

  it should "return 404 when pet doesn't exist" in {
    PetService.Raw.getPet(-1).code shouldBe 404
  }

  behavior of "list pets"

  it should "return list of pets" in new {
    val petsBefore = PetService.listPets()
    new SomePet {
      val petsAfter = PetService.listPets()
      petsAfter.size shouldBe petsBefore.size + 1
    }
  }

}
