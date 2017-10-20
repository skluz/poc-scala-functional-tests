package com.funtis.petstore.services

import com.funtis.commons.api.service.ApiService
import com.funtis.commons.http.HttpClient
import com.funtis.petstore.config.SpecificationRepository
import com.funtis.petstore.model.{Pet, PetInput}

/**
  * Created by Sławomir Kluz on 19/10/2017.
  */
object PetService extends ApiService {

  private lazy val specification = SpecificationRepository.publicApi()

  val _pets = "/pet"
  val _pet = "/pet/{id}"

  def listPets() = Raw.listPets().body().asSeqOf[Pet]
  def getPet(id: Long) = Raw.getPet(id).body().as[Pet]
  def createPet(input: PetInput) = Raw.createPet(input).body().as[Pet]

  object Raw {
    def listPets() = new HttpClient.Builder(specification).name("list pets").GET(_pets)
    def getPet(id: Long) = new HttpClient.Builder(specification).name("get pet").pathParam("id", id).GET(_pet)
    def createPet(input: Any) = new HttpClient.Builder(specification).name("create pet").body(input).POST(_pets)
  }

}
