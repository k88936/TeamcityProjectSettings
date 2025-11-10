package WebstormProjects.ReactNative.Fcalender.backend.buildTypes

import Utils.Trigger.TriggerTemplate
import WebstormProjects.ReactNative.Fcalender.backend.vcsRoots.FcalenderBackendVCS
import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.buildFeatures.sshAgent
import jetbrains.buildServer.configs.kotlin.triggers.vcs

object FcalenderBackendBuild : BuildType({
    id("FcalenderBackendBuild")
    name = "FcalenderBackendBuild"
    vcs {
        root(FcalenderBackendVCS)
    }

    triggers {
        vcs {
            triggerRules = """
               +:<default>
               +:backend
           """.trimIndent()
        }
    }
    TriggerTemplate.excludeCI()(this)
    TriggerTemplate.excludeAI()(this)

    features {
        sshAgent {
            teamcitySshKey = "id_rsa"
        }
    }
})