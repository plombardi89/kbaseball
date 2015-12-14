package kbaseball.util

import java.nio.file.Path
import java.nio.file.Paths

/**
 * Discovers the location of a Java Archive ("JAR").
 *
 * @author Philip Lombardi <plombardi89@gmail.com>
 * @since 1.0
 */


class JarLocation(private val clazz: Class<*>) {

  val path = discover(default = Paths.get("program.jar"))
  val version = clazz.`package`?.implementationVersion

  override fun toString() = path.toString()

  /**
   * Discovers the location of a JAR.
   *
   * @param default the default location if a JAR is not found, for example, when running from an IDE.
   */
  private fun discover(default: Path): Path {
    val location = clazz.protectionDomain.codeSource.location
    return try {
      val jar = Paths.get(location.toURI())
      if (jar.fileName.endsWith(".jar")) jar else default
    } catch (any: Exception) {
      default
    }
  }
}