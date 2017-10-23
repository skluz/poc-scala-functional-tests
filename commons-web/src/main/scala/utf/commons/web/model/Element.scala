package utf.commons.web.model

import org.openqa.selenium.{By, JavascriptExecutor, SearchContext, WebElement}

/**
  * Created by Sławomir Kluz on 19/10/2017.
  */
class Element(by: By)(implicit context: SearchContext) extends WebContext {

  def this(locator: Locator)(implicit context: SearchContext) = this(locator.by)(context)

  protected def element: WebElement = context.findElement(by)

  def location() = (element.getLocation.getX, element.getLocation.getY)

  def highlight() = {
    driver match {
      case executor: JavascriptExecutor => executor.executeScript("arguments[0].style.border = '3px solid red'", element);
      case _ => logger.warn("WebDriver is not valid JavascriptExecutor.")
    }
  }

  override def toString(): String = {
    "%s[%s] (%s)".format(this.getClass.getSimpleName, context.getClass.getSimpleName, by)
  }

}