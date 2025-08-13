package SourceOf_MyCollection.buildTypes

import SourceOf.buildTypes.SourceOf_Build
import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildSteps.script
import jetbrains.buildServer.configs.kotlin.triggers.finishBuildTrigger
import _Self.utils.DeploymentBuilders

object SourceOf_Deploy : BuildType({

    DeploymentBuilders.createGitPushDeployment()(this)

    vcs {
        root(SourceOf_MyCollection.vcsRoots.SourceOf_MyCollection_GitGithubComK88936myCollectionGitRefsHeadsMain)
        cleanCheckout = true
    }

    triggers {
        finishBuildTrigger {
            buildType = "${SourceOf_Build.id}"
        }
    }

    dependencies {
        artifacts(SourceOf.buildTypes.SourceOf_Build) {
            buildRule = lastSuccessful()
            artifactRules = "**"
        }
    }
})