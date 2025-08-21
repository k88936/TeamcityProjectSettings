package GithubPage

import GithubPage.buildTypes.*
import GithubPage.vcsRoots.*
import jetbrains.buildServer.configs.kotlin.Project

object Project : Project({
    id("GithubPage")
    name = "GithubPage"

    vcsRoot(GithubPage_GitGithubComK88936k88936githubIoGit)

    buildType(GithubPage_Build)
})