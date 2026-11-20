package WebstormProjects.GithubPages

import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.Project
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.triggers.vcs
import utils.deploy.applyGithubPagesDeployment
import utils.vcs.GitVcsRootTemplate

private fun githubPagesProject(
    repoSlug: String,
    buildCommand: String,
    buildDir: String,
    extraBuildConfig: BuildType.() -> Unit = {},
): Project {
    val formattedName = repoSlug.replace(Regex("[^a-zA-Z0-9]"), "")
    return Project {
        name = formattedName
        id(formattedName + "_Site")
        val root = GitVcsRootTemplate(
            url = "git@github.com:$repoSlug.git",
            branchSpec = "refs/heads/main",
        )
        vcsRoot(root)

        buildType(
            BuildType {
                id(formattedName + "_Build")
                name = "Build"

                vcs {
                    root(root)
                }

                triggers {
                    vcs {
                    }
                }

                applyGithubPagesDeployment(repoSlug, buildCommand, buildDir)

                features {
                    perfmon {
                    }
                }

                extraBuildConfig()
            }
        )
    }
}

object Projects : Project({
    name = "GithubPages"
    id("GithubPages")
    subProject(githubPagesProject("k88936/rss-reader", "npm install && npm run build", "dist"))
    subProject(githubPagesProject("k88936/k88936.github.io", "pnpm install && pnpm build", "dist"))
    subProject(githubPagesProject("k88936/blogs", "npm install && npm run build", "dist"))
    subProject(
        githubPagesProject(
            "k88936/svelte-navigation",
            """
                set -e
                pnpm install
                pnpm exec playwright install chromium
                pnpm test
                pnpm build
            """.trimIndent(),
            "playground/dist",
        )
    )
})
