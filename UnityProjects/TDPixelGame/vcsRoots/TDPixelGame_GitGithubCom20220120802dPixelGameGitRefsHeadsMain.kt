package UnityProjects.TDPixelGame.vcsRoots

import jetbrains.buildServer.configs.kotlin.vcs.GitVcsRoot

object TDPixelGame_GitGithubCom20220120802dPixelGameGitRefsHeadsMain : GitVcsRoot({
    name = "git@github.com:2022012080/2D-Pixel-Game.git#refs/heads/main"
    url = "git@github.com:2022012080/2D-Pixel-Game.git"
    branch = "refs/heads/main"
    branchSpec = "refs/heads/*"
    agentCleanFilesPolicy = AgentCleanFilesPolicy.IGNORED_ONLY
    authMethod = uploadedKey {
        uploadedKey = "id_rsa"
    }
})