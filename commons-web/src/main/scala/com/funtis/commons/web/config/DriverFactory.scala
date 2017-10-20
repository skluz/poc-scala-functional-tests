package com.funtis.commons.web.config

import java.net.URL

import com.typesafe.scalalogging.LazyLogging
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.{ChromeDriver, ChromeOptions}
import org.openqa.selenium.edge.{EdgeDriver, EdgeOptions}
import org.openqa.selenium.firefox.{FirefoxDriver, FirefoxOptions, FirefoxProfile}
import org.openqa.selenium.remote.{DesiredCapabilities, RemoteWebDriver}

/**
  * Created by Sławomir Kluz on 19/10/2017.
  */
object DriverFactory extends LazyLogging {

  lazy val driver: WebDriver = getInstance()
  private var threadDriver: ThreadLocal[WebDriver] = _

  def getInstance(): WebDriver = {
    if(threadDriver == null) {
      threadDriver = new ThreadLocal[WebDriver]
      threadDriver.set(WebConfig.browser match {
        case "local.chrome" => localChromeDriver()
        case "local.firefox" => localFirefoxDriver()
        case "local.edge" => localEdgeDriver()
        case remote if remote.startsWith("remote") => remoteDriver()
        case default => throw new IllegalArgumentException(s"Driver [$default] not implemented")
      })
    }
    threadDriver.get()
  }

  private def remoteDriver(): WebDriver = {
    val caps = new DesiredCapabilities()
    WebConfig.Browser.capabilities.foreach{ case(key, value) => caps.setCapability(key, value)}
    new RemoteWebDriver(new URL(WebConfig.Browser.remoteUrl), caps)
  }

  private def localChromeDriver(): WebDriver = {
    System.setProperty("webdriver.chrome.driver", WebConfig.Browser.driverPath)
    val options = new ChromeOptions()
    WebConfig.Browser.arguments.foreach(a => {
      options.addArguments(a)
    })
    new ChromeDriver(options)
  }

  private def localFirefoxDriver(): WebDriver = {
    System.setProperty("webdriver.gecko.driver", WebConfig.Browser.driverPath)
    val options = new FirefoxOptions().setProfile(new FirefoxProfile())
    new FirefoxDriver(options)
  }

  private def localEdgeDriver(): WebDriver = {
    System.setProperty("webdriver.edge.driver", WebConfig.Browser.driverPath)
    val options = new EdgeOptions()
    new EdgeDriver(options)
  }

  def destroy(): Unit = {
    if(threadDriver != null) {
      val webDriver = threadDriver.get()
      webDriver.close()
      webDriver.quit()
      threadDriver.remove()
    }
  }

}
