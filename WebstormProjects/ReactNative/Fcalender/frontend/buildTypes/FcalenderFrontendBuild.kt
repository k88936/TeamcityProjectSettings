package WebstormProjects.ReactNative.Fcalender.frontend.buildTypes

import WebstormProjects.ReactNative.Fcalender.vcsRoots.FcalenderMain
import WebstormProjects.ReactNative.ReactNativeBuildTemplate
import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.buildSteps.nodeJS
import jetbrains.buildServer.configs.kotlin.triggers.vcs

object FcalenderFrontendBuild : BuildType({
    name = "Build"

    artifactRules = "frontend/android/app/build/outputs/apk/release/app-release.apk"

    params {
        param("env.NODE_OPTIONS", "--max_old_space_size=4096")
    }

    vcs {
        root(FcalenderMain)
    }


    triggers {
        vcs {
            branchFilter = """
                +:frontend*
            """.trimIndent()
        }
    }

    features {
        perfmon {}
    }
    steps {
        nodeJS {
            id = "jest"
            shellScript = """
                source /etc/profile
                cd frontend
                npm ci
                npm run test
            """.trimIndent()
            dockerImage = "kvtodev/ci-containers:js"
            dockerPull = true
        }
    }
    ReactNativeBuildTemplate.createReactNativeAndroidBuild("frontend")(this)
})
