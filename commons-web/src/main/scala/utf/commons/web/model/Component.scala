package utf.commons.web.model

import org.openqa.selenium.{By, JavascriptExecutor, SearchContext, WebElement}

/**
  * Created by Sławomir Kluz on 19/10/2017.
  */
class Component(locator: Locator)(implicit var searchContext: SearchContext) extends RootContext {

  override implicit protected def rootContext: SearchContext = searchContext.findElement(locator.by)
  protected def webElement: WebElement = searchContext.findElement(locator.by)
  searchContext = webElement

  override def toString(): String = {
    "%s [root: %s, %s]".format(this.getClass.getSimpleName, searchContext.getClass.getSimpleName, locator.by)
  }

}