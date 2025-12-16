package WebstormProjects.ReactNative

import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.buildSteps.script


object ReactNativeBuildTemplate {
    fun createReactNativeAndroidBuild(
        dir: String = ".",
        script: String = """
                        cd $dir
                        set -e
                        source /etc/profile
                        npm install
                        
                        
                        if [ ! -d "android" ]; then
                            npx expo prebuild --platform android
                            yes | sdkmanager --licenses
                        fi
                        
                        cd android
                        ./gradlew assembleRelease --no-daemon --parallel --warning-mode all
                    """.trimIndent()
    ): BuildType.() -> Unit {
        return {
            steps {
                script {
                    id = "build apk"
                    scriptContent = script
                    dockerRunParameters =
                        "--rm -v /root/.m2:/root/.m2 -v /root/.gradle:/root/.gradle/ -v /opt/android-sdk:/opt/android-sdk"
                    dockerImage = "kvtodev/ci-containers:react-native"
                    dockerPull = true
                }
            }
        }
    }
}
