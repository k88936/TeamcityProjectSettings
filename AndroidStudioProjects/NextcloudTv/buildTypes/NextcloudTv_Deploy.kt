package AndroidStudioProjects.NextcloudTv.buildTypes

import AndroidStudioProjects.NextcloudTv.vcsRoots.NextcloudTv_GitGithubComK88936nextcloudTvGitRefsHeadsMain
import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.triggers.finishBuildTrigger
import utils.deploy.applyGithubReleaseDeployment
import utils.deploy.applySourceOfDeployment

object NextcloudTv_Deploy : BuildType({
    id("NextcloudTv_Deploy")
    name = "Deploy"
    type = Type.DEPLOYMENT

    vcs {
        root(NextcloudTv_GitGithubComK88936nextcloudTvGitRefsHeadsMain)
    }

    triggers {
        finishBuildTrigger {
            buildType = NextcloudTv_Build.id?.value
            successfulOnly = true
        }
    }

    dependencies {
        artifacts(NextcloudTv_Build) {
            buildRule = lastSuccessful()
            cleanDestination = true
            artifactRules = "*=>_deploy/"
        }
    }

    applySourceOfDeployment(
        name = "nextcloud-tv"
    )

    applyGithubReleaseDeployment()

    features {
        perfmon { }
    }
})
