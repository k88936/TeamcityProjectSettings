package AndroidStudioProjects.NextcloudTv

import AndroidStudioProjects.NextcloudTv.buildTypes.NextcloudTv_Build
import AndroidStudioProjects.NextcloudTv.buildTypes.NextcloudTv_Deploy
import AndroidStudioProjects.NextcloudTv.vcsRoots.NextcloudTv_GitGithubComK88936nextcloudTvGitRefsHeadsMain
import jetbrains.buildServer.configs.kotlin.Project

object Project : Project({
    id("NextcloudTv")
    name = "Nextcloud TV"

    vcsRoot(NextcloudTv_GitGithubComK88936nextcloudTvGitRefsHeadsMain)

    buildType(NextcloudTv_Build)
    buildType(NextcloudTv_Deploy)
})
