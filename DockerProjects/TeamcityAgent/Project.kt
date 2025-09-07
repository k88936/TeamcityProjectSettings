package DockerProjects.TeamcityAgent

import DockerProjects.TeamcityAgent.buildTypes.*
import DockerProjects.TeamcityAgent.vcsRoots.*
import jetbrains.buildServer.configs.kotlin.Project
import jetbrains.buildServer.configs.kotlin.projectFeatures.dockerRegistry

object Project : Project({
    id("TeamcityAgent")
    name = "Teamcity Agent"

    vcsRoot(TeamcityAgent_GitGithubComK88936teamcityAgentGitRefsHeadsMain)

    buildType(TeamcityAgent_Build)

})