package UnityProjects.TDPixelGame.buildTypes

import UnityProjects.TDPixelGame.vcsRoots.TDPixelGame_GitGithubCom20220120802dPixelGameGitRefsHeadsMain
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
            artifactRules = "**/*=>_deploy/build/"
            cleanDestination = true
        }
    }

    steps {
        script {
            id = "zip_with_7z"
            name = "Create zip archive with 7z"
            scriptContent = """
                cd _deploy/
                7z a -r "TDPixelGame_%build.number%.zip" build/
            """.trimIndent()
        }
    }

    vcs {
        root(TDPixelGame_GitGithubCom20220120802dPixelGameGitRefsHeadsMain)
    }
    applyGithubReleaseDeployment(
        assetsPath = "_deploy/TDPixelGame_%build.number%.zip",
        tagPattern = "build-%build.number%",
        prerelease = true,
    )
})