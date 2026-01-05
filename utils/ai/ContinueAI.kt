package utils.ai

import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.buildSteps.script
import utils.Env
import utils.vcs.USER_INFO


object ContinueAIConfig {
    const val apiBase = "https://llmapi.paratera.com/v1"
    const val model = "Qwen3-Coder-Plus"
    const val contextLength = 500000
}

fun BuildType.applyContinueAIStep(
    prompt: String,
    workdir: String = ".",
) {
    steps {
        script {
            id = "continue"
            scriptContent = """
                |source /etc/profile
                |$USER_INFO
                |cat > /tmp/config.yaml << EOF
                |name: %env.TEAMCITY_PROJECT_NAME%
                |version: build-${Env.BUILD_NUMBER}
                |schema: v1
                |models:
                |  - name: ai
                |    provider: openai
                |    model: ${ContinueAIConfig.model}
                |    apiBase: ${ContinueAIConfig.apiBase}
                |    apiKey: %env.CONTINUE_API_KEY%
                |    capabilities:
                |      - tool_use
                |    defaultCompletionOptions:
                |      contextLength: ${ContinueAIConfig.contextLength}
                |      
                |rules: 
                |  - ALWAYS use Teamcity as user name and teamcity@k88936.top as email.
                |  - ALWAYS add a '[CI]' tag in your git commit message.
                |context:
                |  - provider: diff
                |  - provider: file
                |  - provider: code
                |  - provider: terminal
                |  
                |mcpServers:
                |  - name: Memory
                |    command: npx
                |    args:
                |      - "-y"
                |      - "@modelcontextprotocol/server-memory"
                |  - name: Filesystem
                |    command: npx
                |    args:
                |      - "-y"
                |      - "@modelcontextprotocol/server-filesystem"
                |      - "."
                |  - name: Context7 MCP
                |    type: streamable-http
                |    url: https://mcp.context7.com/mcp
                |EOF
                |
                |cd $workdir
                |read -r -d '' prompt <<'EOF'
                |$prompt
                |EOF
                |cn --config /tmp/config.yaml --verbose --auto -p "${'$'}prompt"
            """.trimMargin()
            dockerRunParameters = "--rm -v /root/.continue:/root/.continue"
            dockerImage = "kvtodev/ci-containers:continue"
            dockerPull = true
        }
    }
    params {
        password("env.CONTINUE_API_KEY", "credentialsJSON:0af0457d-0ac1-4e56-83e7-6e93306effa7")
    }
}
