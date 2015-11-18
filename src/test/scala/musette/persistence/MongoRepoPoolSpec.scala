package musette.persistence

import musette.coredomain.context.longevityContext

import org.scalatest.Suites

class MongoRepoPoolSpec extends Suites(longevityContext.repoPoolSpec)
