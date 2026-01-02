package ClionProjects.Qt.Shotmd.buildTypes

import ClionProjects.Qt.Shotmd.vcsRoots.Shotmd_GitGithubComK88936shotmdGitRefsHeadsMaster
import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.buildSteps.script
import jetbrains.buildServer.configs.kotlin.triggers.finishBuildTrigger
import utils.deploy.applyGithubReleaseDeployment

object Shotmd_Deploy : BuildType({
    id("Shotmd_Deploy")
    name = "deploy"
    type = Type.DEPLOYMENT

    triggers {
        finishBuildTrigger {
            buildType = Shotmd_Build.id?.value
            successfulOnly = true
        }
    }
    features {
        perfmon { }
    }
    dependencies {
        artifacts(Shotmd_Build) {
            buildRule = lastSuccessful()
            artifactRules = "*=>_deploy/"
            cleanDestination = true
        }
    }
    steps {
        script {
            id = "zip_with_7z"
            name = "Create zip archive with 7z"
            scriptContent = """
                cd _deploy
                7z a -r "shotmd.zip" *
            """.trimIndent()
        }
    }


    vcs {
        root(Shotmd_GitGithubComK88936shotmdGitRefsHeadsMaster)
    }
    applyGithubReleaseDeployment(
        assetsPath = "_deploy/shotmd.zip",
    )
})