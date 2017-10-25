package utf.commons.web.model

import org.openqa.selenium.{SearchContext, WebElement}

/**
  * Created by Sławomir Kluz on 19/10/2017.
  */
abstract class Element(locator: Locator)(implicit context: SearchContext) {

  protected def webElement: WebElement = context.findElement(locator.by)
  def asList() = {
    println(this.getClass.getSimpleName)
  }

  override def toString(): String = {
    "%s [context: %s, %s]".format(this.getClass.getSimpleName, context.getClass.getSimpleName, locator.by)
  }

}