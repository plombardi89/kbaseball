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

package kbaseball.user.validation

import javax.validation.Constraint
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import kotlin.text.Regex


@Target(AnnotationTarget.TYPE, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = arrayOf(UsernameValidator::class))
public annotation class ValidUsername

class UsernameValidator: ConstraintValidator<ValidUsername, String> {
  override fun initialize(constraintAnnotation: ValidUsername?) {}

  override fun isValid(value: String?, context: ConstraintValidatorContext?): Boolean {
    return value != null
        && value.matches(Regex("^[a-zA-Z0-9][a-zA-Z0-9_-]{3,30}$"))
  }
}