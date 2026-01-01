package DockerProjects.TeamcityAgent

import DockerProjects.DockerBuildTemplate
import DockerProjects.TeamcityAgent.vcsRoots.TeamcityAgent_GitGithubComK88936teamcityAgentGitRefsHeadsMain
import jetbrains.buildServer.configs.kotlin.Project

object Project : Project({
    id("TeamcityAgent")
    name = "Teamcity Agent"

    vcsRoot(TeamcityAgent_GitGithubComK88936teamcityAgentGitRefsHeadsMain)

    buildType(
        DockerBuildTemplate(
            name = "Teamcity Agent Build",
            imageName = "kvtodev/teamcity-agent",
            vcsRoot = TeamcityAgent_GitGithubComK88936teamcityAgentGitRefsHeadsMain,
            enableVcsTrigger = true
        )
    )

})