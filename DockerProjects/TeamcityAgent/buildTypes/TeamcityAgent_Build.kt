package DockerProjects.TeamcityAgent.buildTypes

import DockerProjects.applyDockerBuild
import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.triggers.vcs

object TeamcityAgent_Build : BuildType({
    id("TeamcityAgent_Build")
    name = "Teamcity Agent Build"

    applyDockerBuild(imageName = "kvtodev/teamcity-agent")

    triggers {
        vcs { }
    }
    vcs {
        root(DockerProjects.TeamcityAgent.vcsRoots.TeamcityAgent_GitGithubComK88936teamcityAgentGitRefsHeadsMain)
    }
})