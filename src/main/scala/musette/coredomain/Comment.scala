package musette.coredomain

import longevity.subdomain._

case class Comment(
  uri: Uri,
  subject: Assoc[BlogPost],
  author: Assoc[User],
  content: Markdown
)
extends Content with RootEntity {
  override val authors = Set(author)
}

object CommentType extends RootEntityType[Comment] {
  key("uri")
}

