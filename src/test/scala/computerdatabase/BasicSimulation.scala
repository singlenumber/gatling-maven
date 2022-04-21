package computerdatabase

import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

class BasicSimulation extends Simulation {
  val baseURL: String = Option(System.getProperty("baseURL")).getOrElse("http://computer-database.gatling.io")
  val path: String = Option(System.getProperty("path")).getOrElse("/")
  val concurrentUsers: Int = Option(System.getProperty("concurrentUsers")).getOrElse("1600").toInt
  val duration: Int = Option(System.getProperty("duration")).getOrElse("15").toInt

  val httpProtocol: HttpProtocolBuilder = http
    .baseUrl(baseURL)
    .shareConnections

  val scn: ScenarioBuilder = scenario("Scenario") // A scenario is a chain of requests and pauses
    .exec(http("Get")
      .get(""))

  setUp(scn
    .inject(
      constantConcurrentUsers(concurrentUsers) during(duration)
    )
    .protocols(httpProtocol))
}
