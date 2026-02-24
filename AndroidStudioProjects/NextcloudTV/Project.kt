package AndroidStudioProjects.NextcloudTV

import AndroidStudioProjects.NextcloudTV.buildTypes.NextcloudTV_Build
import AndroidStudioProjects.NextcloudTV.buildTypes.NextcloudTV_Deploy
import AndroidStudioProjects.NextcloudTV.vcsRoots.NextcloudTv_GitGithubComK88936nextcloudTVGitRefsHeadsMain
import jetbrains.buildServer.configs.kotlin.Project

object Project : Project({
    id("NextcloudTV")
    name = "Nextcloud TV"

    vcsRoot(NextcloudTv_GitGithubComK88936nextcloudTVGitRefsHeadsMain)

    buildType(NextcloudTV_Build)
    buildType(NextcloudTV_Deploy)
})
