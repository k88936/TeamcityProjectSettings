package RustroverProjects.Source.buildTypes

import RustroverProjects.Source.vcsRoots.Source_GitGithubComK88936sourceGitRefsHeadsMain
import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.buildSteps.script
import jetbrains.buildServer.configs.kotlin.triggers.vcs
import utils.tauri.applyTauriAndroidBuildStep
import utils.tauri.applyTauriBuildStep

object Source_Build : BuildType({
    id("Source_Build")
    name = "Build"

    vcs {
        root(Source_GitGithubComK88936sourceGitRefsHeadsMain)
    }

    artifactRules = """
       target/release/source
       target/release/bundle/deb/*.deb
       target/release/bundle/rpm/*.rpm
       target/release/bundle/appimage/*.AppImage
       target/x86_64-pc-windows-msvc/release/source.exe
       src-tauri/gen/android/app/build/outputs/apk/universal/release/*.apk
    """.trimIndent()

    params {
        password("env.ANDROID_KEYSTORE_BASE64", "")
        password("env.ANDROID_KEYSTORE_PASSWORD", "")
        password("env.ANDROID_KEY_PASSWORD", "")
        text("env.ANDROID_KEY_ALIAS", "source")
    }

    triggers {
        vcs {
        }
    }

    steps {
        script {
            name = "dep"
            scriptContent = """
                pacman -Syy alsa-lib gtk3 webkit2gtk-4.1 openssl appmenu-gtk-module libappindicator-gtk3 librsvg xdotool --noconfirm
                mkdir -p /usr/lib/gdk-pixbuf-2.0/2.10.0/loaders
                gdk-pixbuf-query-loaders > /usr/lib/gdk-pixbuf-2.0/2.10.0/loaders.cache
            """.trimIndent()
        }
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

    applyTauriBuildStep()
    applyTauriAndroidBuildStep()

    features {
        perfmon { }
    }
})
