package com.funtis.petstore.mock.servlets

import com.funtis.commons.mock.servlet.BaseServlet
import com.funtis.petstore.mock.model.{PetInput, Status}
import com.funtis.petstore.mock.state.PetStore
import org.json4s.Serializer
import org.json4s.ext.EnumNameSerializer
import org.scalatra._

/**
  * Created by Sławomir Kluz on 13/10/2017.
  */
class PetServlet(store: PetStore) extends BaseServlet("/pet", store) {

  override val customSerializers: Seq[Serializer[_]] = Seq(new EnumNameSerializer(Status))

  get("/") {
    store.pets
  }

  get("/:id") {
    store.pets.find(p => p.id == params("id").toLong) match {
      case Some(pet) => Ok(pet)
      case None => NotFound(s"Pet id=${params("id")} not found")
    }
  }

  delete("/:id") {
    store.pets.find(p => p.id == params("id").toLong) match {
      case Some(pet) => {
        store.pets -= pet
        NoContent()
      }
      case None => NotFound(s"Pet id=${params("id")} not found")
    }
  }

  post("/") {
    try {
      val input = parsedBody.extract[PetInput]
      Created(store.addPet(input))
    } catch {
      case e: Throwable => BadRequest(e)
    }
  }

}
