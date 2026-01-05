# AGENTS.md - TeamCity Kotlin DSL Configuration Guide

This document provides essential guidelines for agentic coding agents working with TeamCity Kotlin DSL configurations.

## Build Commands

### Primary Commands

```bash
# Generate TeamCity configurations
mvn org.jetbrains.teamcity:teamcity-configs-maven-plugin:generate
```

### Testing and Validation

- **No traditional unit tests** - This is a declarative configuration project
- **Validation**: Use `teamcity-configs-maven-plugin:generate` to validate DSL syntax

### Naming Conventions

- **Projects**: `object Project : Project({...})`
- **Build Types**: `{Project}_{Action}` (e.g., `Fernflower_Build`, `Fernflower_Deploy`)
- **VCS Roots**: `{Project}_{Type}_{Url}_{Branch}` (e.g., `Fernflower_GitGithubComK88936fernflowerGitRefsHeadsMaster`)
- **Extension Functions**: `fun BuildType.apply{Feature}(...)`
- **Template Functions**: `fun {Feature}Template(...): BuildType`

### Kotlin DSL Patterns

```kotlin
// Object declaration with configuration block
object BuildName : BuildType({
    name = "Display Name"
    id("BuildName")

    vcs {
        root(ProjectName_GitUrl)
    }

    steps {
        gradle {
            id = "gradle_runner"
            tasks = "clean build"
            jdkHome = "%env.JDK_21_0_x64%"
        }
    }

    triggers {
        vcs { }
    }

    features {
        perfmon { }
    }
})
```

### File Structure

```
{ProjectType}/
├── {ProjectName}/
│   ├── Project.kt           # Main project definition
│   ├── buildTypes/          # Build configuration objects
│   │   ├── {Project}_Build.kt
│   │   └── {Project}_Deploy.kt
│   └── vcsRoots/           # VCS root definitions
│       └── {Project}_Git...kt
└── utils/                  # Shared templates and utilities
    ├── deploy/
    ├── trigger/
    ├── vcs/
    └── Env.kt
```

## Extension Functions and Templates

### Extension Functions

Use Kotlin's extension functions to create reusable DSL components:

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

Template functions should return BuildType and be used for simple configurations:

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

## Common Patterns and Best Practices

### Environment Variables

- Use centralized `utils.Env` for shared constants
- Reference environment variables with `%env.VAR_NAME%` syntax
- Use JDK version variables: `%env.JDK_21_0_x64%`
- TeamCity system variables: `%build.number%`, `%teamcity.build.branch%`

### VCS Integration

- VCS roots follow strict naming: `{Project}_{Type}_{Url}_{Branch}`
- Use `vcsRoot?.let { ... }` for optional VCS configuration
- Import VCS roots in project files: `import ProjectName.vcsRoots.*`

### Build Steps

- Always assign unique IDs: `id = "step_name"`
- Use descriptive step names: `name = "Display Name"`
- Group related steps logically
- Use extension functions for common step patterns

### Error Handling

- Check for null VCS roots before assignment
- Use safe calls (`?.`) and let blocks for optional configuration
- Validate template parameters where necessary

### Code Organization

- Keep project definitions in separate Project.kt files
- Group build types by project in subdirectories
- Store reusable utilities in utils package
- Follow consistent import ordering

### Security

- Never commit secrets directly to configuration
- Use TeamCity credentials: `credentialsJSON:uuid`
- Reference secure parameters in build configurations
- Use SSH agent features for Git operations