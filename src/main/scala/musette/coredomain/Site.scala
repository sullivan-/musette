package musette.coredomain

import longevity.subdomain._

/** a website. */
case class Site(
  val uri: Uri,
  val name: String
)
extends RootEntity

object SiteType extends RootEntityType[Site] {
  key("uri")
}

