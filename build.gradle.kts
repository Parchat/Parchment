import dev.triumphteam.helper.core
import dev.triumphteam.helper.Feature
import dev.triumphteam.helper.PlatformType
import dev.triumphteam.helper.feature

plugins {
    kotlin("jvm") version "1.7.10"

    id("me.mattstudios.triumph") version "0.3.1"

    id("com.github.johnrengelman.shadow") version "7.1.2"

    application
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
    implementation(kotlin("stdlib", "1.7.10"))

    implementation(libs.commons.cli)
    implementation(libs.commons.validator)

    implementation(libs.jda.alpha)
    implementation(libs.logback.classic)

    implementation(libs.triumph.core)
    implementation(libs.triumph.config)

    //implementation(feature(Feature.COMMANDS, PlatformType.JDA, "2.0.2"))
    //implementation(feature(Feature.LISTENERS, PlatformType.JDA, "2.0.2"))

    implementation(libs.kotlin.coroutines)
}

tasks {
    shadowJar {
        listOf(
            "dev.triumphteam.core",
            "ch.qos.logback",
            "net.dv8tion",
            "commons-cli",
            "commons-validator"
        ).onEach {
            relocate(it, "${rootProject.group}.plugin.libs.$it")
        }

        archiveFileName.set("Parchment-v${rootProject.version}.jar")
    }

    application {
        mainClass.set("net.parchat.parchment.ParchmentKt")
    }

    compileKotlin {
        kotlinOptions {
            jvmTarget = "17"
        }
    }
}