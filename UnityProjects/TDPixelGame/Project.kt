package UnityProjects.TDPixelGame

import UnityProjects.TDPixelGame.buildTypes.TDPixelGame_Build
import UnityProjects.TDPixelGame.vcsRoots.TDPixelGame_GitGithubCom20220120802dPixelGameGitRefsHeadsMain
import jetbrains.buildServer.configs.kotlin.Project

object Project : Project({
    id("TDPixelGame")
    name = "2D Pixel Game"

    vcsRoot(TDPixelGame_GitGithubCom20220120802dPixelGameGitRefsHeadsMain)

    buildType(TDPixelGame_Build)
})