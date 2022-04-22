package computerdatabase

import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

class BasicSimulation extends Simulation {
  val baseURL: String = Option(System.getProperty("baseURL")).getOrElse("http://computer-database.gatling.io")
  val path: String = Option(System.getProperty("path")).getOrElse("/")
  val mode: Int = Option(System.getProperty("model")).getOrElse("1").toInt
  val concurrentUsers: Int = Option(System.getProperty("users")).getOrElse("1600").toInt
  val duration: Int = Option(System.getProperty("duration")).getOrElse("15").toInt

  val httpProtocol: HttpProtocolBuilder = http
    .baseUrl(baseURL)
    .shareConnections

  val scn: ScenarioBuilder = scenario("Scenario") // A scenario is a chain of requests and pauses
    .exec(http("Get")
      .get(path))

  if (mode == 1) {
    setUp(scn
      .inject(
        constantConcurrentUsers(concurrentUsers) during(duration)
      )
      .protocols(httpProtocol))
  } else if (mode == 2) {
    setUp(scn
      .inject(
        constantUsersPerSec(concurrentUsers) during(duration)
      )
      .protocols(httpProtocol))
  } else if (mode == 3) {
    setUp(scn
      .inject(
        rampUsers(concurrentUsers) during(duration)
      )
      .protocols(httpProtocol))
  }
}
