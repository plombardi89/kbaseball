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

/**
 * Emailer implementations are responsible for transmitting email messages to one or more recipients.
 *
 * @author plombardi89@gmail.com
 * @since 1.0
 */
interface Emailer {

  /**
   * Send an email to the specified addresses with the specified content. Optionally provide a list of carbon-copy and
   * blind carbon-copy recipients.
   *
   * @param to the primary recipients.
   * @param content the content to send.
   * @param cc carbon copy recipients.
   * @param bcc blind carbon copy recipients.
   */
  fun sendEmail(to: Set<String>,
                subject: String,
                content: EmailContent,
                cc: Set<String> = emptySet(),
                bcc: Set<String> = emptySet())

  /**
   * Send an email to an address with the specified content. Optionally provide a list of carbon-copy and blind
   * carbon-copy recipients.
   *
   * @param to the primary recipient.
   * @param content the content to send.
   * @param cc carbon copy recipients.
   * @param bcc blind carbon copy recipients.
   */
  fun sendEmail(to: String,
                subject: String,
                content: EmailContent,
                cc: Set<String> = emptySet(),
                bcc: Set<String> = emptySet()) {

    sendEmail(setOf(to), subject, content, cc, bcc)
  }
}