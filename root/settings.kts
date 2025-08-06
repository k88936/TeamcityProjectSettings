import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.buildSteps.gradle
import jetbrains.buildServer.configs.kotlin.buildSteps.powerShell
import jetbrains.buildServer.configs.kotlin.buildSteps.script
import jetbrains.buildServer.configs.kotlin.failureConditions.BuildFailureOnText
import jetbrains.buildServer.configs.kotlin.failureConditions.failOnText
import jetbrains.buildServer.configs.kotlin.projectFeatures.buildReportTab
import jetbrains.buildServer.configs.kotlin.triggers.finishBuildTrigger
import jetbrains.buildServer.configs.kotlin.triggers.vcs
import jetbrains.buildServer.configs.kotlin.vcs.GitVcsRoot

/*
The settings script is an entry point for defining a TeamCity
project hierarchy. The script should contain a single call to the
project() function with a Project instance or an init function as
an argument.

VcsRoots, BuildTypes, Templates, and subprojects can be
registered inside the project using the vcsRoot(), buildType(),
template(), and subProject() methods respectively.

To debug settings scripts in command-line, run the

    mvnDebug org.jetbrains.teamcity:teamcity-configs-maven-plugin:generate

command and attach your debugger to the port 8000.

To debug in IntelliJ Idea, open the 'Maven Projects' tool window (View
-> Tool Windows -> Maven Projects), find the generate task node
(Plugins -> teamcity-configs -> teamcity-configs:generate), the
'Debug' option is available in the context menu for the task.
*/

version = "2025.03"

project {
    description = "Contains all other projects"

    features {
        buildReportTab {
            id = "PROJECT_EXT_1"
            title = "Code Coverage"
            startPage = "coverage.zip!index.html"
        }
    }

    cleanup {
        baseRule {
            preventDependencyCleanup = false
        }
    }

    subProject(UnityProjects)
    subProject(Fernflower)
    subProject(LeetcodeEditor)
    subProject(Shotmd)
}


object Fernflower : Project({
    name = "Fernflower"

    vcsRoot(Fernflower_GitGithubComK88936fernflowerGitRefsHeadsMaster)

    buildType(Fernflower_Build)
    buildType(Fernflower_Deploy)
})

object Fernflower_Build : BuildType({
    name = "Build"

    artifactRules = "build/libs/"

    vcs {
        root(Fernflower_GitGithubComK88936fernflowerGitRefsHeadsMaster)
    }

    steps {
        gradle {
            id = "gradle_runner"
            tasks = "clean build"
            gradleWrapperPath = ""
        }
    }

    triggers {
        vcs {
        }
    }

    features {
        perfmon {
        }
    }
})

object Fernflower_Deploy : BuildType({
    name = "Deploy"

    enablePersonalBuilds = false
    type = BuildTypeSettings.Type.DEPLOYMENT
    maxRunningBuilds = 1

    vcs {
        root(Fernflower_GitGithubComK88936fernflowerGitRefsHeadsMaster)
    }

    steps {
        script {
            id = "simpleRunner"
            scriptContent = "gh release create v%build.number% --generate-notes ghrelease/*"
        }
    }

    triggers {
        finishBuildTrigger {
            buildType = "${Fernflower_Build.id}"
            successfulOnly = true
        }
    }

    dependencies {
        artifacts(Fernflower_Build) {
            buildRule = lastSuccessful()
            artifactRules = "*.jar=>ghrelease/"
        }
    }
})

object Fernflower_GitGithubComK88936fernflowerGitRefsHeadsMaster : GitVcsRoot({
    name = "git@github.com:k88936/fernflower.git#refs/heads/master"
    url = "git@github.com:k88936/fernflower.git"
    branch = "refs/heads/master"
    branchSpec = "refs/heads/*"
    authMethod = uploadedKey {
        uploadedKey = "k99836 github"
    }
})


object LeetcodeEditor : Project({
    name = "Leetcode Editor"

    vcsRoot(LeetcodeEditor_GitGithubComK88936leetcodeEditorGitRefsHeadsMaster)

    buildType(LeetcodeEditor_Deploy)
    buildType(LeetcodeEditor_Build)
})

object LeetcodeEditor_Build : BuildType({
    name = "Build"

    artifactRules = "build/distributions"
    publishArtifacts = PublishMode.SUCCESSFUL

    vcs {
        root(LeetcodeEditor_GitGithubComK88936leetcodeEditorGitRefsHeadsMaster)
    }

    steps {
        gradle {
            id = "gradle_runner"
            tasks = "clean buildPlugin"
            jdkHome = "%env.JDK_17_0%"
        }
    }

    triggers {
        vcs {
        }
    }

    features {
        perfmon {
        }
    }
})

object LeetcodeEditor_Deploy : BuildType({
    name = "Deploy"

    enablePersonalBuilds = false
    type = BuildTypeSettings.Type.DEPLOYMENT
    maxRunningBuilds = 1

    params {
        password("env.GITHUB_TOKEN", "credentialsJSON:9b5eb1f1-fa5c-46a2-ad86-23ba0f76364d")
    }

    vcs {
        root(LeetcodeEditor_GitGithubComK88936leetcodeEditorGitRefsHeadsMaster)
    }

    steps {
        script {
            id = "simpleRunner"
            scriptContent = "gh release create v%build.number% --generate-notes ghrelease/*"
        }
    }

    triggers {
        finishBuildTrigger {
            buildType = "${LeetcodeEditor_Build.id}"
            successfulOnly = true
        }
    }

    dependencies {
        artifacts(LeetcodeEditor_Build) {
            buildRule = lastSuccessful()
            cleanDestination = true
            artifactRules = "*.zip=>ghrelease/"
        }
    }
})

object LeetcodeEditor_GitGithubComK88936leetcodeEditorGitRefsHeadsMaster : GitVcsRoot({
    name = "git@github.com:k88936/leetcode-editor.git#refs/heads/master"
    url = "git@github.com:k88936/leetcode-editor.git"
    branch = "refs/heads/master"
    branchSpec = "refs/heads/*"
    authMethod = uploadedKey {
        uploadedKey = "k99836 github"
    }
})


object Shotmd : Project({
    name = "Shotmd"

    vcsRoot(Shotmd_GitGithubComK88936ShotmdGitRefsHeadsMaster)

    buildType(Shotmd_Build)
    buildType(Shotmd_Deploy)
})

object Shotmd_Build : BuildType({
    name = "Build"

    artifactRules = "build/Shotmd.zip"

    vcs {
        root(Shotmd_GitGithubComK88936ShotmdGitRefsHeadsMaster)
    }

    steps {
        powerShell {
            id = "jetbrains_powershell"
            scriptMode = script {
                content = """
                    cmake -S . -B build -DCMAKE_TOOLCHAIN_FILE=%env.QT_WIN_INSTALL%/lib/cmake/Qt6/qt.toolchain.cmake -G Ninja 
                    cmake --build build --config Release
                    mkdir build/Shotmd
                    cp build/Shotmd.exe build/Shotmd
                    %env.QT_WIN_INSTALL%/bin/windeployqt.exe build/Shotmd/Shotmd.exe
                    cd build
                    7z a Shotmd.zip Shotmd/
                """.trimIndent()
            }
        }
    }

    triggers {
        vcs {
        }
    }

    features {
        perfmon {
        }
    }

    requirements {
        exists("env.QT_WIN_INSTALL")
    }
})

object Shotmd_Deploy : BuildType({
    name = "Deploy"

    enablePersonalBuilds = false
    type = BuildTypeSettings.Type.DEPLOYMENT
    maxRunningBuilds = 1

    vcs {
        root(Shotmd_GitGithubComK88936ShotmdGitRefsHeadsMaster)
    }

    steps {
        script {
            name = "New build step"
            id = "simpleRunner_1"
            scriptContent = "gh release create v%build.number% --generate-notes ghrelease/*"
        }
    }

    triggers {
        finishBuildTrigger {
            buildType = "${Shotmd_Build.id}"
            successfulOnly = true
        }
    }

    dependencies {
        artifacts(Shotmd_Build) {
            buildRule = lastSuccessful()
            artifactRules = "Shotmd.zip=>ghrelease/"
        }
    }
})

object Shotmd_GitGithubComK88936ShotmdGitRefsHeadsMaster : GitVcsRoot({
    name = "git@github.com:k88936/Shotmd.git#refs/heads/master"
    url = "git@github.com:k88936/Shotmd.git"
    branch = "refs/heads/master"
    branchSpec = "refs/heads/*"
    authMethod = uploadedKey {
        uploadedKey = "k99836 github"
    }
})


object UnityProjects : Project({
    name = "UnityProjects"

    subProject(Bubble)
})


object Bubble : Project({
    name = "Bubble"

    vcsRoot(Bubble_GitGithubComK88936BubbleGitRefsHeadsMain)

    buildType(Bubble_Build)
})

object Bubble_Build : BuildType({
    name = "Build"

    artifactRules = """
        Build\Windows => Build\Windows
        -:Build\Windows\Bubble_BurstDebugInformation_DoNotShip
        -:Build\Windows\DemoGame_BackUpThisFolder_ButDontShipItWithYourGame
    """.trimIndent()
    publishArtifacts = PublishMode.SUCCESSFUL

    vcs {
        root(Bubble_GitGithubComK88936BubbleGitRefsHeadsMain)
    }

    steps {
        step {
            id = "build"
            type = "unity"
            param("executeMethod", "DemoGame.Editor.BuildScript.PerformBuild_Windows")
            param("noGraphics", "true")
            param("buildTarget", "StandaloneWindows64")
        }
    }

    triggers {
        vcs {
        }
    }

    failureConditions {
        testFailure = false
        nonZeroExitCode = false
        javaCrash = false
        failOnText {
            conditionType = BuildFailureOnText.ConditionType.CONTAINS
            pattern = "Build Finished, Result: Failure."
            failureMessage = "Unity Build Failure."
            reverse = false
            stopBuildOnFailure = true
        }
    }

    features {
        perfmon {
        }
    }

    requirements {
        exists("env.UNITY_WIN_INSTALL")
    }
})

object Bubble_GitGithubComK88936BubbleGitRefsHeadsMain : GitVcsRoot({
    name = "git@github.com:k88936/Bubble.git#refs/heads/main"
    url = "git@github.com:k88936/Bubble.git"
    branch = "refs/heads/main"
    branchSpec = "refs/heads/*"
    agentCleanFilesPolicy = GitVcsRoot.AgentCleanFilesPolicy.IGNORED_ONLY
    authMethod = uploadedKey {
        uploadedKey = "k99836 github"
    }
})
