package utf.commons.petstore.web.tests

import org.openqa.selenium.By
import utf.commons.web.model.{Element, Locator}
import utf.commons.web.test.BaseWebSuite

class SomeTest extends BaseWebSuite {

  it should "do something" in {

    Element.by(Locator.className(""))
    Element.by(By.className(""))


  }

}
