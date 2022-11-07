@file:Suppress("UnstableApiUsage")

plugins {
    application
}

repositories {
    mavenCentral()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

dependencies {
    implementation("com.google.guava:guava:31.0.1-jre")
}

testing {
    suites {
        val test by getting(JvmTestSuite::class) {
            useJUnitJupiter("5.8.2")
        }

        val integrationTest by registering(JvmTestSuite::class) {
            dependencies {
                implementation(project()) // should use `project()` for the slides
            }
            useJUnitJupiter()
            targets.all { testTask.configure { shouldRunAfter(test) } }
        }
    }
}

tasks.named("check") {
    dependsOn(testing.suites.named("integrationTest"))
}

application {
    mainClass.set("my.App")
}
