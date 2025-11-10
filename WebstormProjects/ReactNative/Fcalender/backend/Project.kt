package WebstormProjects.ReactNative.Fcalender.backend

import WebstormProjects.ReactNative.Fcalender.backend.buildTypes.FcalenderBackendBuild
import WebstormProjects.ReactNative.Fcalender.backend.vcsRoots.FcalenderBackendVCS
import jetbrains.buildServer.configs.kotlin.Project
import jetbrains.buildServer.configs.kotlin.projectFeatures.dockerRegistry

object Project : Project({
    name = "Fcalender Backend"
    id("FcalenderBackend")

    vcsRoot(FcalenderBackendVCS)


    buildType(FcalenderBackendBuild)
    features {
        dockerRegistry {
            id = "DOCKER_REGISTRY_CONNECTION"
            name = "Docker Registry"
            userName = "kvtodev"
            password = "credentialsJSON:a43b4956-2b9e-457c-bf3c-6e04db2adc38"
        }
//        dockerRegistry {
//            id = dockerConnection
//            name = "Docker Registry"
//            url = "https://ghcr.io"
//            userName = "k88936"
//            password = "credentialsJSON:87641c9a-897e-4143-b113-ffdc84599188"
//        }
    }
})