package kbaseball.util

import kbaseball.exception.FixtureNotFound
import java.io.InputStream
import java.io.Reader
import java.nio.charset.Charset

/**
 * Simple class for making it easier to load a fixture. A fixture is a resource located on the classpath in the
 * fixtures/ directory.
 *
 * @author Philip Lombardi <plombardi89@gmail.com>
 * @since 1.0
 */


object Fixtures {

  /**
   * Retrieves a classpath fixture resource and returns the InputStream
   *
   * @param path the path to the fixture that will be loaded.
   * @throws FixtureNotFound if the specified fixture is not found.
   */
  fun loadFixture(path: String): InputStream {
    val truePath = fixClasspathFixturePath(path)
    return Fixtures::class.java.getResourceAsStream(truePath) ?: throw FixtureNotFound(truePath)
  }

  /**
   * Retrieves a classpath fixture resource and returns a text representation of the data. The charset must be
   * provided in order to properly read the data.
   *
   * @param path the path to the fixture that will be loaded.
   * @param charset the character set used by the data being read.
   * @throws FixtureNotFound if the specified fixture is not found.
   */
  fun loadFixture(path: String, charset: Charset): String {
    return loadFixture(path).bufferedReader(charset).use { it.readText() }
  }

  /**
   * Cleans a path then ensures it has the /fixtures/ root prefixed to it.
   *
   * @param path the path to the fixture that will be loaded.
   */
  private fun fixClasspathFixturePath(path: String): String {
    val trimmedPath = path.trim()
    var result = if (!path.startsWith("/")) "/" + trimmedPath else trimmedPath
    return if (!result.startsWith("/fixtures")) "/fixtures" + result else result
  }
}