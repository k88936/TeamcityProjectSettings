package DockerProjects.Dockerfiles.vcsRoots

import jetbrains.buildServer.configs.kotlin.vcs.GitVcsRoot

object Dockerfiles_GitGithubComK88936DockerfilessGitRefsHeadsMain : GitVcsRoot({
    id("Dockerfiles_GitGithubComK88936DockerfilesGitRefsHeadsMain")
    name = "dockerfiles"
    url = "git@github.com:k88936/dockerfiles.git"
    branch = "refs/heads/main"
    branchSpec = "refs/heads/*"
    authMethod = uploadedKey {
        uploadedKey = "id_rsa"
    }

})
