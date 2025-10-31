package WebstormProjects.ReactNative.Fcalender.frontend.vcsRoots

import jetbrains.buildServer.configs.kotlin.vcs.GitVcsRoot

object FcalenderFrontendVCS : GitVcsRoot({
    name = "git@github.com:tangerinesodayeah/Fcalendar.git#refs/heads/main"
    url = "git@github.com:tangerinesodayeah/Fcalendar.git"
    branch = "refs/heads/main"
    branchSpec = """
        +:refs/heads/frontend
        +:refs/heads/frontend*
        +:refs/heads/shq
        +:refs/heads/wdy
    """.trimIndent()
    authMethod = uploadedKey {
        uploadedKey = "id_rsa"
    }
})
