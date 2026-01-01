package WebstormProjects.ReactNative.Fcalender.buildTypes

import WebstormProjects.ReactNative.Fcalender.vcsRoots.FcalenderVCS
import WebstormProjects.ReactNative.ReactNativeBuildTemplate
import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.buildFeatures.notifications
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.buildSteps.nodeJS
import jetbrains.buildServer.configs.kotlin.buildSteps.script
import jetbrains.buildServer.configs.kotlin.triggers.vcs
import utils.deploy.applyGithubReleaseDeployment
import utils.deploy.applySourceOfDeployment

object FcalenderFrontendBuild : BuildType({
    id("FcalenderFrontendBuild")
    name = "FcalenderFrontendBuild"

    val apk_location = "frontend/android/app/build/outputs/apk/release/app-release.apk"
    "frontend/artifacts/ => /e2e-test/"


    artifactRules = """
        $apk_location

    """.trimIndent()

    vcs {
        root(FcalenderVCS)
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

//    steps {
//        nodeJS {
//            id = "detox"
//            shellScript = """
//                cd frontend
//                source /etc/profile
//                rm artifacts -rf
//                set -e
//
//                sdkmanager "cmdline-tools;latest"
//                sdkmanager "platform-tools" "emulator"
//                sdkmanager "system-images;android-35;google_apis;x86_64"
//                avdmanager create avd --name "pixel9_api35" --device "pixel_9" --package "system-images;android-35;google_apis;x86_64" --force
//                adb start-server
//
//                npm run e2e-test
//            """.trimIndent()
//            dockerImage = "kvtodev/ci-containers:detox"
//            dockerRunParameters =
//                "--rm -v /cache/.m2:/root/.m2 -v /cache/.gradle:/root/.gradle/ -v /cache/android-sdk:/android-sdk -v /cache/avd:/avd --device /dev/kvm"
//            dockerPull = true
//        }
//    }
//

    steps {
        script {
            id = "version tag"
            scriptContent = """
                
                cat <<'EOF' > version.json
                {
                    "major": 1,
                    "minor": 0,
                    "patch": %build.number%
                }
                EOF
                
                cat <<'EOF' > frontend/version.js
                export const version = {
                    "major": 1,
                    "minor": 0,
                    "patch": %build.number%
                }
                EOF
                
            """.trimIndent()
        }
    }

    ReactNativeBuildTemplate.createReactNativeAndroidBuild(dir = "frontend")(this)

    applySourceOfDeployment(
        name = "Fcalender", assets = apk_location
    )

    applySourceOfDeployment(
        name = "Fcalender",
        tagPattern = "latest", assets = "$apk_location version.json README.md"
    )
    applyGithubReleaseDeployment(
        tagPattern = "build-%build.number%",
        assetsPath = apk_location,
    )
    params {
        password("env.ANDROID_KEYSTORE_PASSWORD", "credentialsJSON:4fb0aac1-ef07-4299-9d16-76bc76f4879f")
        password("env.ANDROID_KEY_ALIAS", "credentialsJSON:35065075-71ae-4db5-9966-cfe20a462105")
        password("env.ANDROID_KEY_PASSWORD", "credentialsJSON:4fb0aac1-ef07-4299-9d16-76bc76f4879f")
    }

    maxRunningBuilds = 1
})
