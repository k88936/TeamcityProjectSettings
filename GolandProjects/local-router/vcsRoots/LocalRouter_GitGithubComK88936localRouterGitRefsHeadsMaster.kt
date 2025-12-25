package GolandProjects.LocalRouter.vcsRoots

import jetbrains.buildServer.configs.kotlin.vcs.GitVcsRoot

object LocalRouter_GitGithubComK88936localRouterGitRefsHeadsMaster : GitVcsRoot({
    name = "git@github.com:k88936/local-router.git#refs/heads/master"
    url = "git@github.com:k88936/local-router.git"
    branch = "refs/heads/master"
    branchSpec = "refs/heads/*"
    authMethod = uploadedKey {
        uploadedKey = "id_rsa"
    }
})