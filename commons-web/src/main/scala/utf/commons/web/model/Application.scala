package utf.commons.web.model

import utf.commons.web.config.DriverFactory

/**
  * Created by Sławomir Kluz on 19/10/2017.
  */
trait Application extends WebContext {
  def open(): Page
  def close() = DriverFactory.destroy()
}
