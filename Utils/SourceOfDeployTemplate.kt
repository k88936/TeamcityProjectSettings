package Utils

import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildSteps.script


object SourceOfDeployTemplate {

    fun ensureBinary(executable: String, url: String): String {
       return """
           ensure_binary() {
               local binary_name="$1"
               local download_url="$2"
               local max_retries=3

               if [ -z "${'$'}binary_name" ] || [ -z "${'$'}download_url" ]; then
                   echo "Error: ensure_binary requires two arguments: binary_name and download_url" >&2
                   exit 1
               fi

               # Check if already exists and executable
               if [ -x "./${'$'}{binary_name}" ]; then
                   return 0  # Already exists, return immediately
               fi

               echo "Executable './${'$'}{binary_name}' not found, downloading from ${'$'}{download_url}..."

               local retry=0
               while [ ${'$'}retry -lt ${'$'}max_retries ]; do
                   if curl -fsSL -o "${'$'}{binary_name}" "${'$'}{download_url}"; then
                       chmod +x "${'$'}{binary_name}"
                       if [ $? -eq 0 ]; then
                           echo "'${'$'}{binary_name}' downloaded and permissions set successfully."
                           return 0
                       else
                           echo "Failed to make '${'$'}{binary_name}' executable." >&2
                           exit 1
                       fi
                   else
                       echo "Download attempt $((retry + 1)) failed." >&2
                       retry=$((retry + 1))
                       if [ ${'$'}retry -lt ${'$'}max_retries ]; then
                           sleep 2
                       fi
                   fi
               done

               echo "Failed to download '${'$'}{binary_name}' after ${'$'}{max_retries} attempts, aborting." >&2
               exit 1
           }
           ensure_binary $executable $url
       """.trimIndent()
    }

    fun createSourceOfDeployment(
        file: String,
        parentNode: String,
        rename: String = "%build.number%",
    ): BuildType.() -> Unit {
        return {

            require(this.params.hasParam("env.FEISHU_ACCESS_TOKEN")) {
                "FEISHU_ACCESS_TOKEN environment variable is not set."
            }

            steps {
                script {
                    this.name = "Create Lark Release"
                    this.scriptContent =
                        """
                            ${ensureBinary("gold","https://rustfs.k88936.top/software-release/gold/v1.0.0/gold")}
                        
                    """.trimIndent()
                }
            }

            requirements {
                exists("env.GH_CLI")
            }
        }
    }

}