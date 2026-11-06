package utils.tauri

import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.buildSteps.script

const val TAURI_ANDROID_NDK_VERSION = "29.0.13846066"

fun BuildType.applyTauriBuildStep() {
    steps {
        script {
            id = "tauri init"
            scriptContent = """
                pnpm i
            """.trimIndent()
        }
        script {
            id = "tauri build linux"
            scriptContent = """
                NO_STRIP=true pnpm tauri build
            """.trimIndent()
        }
        script {
            id = "tauri build windows"
            scriptContent = """
                ln -sf pathcch.lib ~/.cache/cargo-xwin/xwin/sdk/lib/um/x86_64/PathCch.lib
                XWIN_ARCH=x86_64 pnpm tauri build --runner cargo-xwin --target x86_64-pc-windows-msvc
            """.trimIndent()
        }
    }
}

fun BuildType.applyTauriAndroidBuildStep(
    baseDir: String = ".",
    ndkVersion: String = TAURI_ANDROID_NDK_VERSION,
) {
    val cd = baseDir.takeUnless { it.isBlank() || it == "." }
        ?.let { "cd ${it.trimEnd('/')}\n" }
        ?: ""
    steps {
        script {
            id = "tauri build android"
            scriptContent = cd + """
                pnpm i
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

fun genTauriAndroidArtifactRules(baseDir: String = "."): String {
    val prefix = baseDir.takeUnless { it.isBlank() || it == "." }
        ?.let { "${it.trimEnd('/')}/" }
        ?: ""
    return """
       ${prefix}src-tauri/gen/android/app/build/outputs/apk/universal/release/*.apk
    """.trimIndent()
}

fun genTauriArtifactRules(binary: String): String {
    return genTauriLinuxArtifactRules(binary) + "\n" + genTauriWindowsArtifactRules(binary)
}
