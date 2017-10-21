package utf.commons.web.config

import java.util.Map.Entry

import com.typesafe.config.{ConfigFactory, ConfigObject, ConfigValue}
import com.typesafe.scalalogging.LazyLogging

import scala.collection.JavaConverters._
/**
  * Created by Sławomir Kluz on 19/10/2017.
  */
object WebConfig extends LazyLogging {

  private val config = ConfigFactory.load("reference-web.conf")

  def browser = config.getString("browser")

  object Browser {
    private val _browser = config.getConfig(browser)
    lazy val driverPath = _browser.getString("driver.path")
    lazy val arguments: Seq[String] = if(_browser.hasPath("arguments")) _browser.getList("arguments").asScala.map(s => s.unwrapped().toString) else Seq.empty
    lazy val remoteUrl = config.getString("remote.url")
    lazy val capabilities: Map[String, Any] = (for {
      item: ConfigObject <- _browser.getObjectList("capabilities").asScala
      entry: Entry[String, ConfigValue] <- item.entrySet().asScala
      key = entry.getKey
      value = entry.getValue.unwrapped()
    } yield (key, value)).toMap
  }

}