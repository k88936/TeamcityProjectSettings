package WebstormProjects.ReactNative.Fcalender.backend.buildTypes

import DockerProjects.DockerBuildTemplate
import Utils.Trigger.TriggerTemplate
import WebstormProjects.ReactNative.Fcalender.backend.vcsRoots.FcalenderBackendVCS
import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.triggers.vcs

object FcalenderBackendBuild : BuildType({
    id("FcalenderBackendBuild")
    vcs {
        root(FcalenderBackendVCS)
    }

    triggers {
        vcs {
        }
    }
    TriggerTemplate.excludeCI()(this)
    TriggerTemplate.excludeAI()(this)

    DockerBuildTemplate.createDockerBuild(
        imageName = "ghcr.io/k88936/FcalendarBackend",
        dockerfilePath = "backend/Dockerfile",
        connection = "GH_CONNECTION",
    )(this)
})