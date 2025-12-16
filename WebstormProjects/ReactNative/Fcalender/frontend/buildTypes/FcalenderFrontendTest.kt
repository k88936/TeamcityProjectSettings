package WebstormProjects.ReactNative.Fcalender.frontend.buildTypes

import WebstormProjects.ReactNative.Fcalender.frontend.vcsRoots.FcalenderFrontendVCS
import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.buildFeatures.notifications
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.buildSteps.script
import jetbrains.buildServer.configs.kotlin.triggers.vcs

object FcalenderFrontendTest : BuildType({
    name = "Test"


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
        script {
            id = "detox"
            scriptContent = """
                cd frontend
                source /etc/profile
                npm install
                sdkmanager "cmdline-tools;latest"
                sdkmanager "platform-tools" "emulator"
                
                ${"$"}ANDROID_HOME/cmdline-tools/latest/bin/sdkmanager "system-images;android-35;default;x86_64"
                ${"$"}ANDROID_HOME/cmdline-tools/latest/bin/avdmanager create avd --name "pixel9_api35" --device "pixel_9" --package "system-images;android-35;default;x86_64" --force
                ${"$"}ANDROID_HOME/platform-tools/adb start-server
                
                detox build --configuration android.emu.release
                detox test --configuration android.emu.release --headless --record-logs all --take-screenshots all
            """.trimIndent()
            dockerImage = "kvtodev/ci-containers:detox"
            dockerRunParameters =
                "--rm -v /cache/.m2:/root/.m2 -v /cache/.gradle:/root/.gradle/ -v /cache/android-sdk:/android-sdk -v /cache/avd:/avd --device /dev/kvm"
            dockerPull = true
        }
    }
})
