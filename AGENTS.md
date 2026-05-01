# AGENTS.md - TeamCity Kotlin DSL Configuration Guide

This document provides essential guidelines for agentic coding agents working with TeamCity Kotlin DSL configurations.

## test generate
```bash
# Generate TeamCity configurations
mvn org.jetbrains.teamcity:teamcity-configs-maven-plugin:generate
```

## Util Functions and Templates

### Util Functions

Use Kotlin's `extension functions` feature to create reusable DSL components:
example:

```kotlin
fun BuildType.applyGitPushStep(comment: String = "update") {
    steps {
        script {
            name = "Git Push Changes"
            id = "git_push"
            scriptContent = """
                ${GithubUtils.USER_INFO}
                ${GithubUtils.BYPASS_SSH_KEY_CHECK}
                git add -A
                git commit -m"[CI] $comment"
                git push --force
            """.trimIndent()
        }
    }
    features {
        sshAgent {
            teamcitySshKey = "id_rsa"
        }
    }
}
```

### Template Functions

Template functions should return well-built object:

example:

```kotlin
fun DockerBuildTemplate(
    name: String,
    imageName: String,
    dockerfilePath: String = "Dockerfile",
    connection: String = "DOCKER_REGISTRY_CONNECTION",
    vcsRoot: GitVcsRoot? = null,
    enableVcsTrigger: Boolean = true
): BuildType {
    return BuildType({
        id(name)
        this.name = name

        steps {
            dockerCommand {
                id = "build"
                commandType = build {
                    source = file { path = dockerfilePath }
                    namesAndTags = imageName
                }
            }
        }

        vcsRoot?.let { root ->
            vcs { root(root) }
        }

        if (enableVcsTrigger) {
            triggers { vcs { } }
        }
    })
}
```