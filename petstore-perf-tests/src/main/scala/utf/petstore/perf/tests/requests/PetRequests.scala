package utf.petstore.perf.tests.requests

import utf.petstore.config.PetStoreConfig
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.check.HttpCheck
import utf.commons.perf.runners.BaseHttpRequests

/**
  * Created by Sławomir Kluz on 13/10/2017.
  */
trait PetRequests extends BaseHttpRequests {

  override val protocol = http.baseURL(PetStoreConfig.Api.publicUrl)

  def listPets(checks: HttpCheck*) = {
    http("list pets")
      .get("/pet")
      .check(status.is(200))
      .check(checks: _*)
  }

  def getPet(checks: HttpCheck*) = {
    http("get pet")
      .get("/pet/${petId}")
      .check(status.is(200))
      .check(checks: _*)
  }


}
