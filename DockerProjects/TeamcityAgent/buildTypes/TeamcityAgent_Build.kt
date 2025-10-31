package DockerProjects.TeamcityAgent.buildTypes

import DockerProjects.DockerBuildTemplate
import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.triggers.vcs

object TeamcityAgent_Build : BuildType({
    id("TeamcityAgent_Build")
    name = "Teamcity Agent Build"

    // Use the common Docker build template
    DockerBuildTemplate.createDockerBuild(
        imageName = "kvtodev/teamcity-agent",
    )(this)

    triggers {
        vcs { }
    }
    vcs {
        root(DockerProjects.TeamcityAgent.vcsRoots.TeamcityAgent_GitGithubComK88936teamcityAgentGitRefsHeadsMain)
    }
})