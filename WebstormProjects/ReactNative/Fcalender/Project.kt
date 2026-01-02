package WebstormProjects.ReactNative.Fcalender

import WebstormProjects.ReactNative.Fcalender.buildTypes.FcalenderFrontendBuild
import WebstormProjects.ReactNative.Fcalender.vcsRoots.FcalenderVCS
import jetbrains.buildServer.configs.kotlin.Project
import jetbrains.buildServer.configs.kotlin.projectFeatures.dockerRegistry

object Project : Project({
    name = "Fcalender"
    id("Fcalender")

//    buildType(FcalenderBackendBuild)
    buildType(FcalenderFrontendBuild)
    vcsRoot(FcalenderVCS)
    features {
        dockerRegistry {
            id = "DOCKER_REGISTRY_CONNECTION"
            name = "Docker Registry"
            userName = "kvtodev"
            password = "credentialsJSON:a43b4956-2b9e-457c-bf3c-6e04db2adc38"
        }
    }
})