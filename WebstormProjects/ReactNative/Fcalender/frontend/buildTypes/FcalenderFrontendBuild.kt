package WebstormProjects.ReactNative.Fcalender.frontend.buildTypes

import Utils.Deploy.GithubReleaseDeployTemplate.createGithubReleaseDeployment
import Utils.Deploy.SourceOfDeployTemplate
import WebstormProjects.ReactNative.Fcalender.frontend.vcsRoots.FcalenderFrontendVCS
import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.buildFeatures.notifications
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.buildSteps.nodeJS
import jetbrains.buildServer.configs.kotlin.triggers.vcs

object FcalenderFrontendBuild : BuildType({
    name = "Build"

    val apk_location = "frontend/android/app/build/outputs/apk/release/app-release.apk"
    artifactRules = """
        $apk_location 
        frontend/artifacts/ => /logs/
    """.trimIndent()

    vcs {
        root(FcalenderFrontendVCS)
    }

    triggers {
        vcs {
            branchFilter = """
                +:<default>
                +:frontend-playground
                +:dev
            """.trimIndent()
        }
    }

    features {
        perfmon {}
        notifications {
            notifierSettings = emailNotifier {
                email = "k88936@qq.com"
            }
            buildFailed = true
        }
    }
    steps {
        nodeJS {
            id = "jest"
            shellScript = """
                cd frontend
                source /etc/profile
                set -e
                npm install
                npm test
            """.trimIndent()
            dockerImage = "kvtodev/ci-containers:js"
            dockerPull = true
        }
    }

    steps {
        nodeJS {
            id = "detox"
            shellScript = """
                cd frontend
                source /etc/profile
                rm artifacts -rf
                set -e
                
                sdkmanager "cmdline-tools;latest"
                sdkmanager "platform-tools" "emulator"
                sdkmanager "system-images;android-35;google_apis;x86_64"
                avdmanager create avd --name "pixel9_api35" --device "pixel_9" --package "system-images;android-35;google_apis;x86_64" --force
                adb start-server
                
                npm run e2e-test
            """.trimIndent()
            dockerImage = "kvtodev/ci-containers:detox"
            dockerRunParameters =
                "--rm -v /cache/.m2:/root/.m2 -v /cache/.gradle:/root/.gradle/ -v /cache/android-sdk:/android-sdk -v /cache/avd:/avd --device /dev/kvm"
            dockerPull = true
        }
    }

    SourceOfDeployTemplate.createSourceOfDeployment(
        name = "Fcalender", assets = apk_location
    )(this)
    SourceOfDeployTemplate.createSourceOfDeployment(
        name = "Fcalender",
        tagPattern = "latest", assets = apk_location
    )(this)
    createGithubReleaseDeployment(
        tagPattern = "build-%build.number%",
        assetsPath = apk_location,
    )(this)

    maxRunningBuilds = 1
})
