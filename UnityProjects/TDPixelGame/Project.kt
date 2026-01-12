package UnityProjects.TDPixelGame

import UnityProjects.TDPixelGame.buildTypes.TDPixelGame_Build
import UnityProjects.TDPixelGame.buildTypes.TDPixelGame_Deploy
import jetbrains.buildServer.configs.kotlin.Project
import utils.vcs.GitVcsRootTemplate

val vcsRoot = GitVcsRootTemplate("git@github.com:2022012080/2D-Pixel-Game.git")
object Project : Project({
    id("TDPixelGame")
    name = "2D Pixel Game"

    vcsRoot(vcsRoot)

    buildType(TDPixelGame_Build)
    buildType(TDPixelGame_Deploy)
})