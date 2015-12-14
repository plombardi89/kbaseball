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

import org.apache.commons.mail.HtmlEmail
import org.slf4j.LoggerFactory
import javax.mail.Authenticator

/**
 * A class that can send emails and has limited customizability.
 *
 * @author plombardi89@gmail.com
 * @since 1.0
 */
data class SimpleEmailer(
    val host: String,
    val port: Int,
    val authenticator: Authenticator,
    val from: String
): Emailer {

  private val log = LoggerFactory.getLogger(SimpleEmailer::class.java)

  private fun buildBaseEmail(): HtmlEmail {
    val base = HtmlEmail()
    base.hostName = host
    base.setSmtpPort(port)
    base.setAuthenticator(authenticator)
    base.setFrom(from)
    base.setStartTLSEnabled(true)
    return base
  }

  override fun sendEmail(to: Set<String>, subject: String, content: EmailContent, cc: Set<String>, bcc: Set<String>) {
    log.debug("sending email (to: {}, subject: {})", to, subject)

    val email = buildBaseEmail()
    email.setSubject(subject)
    email.addTo(*to.toTypedArray())
    email.setHtmlMsg(content.html)

    if (content.alternateText != null) {
      email.setTextMsg(content.alternateText)
    }

    if (cc.isNotEmpty()) {
      email.addCc(*cc.toTypedArray())
    }

    if (bcc.isNotEmpty()) {
      email.addBcc(*bcc.toTypedArray())
    }

    email.send()
  }
}