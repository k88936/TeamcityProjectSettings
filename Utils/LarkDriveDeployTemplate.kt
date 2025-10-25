package Utils

import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildSteps.script


object LarkDriveDeployTemplate {
    private fun larkUpload( file: String,parentNode: String,rename: String): String {

        return         """
            
            export FEISHU_APP_ID="cli_a8769487d0fb900b"
            export FEISHU_APP_SECRET=""
            feishu_get_tenant_token() {
                if [ -z "${'$'}FEISHU_APP_ID" ] || [ -z "${'$'}FEISHU_APP_SECRET" ]; then
                    echo "Error: FEISHU_APP_ID and FEISHU_APP_SECRET must be set." >&2
                    return 1
                fi

                local response
                response=$(curl -sS --request POST \
                    'https://open.feishu.cn/open-apis/auth/v3/tenant_access_token/internal' \
                    --header 'Content-Type: application/json' \
                    --data "{\"app_id\":\"${'$'}FEISHU_APP_ID\",\"app_secret\":\"${'$'}FEISHU_APP_SECRET\"}")

                local token
                token=$(echo "${'$'}response" | jq -r '.tenant_access_token // empty')

                if [ -z "${'$'}token" ]; then
                    echo "Error: Failed to retrieve tenant_access_token." >&2
                    echo "Response: ${'$'}response" >&2
                    return 1
                fi

            }
            FEISHU_ACCESS_TOKEN=$(feishu_get_tenant_token) || exit 1
            export FEISHU_ACCESS_TOKEN
            echo "FEISHU_ACCESS_TOKEN: ${'$'}FEISHU_ACCESS_TOKEN"
            
            
feishu_upload_file() {
    local file_path="$1"
    local parent_node="$2"
    local rename="$3"

    if [ -z "${'$'}file_path" ] || [ -z "${'$'}parent_node" ]; then
        echo "Error: Missing required arguments." >&2
        echo "Usage: feishu_upload_file <file_path> <parent_node> [rename]" >&2
        return 1
    fi

    if [ ! -f "${'$'}file_path" ]; then
        echo "Error: File not found: ${'$'}file_path" >&2
        return 1
    fi

    if ! command -v jq >/dev/null 2>&1; then
        echo "Error: jq is required for JSON handling." >&2
        return 1
    fi

    local raw_token
    raw_token="${'$'}{FEISHU_ACCESS_TOKEN:-u-f1JAhJvERaQ9tSfqRSVjtJ14lk9wllyhMMG0gks02Hd9}"
    local auth_header
    if [ "${'$'}{raw_token#Bearer }" != "${'$'}raw_token" ]; then
        auth_header="Authorization: ${'$'}raw_token"
    else
        auth_header="Authorization: Bearer ${'$'}raw_token"
    fi

    local file_name
    if [ -n "${'$'}rename" ]; then
        file_name="${'$'}rename"
    else
        file_name="$(basename "${'$'}file_path")"
    fi

    local size
    size="$(stat -f%z "${'$'}file_path" 2>/dev/null || stat -c%s "${'$'}file_path")"

    local preupload_payload
    preupload_payload="$(jq -n --arg file_name "${'$'}file_name" --arg parent_node "${'$'}parent_node" --argjson size "${'$'}size" '{file_name:${'$'}file_name,parent_type:"explorer",parent_node:${'$'}parent_node,size:${'$'}size}')"

    local preupload_response
    preupload_response=$(curl -sS --location --request POST 'https://open.feishu.cn/open-apis/drive/v1/files/upload_prepare' \
        --header "${'$'}auth_header" \
        --header 'Content-Type: application/json; charset=utf-8' \
        --data "${'$'}preupload_payload")
    if [ $? -ne 0 ]; then
        echo "Error: Failed to contact upload_prepare endpoint." >&2
        return 1
    fi

    local preupload_code
    preupload_code="$(echo "${'$'}preupload_response" | jq -r '.code // -1')"
    if [ "${'$'}preupload_code" != "0" ]; then
        echo "Error: upload_prepare failed with code ${'$'}preupload_code" >&2
        echo "Response: ${'$'}preupload_response" >&2
        return 1
    fi

    local upload_id block_size block_num
    upload_id="$(echo "${'$'}preupload_response" | jq -r '.data.upload_id // empty')"
    block_size="$(echo "${'$'}preupload_response" | jq -r '.data.block_size // 4194304')"
    block_num="$(echo "${'$'}preupload_response" | jq -r '.data.block_num // empty')"

    if [ -z "${'$'}upload_id" ]; then
        echo "Error: upload_prepare response missing upload_id." >&2
        echo "Response: ${'$'}preupload_response" >&2
        return 1
    fi

    if [ -z "${'$'}block_size" ] || ! [[ "${'$'}block_size" =~ ^[0-9]+$ ]]; then
        block_size=4194304
    fi

    local tmp_dir
    tmp_dir="$(mktemp -d 2>/dev/null || mktemp -d -t feishu_upload)"
    if [ -z "${'$'}tmp_dir" ]; then
        echo "Error: Failed to create temporary directory." >&2
        return 1
    fi

    split -b "${'$'}block_size" -d -a 6 --additional-suffix=.part "${'$'}file_path" "${'$'}tmp_dir/part_" || {
        echo "Error: Failed to split file." >&2
        rm -rf "${'$'}tmp_dir"
        return 1
    }

    local seq=0
    local chunk_path
    for chunk_path in "${'$'}tmp_dir"/part_*; do
        [ -e "${'$'}chunk_path" ] || break
        local chunk_size
        chunk_size="$(stat -f%z "${'$'}chunk_path" 2>/dev/null || stat -c%s "${'$'}chunk_path")"

        local part_response
        part_response=$(curl -sS --location --request POST 'https://open.feishu.cn/open-apis/drive/v1/files/upload_part' \
            --header "${'$'}auth_header" \
            --form "upload_id=${'$'}upload_id" \
            --form "seq=${'$'}seq" \
            --form "size=${'$'}chunk_size" \
            --form "file=@${'$'}chunk_path")
        if [ $? -ne 0 ]; then
            echo "Error: Failed to contact upload_part endpoint for chunk ${'$'}seq." >&2
            rm -rf "${'$'}tmp_dir"
            return 1
        fi

        local part_code
        part_code="$(echo "${'$'}part_response" | jq -r '.code // -1')"
        if [ "${'$'}part_code" != "0" ]; then
            echo "Error: upload_part failed for chunk ${'$'}seq with code ${'$'}part_code" >&2
            echo "Response: ${'$'}part_response" >&2
            rm -rf "${'$'}tmp_dir"
            return 1
        fi

        seq=$((seq + 1))
    done

    if [ "${'$'}seq" -eq 0 ]; then
        echo "Error: No chunks were generated for upload." >&2
        rm -rf "${'$'}tmp_dir"
        return 1
    fi

    if [ -n "${'$'}block_num" ] && [ "${'$'}seq" -ne "${'$'}block_num" ]; then
        echo "Warning: Uploaded chunk count (${'$'}seq) differs from block_num (${'$'}block_num) returned by server." >&2
    fi

    local finish_payload
    finish_payload="$(jq -n --arg upload_id "${'$'}upload_id" --argjson block_num "${'$'}seq" '{upload_id:${'$'}upload_id,block_num:${'$'}block_num}')"

    local finish_response
    finish_response=$(curl -sS --location --request POST 'https://open.feishu.cn/open-apis/drive/v1/files/upload_finish' \
        --header "${'$'}auth_header" \
        --header 'Content-Type: application/json; charset=utf-8' \
        --data "${'$'}finish_payload")
    if [ $? -ne 0 ]; then
        echo "Error: Failed to contact upload_finish endpoint." >&2
        rm -rf "${'$'}tmp_dir"
        return 1
    fi

    local finish_code
    finish_code="$(echo "${'$'}finish_response" | jq -r '.code // -1')"
    if [ "${'$'}finish_code" != "0" ]; then
        echo "Error: upload_finish failed with code ${'$'}finish_code" >&2
        echo "Response: ${'$'}finish_response" >&2
        rm -rf "${'$'}tmp_dir"
        return 1
    fi

    rm -rf "${'$'}tmp_dir"
    echo "Upload completed successfully."
}

feishu_upload_file _deploy/app-release.apk EgwcfU39olEhT1dtX3fcWRF0nig build-20.apk
""".trimIndent()

    }

    fun createLarkDriveDeployment(
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
                        ${larkUpload(file, parentNode, rename)}
                        
                    """.trimIndent()
                }
            }

            requirements {
                exists("env.GH_CLI")
            }
        }
    }

}