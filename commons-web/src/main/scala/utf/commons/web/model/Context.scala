package utf.commons.web.model

import org.openqa.selenium.SearchContext

/**
  * Created by Sławomir Kluz on 24/10/2017.
  */
trait Context {

  implicit protected def context: SearchContext

  override def toString(): String = {
    "%s [root: %s]".format(this.getClass.getSimpleName, context.getClass.getSimpleName)
  }

}
