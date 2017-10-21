package utf.petstore.services

import utf.commons.http.HttpClient
import utf.petstore.config.SpecificationRepository
import utf.petstore.model.{Pet, PetInput}
import utf.commons.api.service.ApiService

/**
  * Created by Sławomir Kluz on 19/10/2017.
  */
object PetService extends ApiService {

  private lazy val specification = SpecificationRepository.publicApi()

  val _pets = "/pet"
  val _pet = "/pet/{id}"

  def listPets() = Raw.listPets().assertCode(200).body().asSeqOf[Pet]
  def getPet(id: Long) = Raw.getPet(id).assertCode(200).body().as[Pet]
  def createPet(input: PetInput) = Raw.createPet(input).assertCode(201).body().as[Pet]

  object Raw {
    def listPets() = new HttpClient.Builder(specification).name("list pets").GET(_pets)
    def getPet(id: Long) = new HttpClient.Builder(specification).name("get pet").pathParam("id", id).GET(_pet)
    def createPet(input: Any) = new HttpClient.Builder(specification).name("create pet").body(input).POST(_pets)
  }

}
