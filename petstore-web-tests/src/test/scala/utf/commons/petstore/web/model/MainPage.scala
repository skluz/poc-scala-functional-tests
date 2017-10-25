package utf.commons.petstore.web.model

import utf.commons.web.model.{Component, Locator, Page}

class MainPage extends Page {

  def nameInput = Input(Locator.className("input")).asList()

  def form = new Component(Locator.id("component-1")) {
    def nameInput = Input(Locator.className("input"))
  }

  //logger.info(this.toString())

//  def form = new Component(Locator.id("component-1")) {
//    def subclass = new Component(Locator.className("subcomponXent")) {
//      def nameInput = new Input(Locator.className("input"))
//    }
//  }


}
