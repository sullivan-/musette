package musette

import longevity.context._
import longevity.subdomain._
import emblem.traversors.sync.CustomGeneratorPool
import emblem.traversors.sync.CustomGenerator

package object coredomain {

  implicit val shorthands = ShorthandPool(
    Shorthand[Email, String],
    Shorthand[Markdown, String],
    Shorthand[Uri, String]
  )

  object context {

    val entityTypes = EntityTypePool(
      BlogType,
      BlogPostType,
      CommentType ,
      SiteType,
      UserType,
      WikiType,
      WikiPageType)

    implicit def stringToEmail(email: String): Email = Email(email)
    implicit def stringToMarkdown(markdown: String): Markdown = Markdown(markdown)
    implicit def stringToUri(uri: String): Uri = Uri(uri)

    val subdomain = Subdomain("Musette", entityTypes)

    val emailGenerator = CustomGenerator.simpleGenerator[Email] { generator =>
      def part = generator.generate[String]
      Email(s"$part@$part.$part.com")
    }
    val uriGenerator = CustomGenerator.simpleGenerator[Uri] { generator =>
      def part = generator.generate[String]
      Uri(s"http://$part.$part/$part")
    }
    val generators = CustomGeneratorPool.empty + emailGenerator + uriGenerator

    val longevityContext = LongevityContext(subdomain, customGeneratorPool = generators)
  }

}
