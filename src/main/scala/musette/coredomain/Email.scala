package musette.coredomain

import musette.exceptions.ConstraintValidationException

/** an email */
case class Email(email: String) {
  email match {
    case Email.emailRegex(_*) =>
    case _ => throw new ConstraintValidationException(s"invalid email: $email")
  }

  override def toString = email
}

object Email {

  // lifted from http://www.regular-expressions.info/email.html
  private val emailRegex = """^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$""".r

}
