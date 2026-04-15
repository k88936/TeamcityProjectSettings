package AndroidStudioProjects

import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.buildSteps.script

fun BuildType.applyNativeAndroidBuild(
    dir: String = ".",
    script: String = """
                        cd $dir
                        yes | sdkmanager --licenses
                        sh ./gradlew assembleRelease --no-daemon --parallel --warning-mode all
                    """.trimIndent()
) {
    steps {
        script {
            id = "build apk"
            scriptContent = script
        }
    }
}
