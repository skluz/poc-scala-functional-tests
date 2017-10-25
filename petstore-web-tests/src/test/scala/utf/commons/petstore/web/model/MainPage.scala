package utf.commons.petstore.web.model

import org.openqa.selenium.SearchContext
import utf.commons.web.model.{Component, Locator, Page}

class MainPage extends Page {

  def nameInput = new Input(Locator.className("input"))

  def form = new Component(Locator.id("component-1")) {
    def nameInput = new Input(Locator.className("input"))
  }

  //logger.info(this.toString())

//  def form = new Component(Locator.id("component-1")) {
//    def subclass = new Component(Locator.className("subcomponXent")) {
//      def nameInput = new Input(Locator.className("input"))
//    }
//  }


}
