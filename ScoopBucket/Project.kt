package ScoopBucket

import RustroverProjects.Citizen.buildTypes.Citizen_Scoop
import RustroverProjects.Trackit.buildTypes.Trackit_Scoop
import jetbrains.buildServer.configs.kotlin.Project
import utils.deploy.version.ScoopBucketVCS

object Project : Project({
    id("ScoopBucket")
    name = "ScoopBucket"
    vcsRoot(ScoopBucketVCS)
    buildType(Trackit_Scoop)
    buildType(Citizen_Scoop)
})