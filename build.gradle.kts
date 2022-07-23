plugins {
    kotlin("jvm") version "1.7.0"

    id("com.github.johnrengelman.shadow") version "7.1.2"
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

group = "net.parchat"
version = "1.0.0"
description = "A discord bot for Parchat."

repositories {
    maven("https://repo.triumphteam.dev/snapshots/")

    maven("https://jitpack.io")

    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib", "1.7.0"))

    implementation("commons-cli:commons-cli:1.5.0")

    implementation("net.dv8tion:JDA:5.0.0-alpha.13")

    implementation("ch.qos.logback:logback-classic:1.2.11")

    implementation("org.litote.kmongo:kmongo-async:4.6.1")

    implementation("me.carleslc.Simple-YAML:Simple-Yaml:1.8")

    implementation("commons-validator:commons-validator:1.7")

    implementation("dev.triumphteam:triumph-core-jda:1.0.0-SNAPSHOT")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
}

tasks {
    shadowJar {
        relocate("dev.triumphteam.core", "net.parchat.plugin.libs")

        archiveFileName.set("Parchment-v${rootProject.version}.jar")
    }

    compileKotlin {
        kotlinOptions {
            jvmTarget = "17"
        }
    }
}