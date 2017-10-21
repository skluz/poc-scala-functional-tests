package utf.petstore.mock.servlets

import utf.commons.mock.servlet.servlet.BaseServlet
import utf.petstore.mock.model.Status
import utf.petstore.mock.state.PetStore
import org.json4s.Serializer
import org.json4s.ext.EnumNameSerializer
import org.scalatra._

/**
  * Created by Sławomir Kluz on 13/10/2017.
  */
class StoreServlet(store: PetStore) extends BaseServlet("/store", store) {

  override val customSerializers: Seq[Serializer[_]] = Seq(new EnumNameSerializer(Status))

  get("/:status") {
    store.pets.filter(p => p.status == Status.withName(params("status")))
  }

}
