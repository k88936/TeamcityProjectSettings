package GolandProjects.LocalRouter.buildTypes

import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.buildSteps.script
import jetbrains.buildServer.configs.kotlin.triggers.vcs

object LocalRouter_Build : BuildType({
    id("LocalRouter_Build")
    name = "Build"

    vcs {
        root(GolandProjects.LocalRouter.vcsRoots.LocalRouter_GitGithubComK88936localRouterGitRefsHeadsMain)
    }

    artifactRules = """
        build/=>build/
    """.trimIndent()

    triggers {
        vcs {
        }
    }
    steps {
        script {
            name = "Download Dependencies"
            scriptContent = """
                source /etc/profile
                go mod download
                go mod verify
            """.trimIndent()
            dockerImage = "kvtodev/ci-containers:go"
            dockerRunParameters =
                "--rm -v /cache/go:/go"
            dockerPull = true
        }

        script {
            name = "Test"
            scriptContent = """
                source /etc/profile
                go test ./server
            """.trimIndent()
            dockerImage = "kvtodev/ci-containers:go"
            dockerRunParameters =
                "--rm -v /cache/go:/go"
            dockerPull = true
        }

        script {
            name = "Build"
            scriptContent = """
                source /etc/profile
                CGO_ENABLED=0 GOOS=windows GOARCH=amd64 go build -o build/local-router.exe ./server 
                go build -o build/local-router ./server
            """.trimIndent()
            dockerImage = "kvtodev/ci-containers:go"
            dockerRunParameters =
                "--rm -v /cache/go:/go"
            dockerPull = true
        }
    }


    features {
        perfmon { }
    }
})