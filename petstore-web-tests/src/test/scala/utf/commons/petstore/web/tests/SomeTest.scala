package utf.commons.petstore.web.tests

import org.openqa.selenium.By
import utf.commons.petstore.web.model.{Google, Input}
import utf.commons.web.model.{Element, Locator, Page}
import utf.commons.web.test.BaseWebSuite

class SomeTest extends BaseWebSuite {

  it should "do something" in {
    val mainPage = Google.open()
    logger.info(mainPage.toString())
    //val form = mainPage.form
    logger.info(mainPage.nameInput.toString())
    logger.info(mainPage.form.toString())
    logger.info(mainPage.form.nameInput.toString())
    mainPage.nameInput.sendKeys("Hej!!!asdf asHej!!!asdf asHej!!!asdf asHej!!!asdf asHej!!!asdf asHej!!!asdf asHej!!!asdf asHej!!!asdf asHej!!!asdf asHej!!!asdf asHej!!!asdf as")
//    logger.info(form.subclass.toString())
//    logger.info(form.subclass.nameInput.toString())
//    form.subclass.nameInput.sendKeys("Hello!")
    Google.close()
  }

}
