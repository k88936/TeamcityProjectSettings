package SourceOf_MyCollection.buildTypes

import SourceOf.buildTypes.SourceOf_Build
import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildSteps.script
import jetbrains.buildServer.configs.kotlin.triggers.finishBuildTrigger

object SourceOf_Deploy : BuildType({
    name = "Deploy"

    enablePersonalBuilds = false
    type = BuildTypeSettings.Type.DEPLOYMENT
    maxRunningBuilds = 1

    vcs {
        root(SourceOf_MyCollection.vcsRoots.SourceOf_MyCollection_GitGithubComK88936myCollectionGitRefsHeadsMain)

        cleanCheckout = true
    }

    steps {
        script {
            id = "simpleRunner"
            scriptContent = """
                git add -A
                git commit -m"update"
                git push --force
            """.trimIndent()
        }
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
