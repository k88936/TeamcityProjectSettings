package utils.build_steps

fun getDockerRunParameters(): String = "--rm -v /cache:/cache ${getDockerRunProxyParameters()}"

private fun getDockerRunProxyParameters(): String {
    val proxyHost = "wallence.wallence.svc.cluster.local"
    val proxyPort = "7890"
    val proxyUrl = "http://$proxyHost:$proxyPort"
    return """
        --env NO_PROXY="localhost,127.0.0.1,::1,$proxyHost,.local"
        --env no_proxy="localhost,127.0.0.1,::1,$proxyHost,.local" 
        --env HTTP_PROXY=$proxyUrl
        --env http_proxy=$proxyUrl 
        --env HTTPS_PROXY=$proxyUrl
        --env https_proxy=$proxyUrl
        --env 'GRADLE_OPTS=-Dhttp.proxyHost=$proxyHost -Dhttp.proxyPort=$proxyPort -Dhttps.proxyHost=$proxyHost -Dhttps.proxyPort=$proxyPort -Dhttp.nonProxyHosts=localhost|127.0.0.1|::1|$proxyHost|*.local -Dhttps.protocols=TLSv1.2,TLSv1.3'
    """.trimIndent()
}