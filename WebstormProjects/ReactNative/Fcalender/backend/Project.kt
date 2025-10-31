package WebstormProjects.ReactNative.Fcalender.backend

import WebstormProjects.ReactNative.Fcalender.backend.buildTypes.FcalenderBackendBuild
import WebstormProjects.ReactNative.Fcalender.backend.vcsRoots.FcalenderBackendVCS
import jetbrains.buildServer.configs.kotlin.Project
import jetbrains.buildServer.configs.kotlin.projectFeatures.dockerRegistry

object Project : Project({
    name = "Fcalender Backend"
    id("FcalenderBackend")

    vcsRoot(FcalenderBackendVCS)

    val dockerConnection = "GH_CONNECTION"

    buildType(FcalenderBackendBuild)
    features {
        dockerRegistry {
            id = dockerConnection
            name = "Docker Registry"
            url = "https://ghcr.io"
            userName = "k88936"
            password = "credentialsJSON:87641c9a-897e-4143-b113-ffdc84599188"
        }
    }
})