package OllamaProxy

import OllamaProxy.buildTypes.OllamaProxy_BuildWin
import jetbrains.buildServer.configs.kotlin.Project

object Project : Project({
    id("OllamaProxy")
    name = "Ollama Proxy"

    vcsRoot(OllamaProxy.vcsRoots.OllamaProxy_GitGithubComK88936ollamaProxyGitRefsHeadsMain)
    buildType(OllamaProxy.buildTypes.OllamaProxy_BuildLinux)
    buildType(OllamaProxy_BuildWin)
    buildType(OllamaProxy.buildTypes.OllamaProxy_Deploy)
})