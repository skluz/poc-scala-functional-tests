package utf.commons.web.model

import utf.commons.web.config.DriverFactory

/**
  * Created by Sławomir Kluz on 19/10/2017.
  */
abstract class Application extends DriverAware {

  override protected def driver = super.driver
  def open(): Page
  def close() = DriverFactory.destroy()

}
