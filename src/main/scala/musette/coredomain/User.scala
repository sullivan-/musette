package musette.coredomain

import longevity.subdomain._

case class User(
  uri: Uri,
  site: Assoc[Site],
  email: Email,
  handle: String,
  slug: Markdown
)
extends RootEntity

object UserType extends RootEntityType[User] {
  key("uri")
}

