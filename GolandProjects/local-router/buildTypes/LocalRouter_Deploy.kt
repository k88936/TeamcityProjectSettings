package GolandProjects.LocalRouter.buildTypes

import GolandProjects.LocalRouter.vcsRoots.LocalRouter_GitGithubComK88936localRouterGitRefsHeadsMain
import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.triggers.finishBuildTrigger
import utils.Env
import utils.deploy.applyGithubReleaseDeployment
import utils.deploy.applySourceOfDeployment

object LocalRouter_Deploy : BuildType({
    id("LocalRouter_Deploy")
    name = "deploy"
    type = Type.DEPLOYMENT

    vcs {
        root(LocalRouter_GitGithubComK88936localRouterGitRefsHeadsMain)
    }

    triggers {
        finishBuildTrigger {
            buildType = LocalRouter_Build.id?.value
            successfulOnly = true
        }
    }

    dependencies {
        artifacts(LocalRouter_Build) {
            buildRule = lastSuccessful()
            artifactRules = """
                *=>_deploy/
            """.trimIndent()
        }
    }

    applySourceOfDeployment(
        name = "local-router",
        assets = "_deploy/build/local-router.exe"
    )

    applyGithubReleaseDeployment(
        tagPattern = "build-${Env.BUILD_NUMBER}",
        assetsPath = "_deploy/build/local-router _deploy/build/local-router.exe",
        prerelease = true
    )


    features {
        perfmon {

        }
    }
})