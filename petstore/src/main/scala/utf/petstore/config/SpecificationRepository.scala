package utf.petstore.config

import utf.commons.http.Specification

/**
  * Created by Sławomir Kluz on 19/10/2017.
  */
object SpecificationRepository {

  def publicApi() = Specification.default.copy(baseUri = PetStoreConfig.Api.publicUrl)

}
