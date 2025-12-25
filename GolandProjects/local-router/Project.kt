package GolandProjects.LocalRouter

import GolandProjects.LocalRouter.buildTypes.LocalRouter_Build
import GolandProjects.LocalRouter.buildTypes.LocalRouter_Deploy
import GolandProjects.LocalRouter.vcsRoots.LocalRouter_GitGithubComK88936localRouterGitRefsHeadsMain
import jetbrains.buildServer.configs.kotlin.Project

object Project : Project({
    id("LocalRouter")
    name = "local-router"

    vcsRoot(LocalRouter_GitGithubComK88936localRouterGitRefsHeadsMain)

    buildType(LocalRouter_Build)
    buildType(LocalRouter_Deploy)
})