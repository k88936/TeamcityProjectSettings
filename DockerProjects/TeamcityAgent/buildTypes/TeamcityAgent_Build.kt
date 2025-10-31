package DockerProjects.TeamcityAgent.buildTypes

import DockerProjects.DockerBuildTemplate
import jetbrains.buildServer.configs.kotlin.BuildType

object TeamcityAgent_Build : BuildType({
    id("TeamcityAgent_Build")

    // Use the common Docker build template
    DockerBuildTemplate.createDockerBuild(
        imageName = "kvtodev/teamcity-agent",
    )(this)

    vcs {
        root(DockerProjects.TeamcityAgent.vcsRoots.TeamcityAgent_GitGithubComK88936teamcityAgentGitRefsHeadsMain)
    }
})