package WebstormProjects.ReactNative.Fcalender.frontend.buildTypes

import Utils.AI.ContinueAITemplate
import Utils.Trigger.TriggerTemplate
import Utils.Version.GithubTemplate
import WebstormProjects.ReactNative.Fcalender.frontend.vcsRoots.FcalenderFrontendVCS
import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.triggers.vcs

object FcalenderFrontendApiCheck : BuildType({
    name = "api check"


    vcs {
        root(FcalenderFrontendVCS)
    }
    triggers {
        vcs {
            branchFilter = """
                +:<default>
                +:frontend*
                +:frontend
            """.trimIndent()
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
        Task: check if the new commit fully fit the api doc.

        frontend api implementation related:
        - api/
        - types/errors.ts
        - __test__/api/

        api documentation:
        - https://s.apifox.cn/19523199-9712-47ee-8765-ebf35d9912ff/llms.txt

        check and fix on these aspect:
        - if the api encapsule is covered completely
        - if the error code handle is covered completely.
        - if the mocked offline test for api is covered completely

        the api check work flow:
        - firstly check the new commit message(or diff if needed) to see if it is about api change/add, if not, end this task.(to work more efficiently)
        - you should focus more on the new commit related modification.(to avoid repleated check)
        - if there is something to improve, patch it and create a new commit with proper message.
    """.trimIndent(),
        workdir = "frontend"
    )(this)
    GithubTemplate.createPRStep("teamcity-api-check", "check and fix api issue", "improve frontend api client code")(
        this
    )

})
