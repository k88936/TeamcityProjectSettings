package Utils.AI

import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.buildSteps.script


object ContinueAITemplate {
    fun createStep(
        prompt: String,
        workdir: String = ".",
    ): BuildType.() -> Unit {
        return {
            steps {
                script {
                    id = "continue"
                    scriptContent = """
                        source /etc/profile
                        cd $workdir
                        cn --verbose --auto -p "$prompt"
                    """.trimIndent()
                    dockerRunParameters = "--rm -v /root/.continue:/root/.continue"
                    dockerImage = "kvtodev/ci-containers:continue"
                    dockerPull = true
                }
            }
            params {
                password("env.CONTINUE_API_KEY", "credentialsJSON:0af0457d-0ac1-4e56-83e7-6e93306effa7")
                param("env.CONTINUE_API_BASE", "https://llmapi.paratera.com/v1")
                param("env.CONTINUE_MODEL_NAME", "Qwen3-Coder-Plus")
                param("env.CONTINUE_CONTEXT_LENGTH", "1000000")
            }
        }
    }
}
