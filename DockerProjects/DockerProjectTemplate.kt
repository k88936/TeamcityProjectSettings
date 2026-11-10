package DockerProjects

import jetbrains.buildServer.configs.kotlin.Project
import utils.vcs.GitVcsRootTemplate

fun DockerProjectTemplate(
    gitSshUrl: String,
    dockerfilePath: String,
    imageName: String,
    tags: List<String> = listOf("latest"),
    connection: String,
): Project {
    val repositoryName = extractRepositoryName(gitSshUrl)
    val inputFingerprint = stableFingerprint("$gitSshUrl|$dockerfilePath")
    val projectId = "DockerProject_${toTeamCityToken(repositoryName)}_$inputFingerprint"
    val buildTypeId = "${projectId}_Build"
    val vcsRoot = GitVcsRootTemplate(gitSshUrl)
    val buildType = DockerBuildTemplate(
        id = buildTypeId,
        name = "Build Container",
        imageName = imageName,
        tags = tags,
        dockerfilePath = dockerfilePath,
        vcsRoot = vcsRoot,
        connection
    )

    return Project({
        id(projectId)
        name = repositoryName

        vcsRoot(vcsRoot)
        buildType(buildType)
    })
}

private fun extractRepositoryName(gitSshUrl: String): String {
    val repositorySegment = gitSshUrl
        .substringBefore('#')
        .trim()
        .substringAfterLast('/')
        .substringAfterLast(':')
        .removeSuffix(".git")
    return repositorySegment
}

private fun toTeamCityToken(value: String): String {
    val token = value
        .replace("[^0-9a-zA-Z]".toRegex(), "_")
        .trim('_')
    return token
}

private fun stableFingerprint(value: String): String = value.hashCode().toUInt().toString(16)
