package kbaseball.sim.datagen

import kbaseball.exception.belowIntBound

/**
 * Represents a generator of new objects.
 *
 * There is no requirement that a new or distinct result be returned each time the generator is invoked.
 *
 * @author Philip Lombardi <plombardi89@gmail.com>
 * @since 1.0
 */


public interface Generator<T> {

  /**
   * Generates a result.
   */
  public fun generate(): T

  /**
   * Generates many results upto the count provided.
   *
   * @param count the number of results to generate. Must be greater than or equal to 1.
   */
  public fun generateMany(count: Int): Collection<T> {
    if (count < 1) {
      throw belowIntBound("count", 1, count)
    }

    return count.downTo(0).fold(linkedListOf()) { acc, it ->
      acc.add(generate())
      acc
    }
  }
}