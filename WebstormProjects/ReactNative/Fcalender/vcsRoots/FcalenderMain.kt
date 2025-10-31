package WebstormProjects.ReactNative.Fcalender.vcsRoots

import jetbrains.buildServer.configs.kotlin.vcs.GitVcsRoot

object FcalenderMain : GitVcsRoot({
    id("FcalenderMain")
    name = "git@github.com:tangerinesodayeah/Fcalendar.git#refs/heads/main"
    url = "git@github.com:tangerinesodayeah/Fcalendar.git"
    branch = "refs/heads/main"
    branchSpec = "refs/heads/*"
    authMethod = uploadedKey {
        uploadedKey = "id_rsa"
    }
})
