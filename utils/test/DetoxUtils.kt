package utils.test

import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.buildSteps.nodeJS

fun BuildType.applyDetoxStep(
    workingDirectory: String = ".",
    emulatorName: String = "pixel9_api35",
    deviceName: String = "pixel_9",
    androidApi: String = "android-35",
    systemImage: String = "google_apis",
    architecture: String = "x86_64",
) {
    steps {
        nodeJS {
            id = "detox"
            name = "Detox E2E Tests"
            shellScript = """
                cd $workingDirectory
                source /etc/profile
                rm artifacts -rf
                set -e

                sdkmanager "cmdline-tools;latest"
                sdkmanager "platform-tools" "emulator"
                sdkmanager "system-images;$androidApi;$systemImage;$architecture"
                avdmanager create avd --name "$emulatorName" --device "$deviceName" --package "system-images;$androidApi;$systemImage;$architecture" --force
                adb start-server

                npm run e2e-test
            """.trimIndent()
            dockerImage = "kvtodev/ci-containers:detox"
            dockerRunParameters =
                "--rm -v /cache/.m2:/root/.m2 -v /cache/.gradle:/root/.gradle/ -v /cache/android-sdk:/android-sdk -v /cache/avd:/avd --device /dev/kvm"
            dockerPull = true
        }
    }
}