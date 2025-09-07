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
    features {
        dockerRegistry {
            id = "PROJECT_EXT_4"
            name = "Docker Registry"
            userName = "kvtodev"
            password = "credentialsJSON:a43b4956-2b9e-457c-bf3c-6e04db2adc38"
        }
    }
})