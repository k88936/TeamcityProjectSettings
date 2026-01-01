package ScoopBucket

import ClionProjects.Qt.shotmd.buildTypes.Shotmd_Scoop
import GolandProjects.LocalRouter.buildTypes.LocalRouter_Scoop
import jetbrains.buildServer.configs.kotlin.Project
import utils.deploy.version.ScoopBucketVCS

object Project : Project({
    id("ScoopBucket")
    name = "ScoopBucket"

    vcsRoot(ScoopBucketVCS)

    buildType(LocalRouter_Scoop)
    buildType(Shotmd_Scoop)
})