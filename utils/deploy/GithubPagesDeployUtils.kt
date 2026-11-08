package utils.deploy

import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.buildSteps.script
import utils.Env

/**
 * 构建静态站点并以孤提交强推到 gh-pages, 由 GitHub Pages 的 legacy 分支源直接发布
 */
fun BuildType.applyGithubPagesDeployment(
    repoSlug: String,
    buildCommand: String,
    buildDir: String,
) {
    steps {
        script {
            this.name = "Build"
            this.scriptContent = buildCommand
        }
        script {
            this.name = "Deploy gh-pages"
            this.scriptContent = """
                set -euo pipefail
                dest=${'$'}(mktemp -d)
                touch "$buildDir/.nojekyll"
                cp -r "$buildDir/." "${'$'}dest/"
                cd "${'$'}dest"
                git init -q -b main
                git config user.email "Teamcity@k88936.top"
                git config user.name "Teamcity"
                git add -A
                git commit -q -m "Deploy ${Env.BUILD_BRANCH} #${Env.BUILD_NUMBER}"
                git push --force "https://x-access-token:${'$'}GH_TOKEN@github.com/$repoSlug.git" HEAD:gh-pages
                rm -rf "${'$'}dest"
            """.trimIndent()
        }
    }
    params {
        password("env.GH_TOKEN", "credentialsJSON:04d96fb0-dbf8-457b-be29-2327ab11dd68")
    }
}
