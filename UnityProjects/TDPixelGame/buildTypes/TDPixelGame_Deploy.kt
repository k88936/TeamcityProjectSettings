package UnityProjects.TDPixelGame.buildTypes

import UnityProjects.TDPixelGame.vcsRoot
import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.buildSteps.script
import jetbrains.buildServer.configs.kotlin.triggers.finishBuildTrigger
import utils.deploy.applyGithubReleaseDeployment

object TDPixelGame_Deploy : BuildType({
    id("TDPixelGame_Deploy")
    name = "deploy"
    type = Type.DEPLOYMENT

    triggers {
        finishBuildTrigger {
            buildType = TDPixelGame_Build.id?.value
            successfulOnly = true
        }
    }
    features {
        perfmon { }
    }
    dependencies {
        artifacts(TDPixelGame_Build) {
            buildRule = lastSuccessful()
            artifactRules = """
                build/=>_deploy/build/
            """.trimIndent()
            cleanDestination = true
        }
    }

    steps {
        script {
            id = "zip_with_7z"
            name = "Create zip archive with 7z"
            scriptContent = """
                cd _deploy/
                7z a -r "TDPixelGame_${utils.Env.BUILD_NUMBER}.zip" build/
            """.trimIndent()
        }
    }

    vcs {
        root(vcsRoot)
    }
    applyGithubReleaseDeployment(
        assetsPath = "_deploy/TDPixelGame_${utils.Env.BUILD_NUMBER}.zip",
        tagPattern = "build-${utils.Env.BUILD_NUMBER}",
        prerelease = true,
    )
})