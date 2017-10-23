package utf.commons.petstore.web.model

import org.openqa.selenium.SearchContext
import utf.commons.web.model.{Element, Locator}

class Input(locator: Locator)(implicit context: SearchContext) extends Element(locator)(context) {

  def sendKeys(charSequence: CharSequence) = element.sendKeys(charSequence)

}