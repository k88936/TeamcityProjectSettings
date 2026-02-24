package AndroidStudioProjects.NextcloudTV.buildTypes

import AndroidStudioProjects.NativeAndroidBuildTemplate
import AndroidStudioProjects.NextcloudTV.vcsRoots.NextcloudTv_GitGithubComK88936nextcloudTVGitRefsHeadsMain
import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.triggers.vcs

object NextcloudTV_Build : BuildType({
    name = "Build"

    id("NextcloudTv_Build")

    artifactRules = "app/build/outputs/apk/release/*.apk"

    vcs {
        root(NextcloudTv_GitGithubComK88936nextcloudTVGitRefsHeadsMain)
    }

    NativeAndroidBuildTemplate.createNativeAndroidBuild()

    triggers {
        vcs { }
    }

    features {
        perfmon { }
    }
})
