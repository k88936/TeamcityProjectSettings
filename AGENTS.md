# AGENTS.md - TeamCity Kotlin DSL Configuration Guide

This document provides essential guidelines for agentic coding agents working with TeamCity Kotlin DSL configurations.

## Build Commands

### Primary Commands

```bash
# Generate TeamCity configurations
mvn org.jetbrains.teamcity:teamcity-configs-maven-plugin:generate

# Standard Maven lifecycle
mvn clean compile test-compile
```

### Testing and Validation

- **No traditional unit tests** - This is a declarative configuration project
- **Validation**: Use `teamcity-configs-maven-plugin:generate` to validate DSL syntax

### Naming Conventions

- **Projects**: `object Project : Project({...})`
- **Build Types**: `{Project}_{Action}` (e.g., `Fernflower_Build`, `Fernflower_Deploy`)
- **VCS Roots**: `{Project}_{Type}_{Url}_{Branch}` (e.g., `Fernflower_GitGithubComK88936fernflowerGitRefsHeadsMaster`)
- **Extension Functions**: `fun BuildType.apply{Feature}(...)`

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
└── Utils/                  # Shared templates and utilities
    ├── Deploy/
    ├── Trigger/
    ├── VCS/
    └── Env.kt
```

## Utilities

### Extension Functions

```kotlin
// Create reusable extension functions
fun BuildType.applyGitPushStep(comment: String = "update") {
    steps {
        script {
            name = "Git Push Changes"
            id = "git_push"
            scriptContent = """
                ${GithubTemplate.USER_INFO}
                ${GithubTemplate.BYPASS_SSH_KEY_CHECK}
                git add -A
                git commit -m"[CI] $comment"
                git push --force
            """.trimIndent()
        }
    }
}
```

## Common Patterns

### Environment Variables

- Use centralized `Utils.Env` for shared constants
- Reference environment variables with `%env.VAR_NAME%` syntax
- Use JDK version variables: `%env.JDK_21_0_x64%`