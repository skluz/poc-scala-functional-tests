package utf.commons.petstore.web.model

import org.openqa.selenium.{By, SearchContext}
import utf.commons.web.model.{Element, Locator}

class Input(locator: Locator)(implicit root: SearchContext) extends Element(locator)(root) {

  private def textInput = webElement.findElement(By.tagName("input"))

  def sendKeys(charSequence: CharSequence) = {
    textInput.sendKeys(charSequence)
  }

  def getValue(): String = {
    textInput.getAttribute("value")
  }

}