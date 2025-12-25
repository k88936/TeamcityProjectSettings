package GolandProjects.LocalRouter.vcsRoots

import jetbrains.buildServer.configs.kotlin.vcs.GitVcsRoot

object LocalRouter_GitGithubComK88936localRouterGitRefsHeadsMain : GitVcsRoot({
    name = "git@github.com:k88936/local-router.git#refs/heads/main"
    url = "git@github.com:k88936/local-router.git"
    branch = "refs/heads/main"
    branchSpec = "refs/heads/*"
    authMethod = uploadedKey {
        uploadedKey = "id_rsa"
    }
})