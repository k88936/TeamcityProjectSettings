package Shotmd.buildTypes

import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildSteps.script
import jetbrains.buildServer.configs.kotlin.triggers.finishBuildTrigger

object Shotmd_Deploy : BuildType({
    name = "Deploy"

    enablePersonalBuilds = false
    type = BuildTypeSettings.Type.DEPLOYMENT
    maxRunningBuilds = 1

    vcs {
        root(Shotmd.vcsRoots.Shotmd_GitGithubComK88936ShotmdGitRefsHeadsMaster)
    }

    steps {
        script {
            name = "New build step"
            id = "simpleRunner_1"
            scriptContent = "gh release create v%build.number% --generate-notes ghrelease/*"
        }
    }

    triggers {
        finishBuildTrigger {
            buildType = "${Shotmd_Build.id}"
            successfulOnly = true
        }
    }

    dependencies {
        artifacts(Shotmd_Build) {
            buildRule = lastSuccessful()
            artifactRules = "Shotmd.zip=>ghrelease/"
        }
    }
})
