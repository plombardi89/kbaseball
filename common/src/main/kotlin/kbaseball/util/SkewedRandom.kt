package kbaseball.util

import java.util.*


class SkewedRandom(private val random: Random) {

  constructor(seed: Long): this(Random(seed))
  constructor(): this(Random())

  public fun nextInt(range: IntRange, skew: Double, bias: Double): Int {
    return nextDouble(range.start.toDouble(), range.end.toDouble(), skew, bias).toInt()
  }

  public fun nextDouble(min: Double, max: Double, skew: Double, bias: Double): Double {
    val range = max - min
    val middle = min + range / 2
    val gaussian = random.nextGaussian()
    val biasFactor = Math.exp(bias)
    return middle + (range * (biasFactor / (biasFactor + Math.exp(-gaussian / skew)) - 0.5))
  }
}