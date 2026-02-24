package AndroidStudioProjects

import jetbrains.buildServer.configs.kotlin.Project

object Project : Project({
    id("AndroidStudioProjects")
    name = "Android Studio Projects"

    subProject(AndroidStudioProjects.NextcloudTV.Project)
})
