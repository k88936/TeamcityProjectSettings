package Utils

import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildSteps.script


object LarkDriveDeployTemplate {
    private fun larkUpload( file: String,parentNode: String,rename: String): String {

        return         """
feishu_upload_file() {
    # Validate inputs
    if [ -z "$file" ] || [ -z "$parentNode" ]; then
        echo "Error: Missing required arguments." >&2
        echo "Usage: feishu_upload_file <file_path> <parent_node> [rename]" >&2
        return 1
    fi

    if [ ! -f "$file" ]; then
        echo "Error: File not found: $file" >&2
        return 1
    fi

    if [ -z "${'$'}FEISHU_ACCESS_TOKEN" ]; then
        echo "Error: FEISHU_ACCESS_TOKEN environment variable is not set." >&2
        return 1
    fi

    # Determine file name: use rename if provided, otherwise basename
    local file_name
    if [ -n "$rename" ]; then
        file_name="$rename"
    else
        file_name="$(basename "$file")"
    fi

    # Get file size (cross-platform)
    local size
    size="$(stat -f%z "$file" 2>/dev/null || stat -c%s "$file")"

    curl --location --request POST 'https://open.feishu.cn/open-apis/drive/v1/files/upload_all' \
        --header "Authorization: Bearer %env.FEISHU_ACCESS_TOKEN%" \
        --form "file_name=\"${'$'}file_name\"" \
        --form 'parent_type="explorer"' \
        --form "parent_node=\"$parentNode\"" \
        --form "size=\"${'$'}size\"" \
        --form "file=@\"$file\""
}            
feishu_upload_file

""".trimIndent()
       
    }

    fun createLarkDriveDeployment(
        token: String="",
        file: String,
        parentNode: String,
        rename: String = "%build.number%",
    ): BuildType.() -> Unit {
        return {

            steps {
                script {
                    this.name = "Create Lark Release"
                    this.scriptContent = 
                        """
                        ${larkUpload(file, parentNode, rename)}
                        
                    """.trimIndent() 
                }
            }
            params {
                password("env.FEISHU_ACCESS_TOKEN", token)
            }
            requirements{
                exists("env.GH_CLI")
            }
        }
    }

}