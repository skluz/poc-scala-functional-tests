package utf.commons.web.model

import org.openqa.selenium.{By, JavascriptExecutor, SearchContext, WebElement}

/**
  * Created by Sławomir Kluz on 19/10/2017.
  */
abstract class Component(locator: Locator)(implicit var context: SearchContext) {

  protected def webElement: WebElement = context.findElement(locator.by)
  private val originalContext = context
  context = webElement

  override def toString(): String = {
    "%s [context: %s, %s]".format(this.getClass.getSimpleName, originalContext.getClass.getSimpleName, locator.by)
  }

}