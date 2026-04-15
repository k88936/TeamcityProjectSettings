package utils

fun genRustArtifactRules(exeName: String): String {
    return """
        target/release/${exeName}
        target/x86_64-pc-windows-msvc/release/${exeName}.exe
    """.trimIndent()
}