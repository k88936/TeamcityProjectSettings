package DockerProjects.CiContainers.vcsRoots

import jetbrains.buildServer.configs.kotlin.vcs.GitVcsRoot

object CiContainers_GitGithubComK88936CiContainersGitRefsHeadsMain : GitVcsRoot({
    name = "ci-containers"
    url = "git@github.com:k88936/ci-containers.git"
    branch = "refs/heads/main"
    branchSpec = """
        refs/heads/*
    """.trimIndent()
})
