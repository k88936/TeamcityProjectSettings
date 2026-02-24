package AndroidStudioProjects.NextcloudTv.buildTypes

import AndroidStudioProjects.NativeAndroidBuildTemplate
import AndroidStudioProjects.NextcloudTv.vcsRoots.NextcloudTv_GitGithubComK88936nextcloudTvGitRefsHeadsMain
import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.BuildType.PublishMode
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.triggers.vcs

object NextcloudTv_Build : BuildType({
    name = "Build"

    id("NextcloudTv_Build")

    artifactRules = "app/build/outputs/apk/release/*.apk"
    publishArtifacts = PublishMode.SUCCESSFUL

    vcs {
        root(NextcloudTv_GitGithubComK88936nextcloudTvGitRefsHeadsMain)
    }

    NativeAndroidBuildTemplate.createNativeAndroidBuild()

    triggers {
        vcs { }
    }

    features {
        perfmon { }
    }
})
