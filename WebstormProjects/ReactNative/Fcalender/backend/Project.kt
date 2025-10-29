package WebstormProjects.ReactNative.Fcalender.backend

import WebstormProjects.ReactNative.Fcalender.frontend.buildTypes.FcalenderFrontendBuild
import WebstormProjects.ReactNative.Fcalender.frontend.buildTypes.FcalenderFrontendi18nCheck
import WebstormProjects.ReactNative.Fcalender.vcsRoots.FcalenderMain
import jetbrains.buildServer.configs.kotlin.Project
import jetbrains.buildServer.configs.kotlin.projectFeatures.dockerRegistry

object Project : Project({
    name = "Fcalender Backend"
    id("FcalenderBackend")

    vcsRoot(FcalenderMain)

    features {
        dockerRegistry {
            id = "PROJECT_EXT_4"
            name = "Docker Registry"
            url = "https://ghcr.io"
            userName = "k88936"
            password = "credentialsJSON:87641c9a-897e-4143-b113-ffdc84599188"
        }
    }
})