package com.funtis.commons.web.model

import org.openqa.selenium.By

/**
  * Created by Sławomir Kluz on 19/10/2017.
  */
case class Locator(by: By)

object Locator {
  def id(id: String): Locator = Locator(By.id(id))
  def name(name: String): Locator = Locator(By.name(name))
  def xpath(xpath: String): Locator = Locator(By.xpath(xpath))
  def tagName(tagName: String): Locator = Locator(By.tagName(tagName))
  def className(className: String): Locator = Locator(By.className(className))
  def cssSelector(cssSelector: String): Locator = Locator(By.cssSelector(cssSelector))
}
