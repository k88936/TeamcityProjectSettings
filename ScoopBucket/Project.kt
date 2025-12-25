package ScoopBucket

import ClionProjects.Qt.shotmd.buildTypes.Shotmd_Scoop
import GolandProjects.LocalRouter.buildTypes.LocalRouter_Scoop
import Utils.Deploy.Version.ScoopBucketVCS
import jetbrains.buildServer.configs.kotlin.Project

object Project : Project({
    id("ScoopBucket")
    name = "ScoopBucket"

    vcsRoot(ScoopBucketVCS)

    buildType(LocalRouter_Scoop)
    buildType(Shotmd_Scoop)
})