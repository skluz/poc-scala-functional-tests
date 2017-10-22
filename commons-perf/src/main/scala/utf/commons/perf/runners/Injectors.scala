package utf.commons.perf.runners

import io.gatling.core.Predef._
import scala.concurrent.duration._

/**
  * Created by Sławomir Kluz on 13/10/2017.
  */
trait Injectors {

  def singleUser() = atOnceUsers(1)

  def atOnce(users: Int) = atOnceUsers(users)

  def steppingUsers(stepsCount: Int, usersPerStep: Int, stepDuration: Int) = (0 until stepsCount).flatMap(i => {
    Seq(
      rampUsersPerSec(1 + i * usersPerStep) to((i+1) * usersPerStep) during(stepDuration/10.0 seconds),
      constantUsersPerSec((i+1) * usersPerStep) during(stepDuration seconds))
  })

}
