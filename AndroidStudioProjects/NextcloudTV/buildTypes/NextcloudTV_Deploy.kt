package AndroidStudioProjects.NextcloudTV.buildTypes

import AndroidStudioProjects.NextcloudTV.vcsRoots.NextcloudTv_GitGithubComK88936nextcloudTVGitRefsHeadsMain
import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.triggers.finishBuildTrigger
import utils.deploy.applyGithubReleaseDeployment
import utils.deploy.applySourceOfDeployment

object NextcloudTV_Deploy : BuildType({
    id("NextcloudTv_Deploy")
    name = "Deploy"
    type = Type.DEPLOYMENT

    vcs {
        root(NextcloudTv_GitGithubComK88936nextcloudTVGitRefsHeadsMain)
    }

    triggers {
        finishBuildTrigger {
            buildType = NextcloudTV_Build.id?.value
            successfulOnly = true
        }
    }

    dependencies {
        artifacts(NextcloudTV_Build) {
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
