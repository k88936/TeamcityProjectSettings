package Utils.Deploy.Version

import Utils.VCS.applyGitPushStep
import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.buildSteps.script
import jetbrains.buildServer.configs.kotlin.vcs.GitVcsRoot

data class AppManifests(
    val appName: String,
    val content: String
)

object ScoopBucketVCS : GitVcsRoot({
    name = "git@github.com:k88936/scoop-bucket.git#refs/heads/master"
    url = "git@github.com:k88936/scoop-bucket.git"
    branch = "refs/heads/master"
    branchSpec = "refs/heads/*"
    authMethod = uploadedKey {
        uploadedKey = "id_rsa"
    }
})

object ScoopBucketDeployTemplate

fun BuildType.applyScoopBucketDeployment(manifests: AppManifests) {
    name = "${manifests.appName}_Scoop"
    vcs {
        root(ScoopBucketVCS)
    }
    steps {
        script {
            id = "Deploy"
            scriptContent = """
                |cat <<'EOF' > bucket/${manifests.appName}.json
                |${manifests.content}
                |EOF
                |
            """.trimMargin()
        }
    }
    applyGitPushStep("update ${manifests.appName}")
}