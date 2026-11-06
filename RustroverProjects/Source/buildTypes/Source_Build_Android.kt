package RustroverProjects.Source.buildTypes

import RustroverProjects.Source.vcsRoots.Source_GitGithubComK88936sourceGitRefsHeadsMain
import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.buildSteps.script
import jetbrains.buildServer.configs.kotlin.triggers.vcs
import utils.tauri.applyTauriAndroidBuildStep
import utils.tauri.genTauriAndroidArtifactRules

object Source_Build_Android : BuildType({
    id("Source_Build_Android")
    name = "Build Android"

    vcs {
        root(Source_GitGithubComK88936sourceGitRefsHeadsMain)
    }

    artifactRules = genTauriAndroidArtifactRules()

    triggers {
        vcs {
        }
    }

    params {
        password("env.ANDROID_KEYSTORE_BASE64", "credentialsJSON:7dcbfd11-bcae-4421-996f-ac72eb5eda83")
        password("env.ANDROID_KEYSTORE_PASSWORD", "credentialsJSON:0795c0f1-4634-4d24-ae10-6609d570cd58")
        password("env.ANDROID_KEY_PASSWORD", "credentialsJSON:0795c0f1-4634-4d24-ae10-6609d570cd58")
        text("env.ANDROID_KEY_ALIAS", "source")
    }

    steps {
        script {
            name = "android keystore"
            scriptContent = """
                printf '%s' "${'$'}ANDROID_KEYSTORE_BASE64" | base64 -d > /tmp/source-release.jks
                cat > src-tauri/gen/android/keystore.properties <<EOF
                storeFile=/tmp/source-release.jks
                storePassword=${'$'}ANDROID_KEYSTORE_PASSWORD
                keyAlias=${'$'}ANDROID_KEY_ALIAS
                keyPassword=${'$'}ANDROID_KEY_PASSWORD
                EOF
            """.trimIndent()
        }
    }

    applyTauriAndroidBuildStep()

    features {
        perfmon { }
    }
})
