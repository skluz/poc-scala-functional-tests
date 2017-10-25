package utf.commons.web.model

import org.openqa.selenium.{SearchContext, WebElement}

/**
  * Created by Sławomir Kluz on 19/10/2017.
  */
class Element(locator: Locator)(implicit searchContext: SearchContext) {

  protected def webElement: WebElement = searchContext.findElement(locator.by)

  override def toString(): String = {
    "%s [root: %s, %s]".format(this.getClass.getSimpleName, searchContext.getClass.getSimpleName, locator.by)
  }

}