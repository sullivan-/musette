package musette.coredomain

import longevity.subdomain._

/** content authored by a site user. */
case class BlogPost(
  uri: Uri,
  blog: Assoc[Blog],
  authors: Set[Assoc[User]],
  content: Markdown,
  slug: Markdown
)
extends TopContent with RootEntity

object BlogPostType extends RootEntityType[BlogPost] {
  key("uri")
}

