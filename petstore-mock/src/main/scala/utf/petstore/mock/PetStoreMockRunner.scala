package utf.petstore.mock

import utf.petstore.mock.config.MockConfig
import utf.petstore.mock.servlets.{PetServlet, StoreServlet}
import utf.petstore.mock.state.PetStore
import utf.commons.mock.servlet.BaseMockRunner

/**
  * Created by Sławomir Kluz on 13/10/2017.
  */
object PetStoreMockRunner extends BaseMockRunner {

  override val interface: String = MockConfig.interface
  override val port: Int = MockConfig.port
  override val resourceBase = "./petstore-mock/src/main/webapp"

  private val store = PetStore.fullStore()
  override val servlets = Seq(new PetServlet(store), new StoreServlet(store))
}
