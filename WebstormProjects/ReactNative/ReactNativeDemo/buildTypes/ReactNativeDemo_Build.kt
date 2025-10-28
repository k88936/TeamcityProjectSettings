package WebstormProjects.ReactNative.ReactNativeDemo.buildTypes

import Utils.AI.ContinueAITemplate
import Utils.Repo.GithubTemplate
import Utils.Trigger.TriggerTemplate
import WebstormProjects.ReactNative.ReactNativeBuildTemplate
import WebstormProjects.ReactNative.ReactNativeDemo.vcsRoots.ReactNativeDemo_GitGithubComK88936reactNativeDemoGitRefsHeadsMain
import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon

object ReactNativeDemo_Build : BuildType({
    name = "Build"

    artifactRules = "android/app/build/outputs/apk/release/app-release.apk"

    params {
        param("env.NODE_OPTIONS", "--max_old_space_size=4096")
    }

    vcs {
        root(ReactNativeDemo_GitGithubComK88936reactNativeDemoGitRefsHeadsMain)
    }


    TriggerTemplate.excludeCI()(this)
    features {
        perfmon {
        }
    }

    ContinueAITemplate.createStep("check if the newest commit fully support i18n .if not, patch it")(this)
    GithubTemplate.createGitPushStep("ensure i18n")
    ReactNativeBuildTemplate.createReactNativeAndroidBuild()(this)
})
