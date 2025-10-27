package WebstormProjects.ReactNative.Fcalender.vcsRoots

import jetbrains.buildServer.configs.kotlin.vcs.GitVcsRoot

object ReactNativeDemo_GitGithubComK88936reactNativeDemoGitRefsHeadsMain : GitVcsRoot({
    name = "git@github.com:k88936/react-native-demo.git#refs/heads/main"
    url = "git@github.com:k88936/react-native-demo.git"
    branch = "refs/heads/main"
    branchSpec = "refs/heads/*"
    authMethod = uploadedKey {
        uploadedKey = "id_rsa"
    }
})
