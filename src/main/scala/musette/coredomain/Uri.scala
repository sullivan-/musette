package musette.coredomain

import musette.exceptions.ConstraintValidationException

/** a uri */
case class Uri(uri: String) {
  uri match {
    case Uri.uriRegex(_*) =>
    case _ => throw new ConstraintValidationException(s"invalid uri: $uri")
  }

  override def toString = uri
}

object Uri {

  // lifted from https://gist.github.com/dryliketoast/1355177
  private val uriRegex = """^(([^:/?#]+):)?(//([^/?#]*))?([^?#]*)(\?([^#]*))?(#(.*))?$""".r

}
