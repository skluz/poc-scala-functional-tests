package utf.commons.web.model

/**
  * Created by Sławomir Kluz on 19/10/2017.
  */
trait Application extends WebContext {
  def open(): Page
}
