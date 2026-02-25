package WebstormProjects.ReactNative

import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.buildSteps.script
import utils.build_steps.getDockerRunParameters

object ReactNativeBuildTemplate {
    fun createReactNativeAndroidBuild(
        dir: String = ".",
        script: String = """
                        cd $dir
                        npm install
                        cd android
                        yes | sdkmanager --licenses
                        sh ./gradlew assembleRelease --no-daemon --parallel --warning-mode all
                    """.trimIndent()
    ): BuildType.() -> Unit {
        return {
            steps {
                script {
                    id = "build apk"
                    scriptContent = script
                    dockerRunParameters = getDockerRunParameters()
                    dockerImage = "kvtodev/ci-containers:react-native"
                    dockerPull = true
                }
            }
        }
    }
}
