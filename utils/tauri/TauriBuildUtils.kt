package utils.tauri

import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.buildSteps.script

const val TAURI_ANDROID_NDK_VERSION = "29.0.13846066"

fun BuildType.applyTauriInitStep() {
    steps {
        script {
            id = "tauri init"
            scriptContent = """
                pnpm i
            """.trimIndent()
        }
    }
}

fun BuildType.applyTauriLinuxBuildStep() {
    steps {
        script {
            id = "tauri build linux"
            scriptContent = """
                NO_STRIP=true pnpm tauri build
            """.trimIndent()
        }
    }
}

fun BuildType.applyTauriWindowsBuildStep() {
    steps {
        script {
            id = "tauri build windows"
            scriptContent = """
                ln -sf pathcch.lib ~/.cache/cargo-xwin/xwin/sdk/lib/um/x86_64/PathCch.lib
                XWIN_ARCH=x86_64 pnpm tauri build --runner cargo-xwin --target x86_64-pc-windows-msvc
            """.trimIndent()
        }
    }
}

fun BuildType.applyTauriBuildStep() {
    applyTauriInitStep()
    applyTauriLinuxBuildStep()
    applyTauriWindowsBuildStep()
}

fun BuildType.applyTauriAndroidBuildStep(
    ndkVersion: String = TAURI_ANDROID_NDK_VERSION,
) {
    steps {
        script {
            id = "tauri build android"
            scriptContent = """
                yes | sdkmanager --sdk_root=${'$'}ANDROID_HOME --licenses > /dev/null
                sdkmanager --sdk_root=${'$'}ANDROID_HOME --install "ndk;$ndkVersion"
                pnpm tauri android build --apk
            """.trimIndent()
        }
    }
}

fun genTauriLinuxArtifactRules(binary: String): String {
    return """
       target/release/${binary}
       target/release/bundle/deb/*.deb
       target/release/bundle/rpm/*.rpm
       target/release/bundle/appimage/*.AppImage
    """.trimIndent()
}

fun genTauriWindowsArtifactRules(binary: String): String {
    return """
       target/x86_64-pc-windows-msvc/release/${binary}.exe
       target/x86_64-pc-windows-msvc/release/bundle/nsis/*-setup.exe
    """.trimIndent()
}

fun genTauriAndroidArtifactRules(): String {
    return """
       src-tauri/gen/android/app/build/outputs/apk/universal/release/*.apk
    """.trimIndent()
}

fun genTauriArtifactRules(binary: String): String {
    return listOf(
        genTauriLinuxArtifactRules(binary),
        genTauriWindowsArtifactRules(binary),
    ).joinToString("\n")
}
