package GolandProjects.LocalRouter.buildTypes

import GolandProjects.LocalRouter.vcsRoots.LocalRouter_GitGithubComK88936localRouterGitRefsHeadsMain
import Utils.Deploy.applyGithubReleaseDeployment
import Utils.Deploy.applySourceOfDeployment
import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.triggers.finishBuildTrigger

object LocalRouter_Deploy : BuildType({
    id("LocalRouter_Deploy")
    name = "Deploy"
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
                local-router=>_deploy/
                local-router.exe=>_deploy/
            """.trimIndent()
        }
    }

    applySourceOfDeployment(
        name = "local-router",
        assets = "_deploy/local-router.exe"
    )

    applyGithubReleaseDeployment(
        assetsPath = "_deploy/local-router _deploy/local-router.exe",
        prerelease = true
    )


    features {
        perfmon { }
    }
})