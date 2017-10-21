package utf.commons.web.model

import org.openqa.selenium.{JavascriptExecutor, SearchContext, WebElement}

/**
  * Created by Sławomir Kluz on 19/10/2017.
  */
class Element(locator: Locator)(implicit context: SearchContext) extends WebContext {

  private def element: WebElement = context.findElement(locator.by)

  def location() = (element.getLocation.getX, element.getLocation.getY)

  def highlight() = {
    driver match {
      case executor: JavascriptExecutor => executor.executeScript("arguments[0].style.border = '3px solid red'", element);
      case _ => logger.warn("WebDriver is not valid JavascriptExecutor.")
    }
  }

  override def toString(): String = {
    "%s (%s)".format(this.getClass.getSimpleName, locator.by)
  }

}