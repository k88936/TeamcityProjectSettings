package DockerProjects.TeamcityAgent.buildTypes

import jetbrains.buildServer.configs.kotlin.BuildType
import DockerProjects.DockerBuildTemplate

object TeamcityAgent_Build : BuildType({
    id("TeamcityAgent_Build")

    // Use the common Docker build template
    DockerBuildTemplate.createDockerBuild(
        dockerfilePath = "Dockerfile",
        imageName = "kvtodev/teamcity-agent"
    )(this)

    vcs {
        root(DockerProjects.TeamcityAgent.vcsRoots.TeamcityAgent_GitGithubComK88936teamcityAgentGitRefsHeadsMain)
    }
})