package utf.commons.petstore.web.model

import utf.commons.web.model.Application

object Google extends Application {
  override def open() = {
    driver.get("http://www.google.com")
    new MainPage()
  }
}
