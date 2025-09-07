package DockerProjects.TeamcityAgent.buildTypes

import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.buildFeatures.dockerRegistryConnections
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.buildSteps.dockerCommand
import jetbrains.buildServer.configs.kotlin.triggers.vcs

object TeamcityAgent_Build : BuildType({
    id("TeamcityAgent_Build")
    name = "Build"

    vcs {
        root(DockerProjects.TeamcityAgent.vcsRoots.TeamcityAgent_GitGithubComK88936teamcityAgentGitRefsHeadsMain)
    }

    steps {
        dockerCommand {
            id = "DockerCommand"
            commandType = build {
                source = file {
                    path = "Dockerfile"
                }
                namesAndTags = "kvtodev/teamcity-agent"
            }
        }
        dockerCommand {
            id = "DockerCommand_1"
            commandType = push {
                namesAndTags = "kvtodev/teamcity-agent"
            }
        }
    }

    triggers {
        vcs {
        }
    }

    features {
        perfmon {
        }
        dockerRegistryConnections {
            loginToRegistry = on {
                dockerRegistryId = "PROJECT_EXT_4"
            }
        }
    }
})