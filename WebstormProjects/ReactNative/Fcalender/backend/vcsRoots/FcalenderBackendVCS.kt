package WebstormProjects.ReactNative.Fcalender.backend.vcsRoots

import jetbrains.buildServer.configs.kotlin.vcs.GitVcsRoot

object FcalenderBackendVCS : GitVcsRoot({
    name = "git@github.com:tangerinesodayeah/Fcalendar.git#refs/heads/main"
    url = "git@github.com:tangerinesodayeah/Fcalendar.git"
    branch = "refs/heads/main"
    branchSpec = """
        +:refs/heads/backend*
        +:refs/heads/zyc
        +:refs/heads/zwr
    """.trimIndent()
    authMethod = uploadedKey {
        uploadedKey = "id_rsa"
    }
})
