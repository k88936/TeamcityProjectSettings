package WebstormProjects.ReactNative

import jetbrains.buildServer.configs.kotlin.BuildSteps
import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.buildSteps.script


object ReactNativeBuildTemplate {
   fun createReactNativeAndroidBuild(): BuildSteps.() -> Unit {
        return {
            this.script {
                id = "nodejs_runner"
                scriptContent =
                    """
                        source /etc/profile
                        npm install
                        npx expo prebuild
                        npx react-native build-android --tasks assembleRelease
                    """.trimIndent()
                dockerRunParameters = "--rm v ~/.m2:/root/.m2"
                dockerImage = "kvtodev/ci-containers:react-native"
            }
        }
    }
}
