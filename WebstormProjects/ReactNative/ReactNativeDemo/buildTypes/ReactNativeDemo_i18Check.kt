package WebstormProjects.ReactNative.ReactNativeDemo.buildTypes

import Utils.AI.ContinueAITemplate
import Utils.Trigger.TriggerTemplate
import Utils.Version.GithubTemplate
import WebstormProjects.ReactNative.ReactNativeDemo.vcsRoots.ReactNativeDemo_GitGithubComK88936reactNativeDemoGitRefsHeadsMain
import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon

object ReactNativeDemo_i18Check : BuildType({
    name = "i18nCheck"


    vcs {
        root(ReactNativeDemo_GitGithubComK88936reactNativeDemoGitRefsHeadsMain)
    }


    TriggerTemplate.excludeCI()(this)
    features {
        perfmon {
        }
    }

    ContinueAITemplate.createStep("check if the newest commit fully support i18n(ch and en). if not, patch it")(this)
    GithubTemplate.createPRStep("i18nCheck", "check and fix i18n", "improve i18n support")(this)
})
