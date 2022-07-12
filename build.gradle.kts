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
    mavenCentral()

    mavenLocal()

    maven("https://repo.triumphteam.dev/snapshots/")
}

dependencies {
    implementation(kotlin("stdlib", "1.7.0"))

    compileOnly("commons-validator:commons-validator:1.7")

    compileOnly("ch.qos.logback:logback-classic:1.2.11")

    compileOnly("net.dv8tion:JDA:5.0.0-alpha.13")

    compileOnly("commons-cli:commons-cli:1.5.0")

    implementation("net.parchat.parcore:Parcore:1.0.0")
}

tasks {
    shadowJar {
        archiveFileName.set("Parchment-v${rootProject.version}.jar")
    }

    compileKotlin {
        kotlinOptions {
            jvmTarget = "17"
        }
    }
}