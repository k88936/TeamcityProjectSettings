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
                sdkmanager "platform-tools"
                sdkmanager "cmdline-tools;latest"
                sdkmanager "system-images;android-35;default;x86_64" "emulator"
                /opt/android-sdk/cmdline-tools/latest/bin/avdmanager create avd --name "pixel_9_api35" --device "pixel_9" --package "system-images;android-35;default;x86_64" --force
                avd start-server
                
                detox build --configuration android.emu.release
                detox test --configuration android.emu.release --headless 
            """.trimIndent()
            dockerImage = "kvtodev/ci-containers:detox"
            dockerRunParameters =
                "--rm -v /root/.m2:/root/.m2 -v /root/.gradle:/root/.gradle/ -v /opt/android-sdk:/opt/android-sdk"
            dockerPull = true
        }
    }
})
