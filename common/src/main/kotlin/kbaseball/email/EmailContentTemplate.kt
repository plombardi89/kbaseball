/*
Copyright (C) 2015  Philip Lombardi <plombardi89@gmail.com>

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package kbaseball.email

import com.github.mustachejava.DefaultMustacheFactory
import com.github.mustachejava.Mustache
import kbaseball.util.Fixtures
import java.io.StringReader
import java.io.StringWriter


data class EmailContentTemplate(
    private val htmlMustache: Mustache,
    private val textMustache: Mustache?,
    private val variables: Map<String, Any>
) {

  constructor(htmlMustache: Mustache, textMustache: Mustache?): this(htmlMustache, textMustache, emptyMap())

  private fun render(mustache: Mustache): String {
    val writer = StringWriter()
    mustache.execute(writer, variables)
    return writer.toString()
  }

  fun render(): EmailContent {
    return EmailContent(render(htmlMustache), if (textMustache != null) render(textMustache) else null)
  }

  fun withVariables(scopes: Map<String, Any>): EmailContentTemplate {
    return this.copy(variables = this.variables + scopes)
  }

  fun withVariable(name: String, value: Any): EmailContentTemplate {
    return this.copy(variables = this.variables + mapOf(name to value))
  }

  companion object Factory {
    fun newTemplate(htmlTemplateFixture: String, textTemplateFixture: String?): EmailContentTemplate {
      val htmlTemplate = Fixtures.loadFixture(htmlTemplateFixture, Charsets.UTF_8)
      val textTemplate = if (textTemplateFixture != null ) {
        Fixtures.loadFixture(textTemplateFixture, Charsets.UTF_8)
      } else {
        null
      }

      val mustacheFactory = DefaultMustacheFactory()
      val htmlMustache = mustacheFactory.compile(StringReader(htmlTemplate), htmlTemplateFixture)
      val textMustache = mustacheFactory.compile(StringReader(textTemplate), textTemplateFixture)

      return EmailContentTemplate(htmlMustache, textMustache)
    }
  }
}