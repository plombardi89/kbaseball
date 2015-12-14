package kbaseball.sim.game

import kbaseball.sim.model.Team
import org.slf4j.LoggerFactory


class Game(
    private val homeTeam: Team,
    private val awayTeam: Team
) {

  private var inning = 1
  private var homeScore = 0
  private var awayScore = 0

  companion object {
    private val log = LoggerFactory.getLogger(Game::class.java)
  }

  fun play(): Boxscore {
    log.info("Playing sim {} @ {}", awayTeam.name, homeTeam.name)

    while(homeScore == awayScore || inning < 10) {
      playInningHalf(half = InningHalf.TOP, batting = awayTeam, /*lineup = awayLineup,*/ fielding = homeTeam)

      if (!(inning > 8 && homeScore > awayScore)) {
        playInningHalf(InningHalf.BOTTOM, homeTeam, awayTeam)
      }

      inning += 1
    }

    return Boxscore(this)
  }

  private fun playInningHalf(half: InningHalf, batting: Any, fielding: Any) {
    log.info("{} of inning {} (score: {} - {})", null)

    var outs = 0
    while (outs < 3) {
      // todo: AB Logic!
    }
  }

  private enum class InningHalf {
    TOP,
    BOTTOM
  }
}