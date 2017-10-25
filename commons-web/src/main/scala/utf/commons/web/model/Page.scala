package utf.commons.web.model
import org.openqa.selenium.SearchContext

/**
  * Created by Sławomir Kluz on 19/10/2017.
  */
class Page extends RootContext with DriverAware {
  override implicit def rootContext: SearchContext = driver
}
