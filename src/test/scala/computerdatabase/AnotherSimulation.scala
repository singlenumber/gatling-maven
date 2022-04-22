package computerdatabase

import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

class AnotherSimulation extends Simulation {
  val baseURL: String = Option(System.getProperty("baseURL")).getOrElse("http://computer-database.gatling.io")
  val path: String = Option(System.getProperty("path")).getOrElse("/")
  val concurrentUsers: Int = Option(System.getProperty("users")).getOrElse("1600").toInt
  val duration: Int = Option(System.getProperty("duration")).getOrElse("15").toInt

  val httpProtocol: HttpProtocolBuilder = http
    .baseUrl(baseURL)
    .shareConnections

  val scn: ScenarioBuilder = scenario("Scenario")
    .during(duration) {
      exec(http("Get")
        .get(path))
    }

  setUp(scn
    .inject(
      atOnceUsers(concurrentUsers)
    )
    .protocols(httpProtocol))
}
