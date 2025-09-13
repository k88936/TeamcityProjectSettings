package RustroverProjects.OllamaProxy

import RustroverProjects.OllamaProxy.buildTypes.OllamaProxy_BuildWin
import jetbrains.buildServer.configs.kotlin.Project

object Project : Project({
    id("OllamaProxy")
    name = "Ollama Proxy"

    vcsRoot(RustroverProjects.OllamaProxy.vcsRoots.OllamaProxy_GitGithubComK88936ollamaProxyGitRefsHeadsMain)
    buildType(OllamaProxy_BuildWin)
    buildType(RustroverProjects.OllamaProxy.buildTypes.OllamaProxy_Deploy)
})