package WebstormProjects.ReactNative.Fcalender.backend.buildTypes

import DockerProjects.DockerBuildTemplate
import Utils.Env
import Utils.Trigger.TriggerTemplate
import WebstormProjects.ReactNative.Fcalender.backend.vcsRoots.FcalenderBackendVCS
import jetbrains.buildServer.configs.kotlin.BuildType
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
               +:backend*
               +:backend
               +:zwr
               +:zyc
           """.trimIndent()
        }
    }
    TriggerTemplate.excludeCI()(this)
    TriggerTemplate.excludeAI()(this)

    DockerBuildTemplate.createDockerBuild(
        imageName = "ghcr.io/k88936/fcalender-backend:${Env.BUILD_BRANCH}",
        dockerfilePath = "backend/Dockerfile",
        connection = "GH_CONNECTION",
    )(this)
})