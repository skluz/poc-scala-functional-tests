package utf.commons.web.model

import utf.commons.web.config.DriverFactory
import com.typesafe.scalalogging.LazyLogging
import org.openqa.selenium.{By, SearchContext, WebDriver}

import scala.reflect.ClassTag

/**
  * Created by Sławomir Kluz on 19/10/2017.
  */
trait WebContext extends LazyLogging {

  //override def toString(): String = "%s [root: %s]".format(this.getClass.getSimpleName, root.getClass.getSimpleName)

}
