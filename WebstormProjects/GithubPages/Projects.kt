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
    subProject(githubPagesProject("k88936/rss-reader", "npm install && npm run fetch-feeds && npm run build", "dist"))
    subProject(githubPagesProject("k88936/k88936.github.io", "pnpm install && pnpm build", "dist"))
    subProject(githubPagesProject("k88936/blogs", "npm install && npm run build", "dist"))
    subProject(
        githubPagesProject("k88936/source-of", "npm install && npm run build", "dist") {
            params {
                password("env.S3_ACCESS_KEY", "%KVTO_RUSTFS_ACCESS_KEY%")
                password("env.S3_SECRET_KEY", "%KVTO_RUSTFS_SECRET_KEY%")
                param("env.S3_BUCKET_NAME", "software-release")
                param("env.S3_ENDPOINT", "https://rustfs.k88936.top")
                param("env.AWS_REGION", "us-east-1")
            }
        }
    )
})
