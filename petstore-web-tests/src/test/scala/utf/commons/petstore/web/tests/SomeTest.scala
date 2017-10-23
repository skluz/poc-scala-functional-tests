package utf.commons.petstore.web.tests

import org.openqa.selenium.By
import utf.commons.petstore.web.model.{Google, Input}
import utf.commons.web.model.{Element, Locator, Page}
import utf.commons.web.test.BaseWebSuite

class SomeTest extends BaseWebSuite {

  it should "do something" in {
    val mainPage = Google.open()
    val input = mainPage.input
    logger.info(input.toString())
    input.sendKeys("Hello!")
    Google.close()
  }

}
