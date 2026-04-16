package utils.tauri

import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.buildSteps.script

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
                ln -s pathcch.lib ~/.cache/cargo-xwin/xwin/sdk/lib/um/x86_64/PathCch.lib
                pnpm tauri build --runner cargo-xwin --target x86_64-pc-windows-msvc
            """.trimIndent()
        }
    }

}

fun genTauriArtifactRules(binary: String): String {
    return """
       src-tauri/target/release/${binary} 
       src-tauri/target/release/bundle/deb/*.deb
       src-tauri/target/release/bundle/rpm/*.rpm
       src-tauri/target/release/bundle/appimage/*.AppImage
       src-tauri/target/x86_64-pc-windows-msvc/release/${binary}.exe
       src-tauri/target/x86_64-pc-windows-msvc/release/bundle/nsis/*-setup.exe
    """.trimIndent()
}