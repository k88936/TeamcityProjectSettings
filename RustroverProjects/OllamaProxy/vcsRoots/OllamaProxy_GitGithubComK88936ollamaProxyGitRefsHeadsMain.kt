package RustroverProjects.OllamaProxy.vcsRoots

import jetbrains.buildServer.configs.kotlin.vcs.GitVcsRoot

object OllamaProxy_GitGithubComK88936ollamaProxyGitRefsHeadsMain : GitVcsRoot({
    id("OllamaProxy_GitGithubComK88936ollamaProxyGitRefsHeadsMain")
    name = "git@github.com:k88936/ollama-proxy.git#refs/heads/main"
    url = "git@github.com:k88936/ollama-proxy.git"
    branch = "refs/heads/main"
    branchSpec = "refs/heads/*"
    authMethod = uploadedKey {
        uploadedKey = "id_rsa"
    }
})