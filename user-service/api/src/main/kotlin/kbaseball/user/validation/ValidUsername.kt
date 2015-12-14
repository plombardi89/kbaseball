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