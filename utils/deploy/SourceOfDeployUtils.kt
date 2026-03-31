package utils.deploy

import jetbrains.buildServer.configs.kotlin.BuildType
import jetbrains.buildServer.configs.kotlin.buildSteps.script


fun BuildType.applySourceOfDeployment(
    name: String,
    tagPattern: String = "v${utils.Env.BUILD_NUMBER}",
    assets: String = "_deploy/*",
) {
    val BUCKET_NAME = "software-release"
    val S3_PATH="${BUCKET_NAME}/${name}/${tagPattern}"
    steps {
        script {
            this.name = "Source of Release"
            this.scriptContent =
                """
                set -e
                for asset in $assets; do
                    if [ -f "${'$'}asset" ]; then
                        aws s3 cp "${'$'}asset" "s3://${S3_PATH}/${'$'}asset)" --endpoint-url "https://rustfs.k88936.top"
                    else
                        echo "Warning: ${'$'}asset does not exist, skipping"
                    fi
                done
                """.trimIndent()
        }
    }

    params {
        password("env.AWS_ACCESS_KEY_ID", "%KVTO_RUSTFS_ACCESS_KEY%")
        password("env.AWS_SECRET_ACCESS_KEY", "%KVTO_RUSTFS_SECRET_KEY%")
        param("env.AWS_DEFAULT_REGION", "us-east-1")
    }
}