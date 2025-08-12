package Fernflower.buildTypes

import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildSteps.script
import jetbrains.buildServer.configs.kotlin.triggers.finishBuildTrigger

object Fernflower_Deploy : BuildType({
    name = "Deploy"

    enablePersonalBuilds = false
    type = BuildTypeSettings.Type.DEPLOYMENT
    maxRunningBuilds = 1

    vcs {
        root(Fernflower.vcsRoots.Fernflower_GitGithubComK88936fernflowerGitRefsHeadsMaster)
    }

    steps {
        script {
            id = "simpleRunner"
            scriptContent = "gh release create v%build.number% --generate-notes ghrelease/*"
        }
    }

    triggers {
        finishBuildTrigger {
            buildType = "${Fernflower_Build.id}"
            successfulOnly = true
        }
    }

    dependencies {
        artifacts(Fernflower_Build) {
            buildRule = lastSuccessful()
            artifactRules = "*.jar=>ghrelease/"
        }
    }
})
