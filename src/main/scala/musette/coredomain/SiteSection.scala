package musette.coredomain

import longevity.subdomain._

/** a section of a website. it could be a blog, a wiki, or a forum. */
trait SiteSection {
  val uri: Uri
  val site: Assoc[Site]
  val authors: Set[Assoc[User]]
  val slug: Markdown
}

