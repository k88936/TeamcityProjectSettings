package Utils.AI

import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.buildSteps.script


object ContinueAITemplate {
    fun createStep(
        prompt: String,
        workdir: String = ".",
    ): BuildType.() -> Unit {
        val apiBase = "https://llmapi.paratera.com/v1"
        val model = "Qwen3-Coder-Plus"
        val contextLength = 500000
        val config = """
name: %env.TEAMCITY_PROJECT_NAME%
version: build-%build.number%
schema: v1
models:
  - name: ai
    provider: openai
    model: $model
    apiBase: $apiBase
    apiKey: %env.CONTINUE_API_KEY%
    capabilities:
      - tool_use
    defaultCompletionOptions:
      contextLength: $contextLength

context:
  - provider: diff
  - provider: file
  - provider: code
  - provider: terminal

mcpServers:
  - name: Memory
    command: npx
    args:
      - "-y"
      - "@modelcontextprotocol/server-memory"
  - name: Filesystem
    command: npx
    args:
      - "-y"
      - "@modelcontextprotocol/server-filesystem"
      - "."
  - name: Context7 MCP
    type: streamable-http
    url: https://mcp.context7.com/mcp
        """.trimIndent()
        return {
            steps {
                script {
                    id = "continue"
                    scriptContent = """
                        source /etc/profile
                        cat > /tmp/config.yaml << EOF
                        $config
                        EOF
                        cd $workdir
                        cn --config /tmp/config.yaml --verbose --auto -p "$prompt"
                    """.trimIndent()
                    dockerRunParameters = "--rm -v /root/.continue:/root/.continue"
                    dockerImage = "kvtodev/ci-containers:continue"
                    dockerPull = true
                }
            }
            params {
                password("env.CONTINUE_API_KEY", "credentialsJSON:0af0457d-0ac1-4e56-83e7-6e93306effa7")
            }
        }
    }
}
