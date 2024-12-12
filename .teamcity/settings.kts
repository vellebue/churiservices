import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildSteps.maven

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

version = "2024.03"

project {
    description = "Churiservices Building Main Project"

    buildType(ChuriservicesGlobalBuilding)
    buildType(ChuriservicesGolbalTesting)
}

object ChuriservicesGlobalBuilding : BuildType({
    name = "Churiservices Global Building"
    description = "Main building task to build churiservices"

    artifactRules = """
        articles/target/articles-1.0-SNAPSHOT-exec.jar
        orders/target/orders-1.0-SNAPSHOT-exec.jar
    """.trimIndent()

    vcs {
        root(DslContext.settingsRoot)
    }

    steps {
        maven {
            name = "Build All"
            id = "Build_All"
            goals = "clean install"
            runnerArgs = "-s /usr/shared/maven/settings.xml"
        }
    }
})

object ChuriservicesGolbalTesting : BuildType({
    name = "Churiservices Global Testing"
    description = "Churiservices Executor For All Integration Tests"

    params {
        param("env.DEFAULT_JWT_URL", "http://localhost:8000/auth/realms/ChuriservicesRealm/protocol/openid-connect/certs")
        param("env.ORDERS_APP_DB_USERNAME", "churiservices_orders")
        param("env.STORAGE_APP_JDBC_URL", "jdbc:postgresql://localhost:6963/churiservices_storage")
        param("env.DELIVERIES_APP_JDBC_URL", "dbc:postgresql://localhost:6962/churiservices_deliveries")
        param("env.STORAGE_APP_DB_USERNAME", "churiservices_storage")
        param("env.STORAGE_APP_PORT", "7083")
        param("env.ORDERS_APP_DB_PASSWORD", "angel7")
        param("env.DELIVERIES_APP_DB_PASSWORD", "angel7")
        param("env.ORDERS_APP_PORT", "7080")
        param("env.ARTICLES_APP_JDBC_URL", "jdbc:postgresql://localhost:6961/churiservices_articles")
        param("env.STORAGE_APP_DB_PASSWORD", "angel7")
        param("env.ORDERS_APP_JDBC_URL", "jdbc:postgresql://localhost:6960/churiservices_orders")
        param("env.ARTICLES_APP_DB_PASSWORD", "angel7")
        param("env.ARTICLES_APP_DB_USERNAME", "churiservices_articles")
        param("env.DELIVERIES_APP_DB_USERNAME", "churiservices_deliveries")
        param("env.DELIVERIES_APP_PORT", "7082")
        param("env.ARTICLES_APP_PORT", "7081")
    }

    vcs {
        root(DslContext.settingsRoot)
    }

    steps {
        maven {
            id = "Maven2"
            goals = "clean integration-test verify site"
            runnerArgs = "-Dskip.integration.tests=false -s /usr/shared/maven/settings.xml"
            jdkHome = "%env.JDK_21_0_x64%"
        }
    }
})
