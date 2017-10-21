package utf.commons.web.model

import utf.commons.web.config.DriverFactory
import com.typesafe.scalalogging.LazyLogging
import org.openqa.selenium.{SearchContext, WebDriver}

/**
  * Created by Sławomir Kluz on 19/10/2017.
  */
trait WebContext extends LazyLogging {

  protected def driver: WebDriver = DriverFactory.driver
  implicit def context: SearchContext = driver

}
