package WebstormProjects.ReactNative

import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.buildSteps.script


object ReactNativeBuildTemplate {
    fun createReactNativeAndroidBuild(
        dir: String = "."
    ): BuildType.() -> Unit {
        return {
            steps {
                script {
                    id = "build apk"
                    scriptContent = """
                        cd $dir
                        source /etc/profile
                        npm ci
                        npx expo prebuild
                        cd android
                        ./gradlew assembleRelease --no-daemon --parallel   
                    """.trimIndent()
                    dockerRunParameters = "--rm -v /root/.m2:/root/.m2 -v /root/.gradle:/root/.gradle/"
                    dockerImage = "kvtodev/ci-containers:react-native"
                    dockerPull = true
                }
            }
        }
    }
}
