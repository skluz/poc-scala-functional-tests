package utf.commons.web.model

import org.openqa.selenium.WebDriver
import utf.commons.web.config.DriverFactory

/**
  * Created by Sławomir Kluz on 24/10/2017.
  */
trait DriverAware {

  protected def driver: WebDriver = DriverFactory.driver

}
