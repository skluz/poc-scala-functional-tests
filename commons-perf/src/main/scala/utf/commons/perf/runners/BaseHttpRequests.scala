package utf.commons.perf.runners

import io.gatling.http.protocol.HttpProtocolBuilder

/**
  * Created by Sławomir Kluz on 13/10/2017.
  */
trait BaseHttpRequests {
  val protocol: HttpProtocolBuilder
}
