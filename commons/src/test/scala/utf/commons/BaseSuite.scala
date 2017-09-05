package utf.commons

import com.typesafe.scalalogging.LazyLogging
import org.scalatest.{BeforeAndAfter, BeforeAndAfterAll, FlatSpec, Outcome}
import org.slf4j.MDC

/**
  * Created by Sławomir Kluz on 12/08/2017.
  */
abstract class BaseSuite extends FlatSpec with LazyLogging with BeforeAndAfterAll {

  override def beforeAll(): Unit = {
    super.beforeAll()
    MDC.put("suiteId", suiteId)
  }

  override def withFixture(test: NoArgTest): Outcome = {
    logTestStart(test.name)
    super.withFixture(test)
  }

  private def logTestStart(name: String): Unit = {
    val width = 100
    val margin = (width-2-name.length)/2
    val borderLine = s"+${"-"*(width-2)}+"
    val nameLine = s"|${" "*(margin+name.length%2)}$name${" "*margin}|"
    logger.info(borderLine)
    logger.info(nameLine)
    logger.info(borderLine)
  }

}
