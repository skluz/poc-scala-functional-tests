package com.funtis.petstore.mock

import com.funtis.commons.mock.BaseMockRunner
import com.funtis.petstore.mock.config.MockConfig
import com.funtis.petstore.mock.servlets.{PetServlet, StoreServlet}
import com.funtis.petstore.mock.state.PetStore

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
