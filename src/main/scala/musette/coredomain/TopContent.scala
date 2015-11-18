package musette.coredomain

/** content that is typically displayed on its own webpage. */
trait TopContent extends Content {
  val slug: Markdown
}
