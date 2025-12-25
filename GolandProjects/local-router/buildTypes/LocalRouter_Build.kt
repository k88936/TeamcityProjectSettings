package GolandProjects.LocalRouter.buildTypes

import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.buildSteps.script
import jetbrains.buildServer.configs.kotlin.triggers.vcs

object LocalRouter_Build : BuildType({
    id("LocalRouter_Build")
    name = "Build"

    vcs {
        root(GolandProjects.LocalRouter.vcsRoots.LocalRouter_GitGithubComK88936localRouterGitRefsHeadsMaster)
    }

    artifactRules = """
        bin/local-router.exe
        bin/local-router
    """.trimMargin()

    triggers {
        vcs {
        }
    }
    steps {
        script {
            name = "Build"
            scriptContent = """
                source /etc/profile
                CGO_ENABLED=0 GOOS=windows GOARCH=amd64 go build -o bin/local-router.exe
                go build -o bin/local-router.exe
            """.trimIndent()
            dockerImage = "kvtodev/ci-containers:go"

        }
    }


    features {
        perfmon { }
    }
})