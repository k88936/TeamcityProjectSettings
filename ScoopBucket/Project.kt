package ScoopBucket

import jetbrains.buildServer.configs.kotlin.Project
import utils.deploy.version.ScoopBucketVCS

object Project : Project({
    id("ScoopBucket")
    name = "ScoopBucket"
    vcsRoot(ScoopBucketVCS)
})