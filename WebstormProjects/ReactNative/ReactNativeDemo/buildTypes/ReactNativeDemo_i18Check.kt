package WebstormProjects.ReactNative.ReactNativeDemo.buildTypes

import Utils.AI.ContinueAITemplate
import Utils.Trigger.TriggerTemplate
import Utils.VCS.GithubTemplate
import WebstormProjects.ReactNative.ReactNativeDemo.vcsRoots.ReactNativeDemo_GitGithubComK88936reactNativeDemoGitRefsHeadsMain
import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.triggers.vcs

object ReactNativeDemo_i18Check : BuildType({
    name = "i18nCheck"


    vcs {
        root(ReactNativeDemo_GitGithubComK88936reactNativeDemoGitRefsHeadsMain)
    }
    triggers {
        vcs {
            branchFilter = "+:<default>"
        }
    }


    TriggerTemplate.excludeCI()(this)
    TriggerTemplate.excludeAI()(this)
    features {
        perfmon {
        }
    }
    failureConditions {
        executionTimeoutMin = 10
    }

    ContinueAITemplate.createStep(
        """
        Check if the new commit fully support i18n(zh and en).
        To work more efficiently, you should: firstly check the new commit message(or diff if needed) to see if it is about frontend UI, if not, end this task.
        Try to avoid scan the whole workspace as possible.
        If there is something to improve, patch it and create a new commit with proper message.
    """.trimMargin()
    )(this)
    GithubTemplate.createPRStep("i18nCheck", "check and fix i18n", "improve i18n support")(this)
})
