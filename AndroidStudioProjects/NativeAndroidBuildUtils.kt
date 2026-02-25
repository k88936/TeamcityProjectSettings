package AndroidStudioProjects

import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.buildSteps.script
import utils.build_steps.getDockerRunProxyParameters

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
            dockerRunParameters =
                "--rm -v /cache:/cache ${getDockerRunProxyParameters()}"
            dockerImage = "kvtodev/ci-containers:android"
            dockerPull = true
        }
    }
}
