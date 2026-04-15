package DockerProjects

import jetbrains.buildServer.configs.kotlin.Project
import utils.vcs.GitVcsRootTemplate

fun DockerProjectTemplate(
    gitSshUrl: String,
    dockerfilePath: String,
    targetContainerTag: String,
): Project {
    val repositoryName = extractRepositoryName(gitSshUrl)
    val inputFingerprint = stableFingerprint("$gitSshUrl|$dockerfilePath|$targetContainerTag")
    val projectId = "DockerProject_${toTeamCityToken(repositoryName)}_$inputFingerprint"
    val buildTypeId = "${projectId}_Build"
    val vcsRoot = GitVcsRootTemplate(gitSshUrl)
    val buildType = DockerBuildTemplate(
        name = buildTypeId,
        imageName = targetContainerTag,
        dockerfilePath = dockerfilePath,
        vcsRoot = vcsRoot,
    )

    return Project({
        id(projectId)
        name = "$repositoryName docker build"

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
