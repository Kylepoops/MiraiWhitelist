import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    kotlin("jvm") version "1.6.21"
    id("io.izzel.taboolib") version "1.38"
}

taboolib {
    install("common")
    install("common-5")
    install("platform-bukkit")
    install("module-configuration")
    install("module-chat")
    install("module-lang")
    install("module-nms")
    install("module-nms-util")
    install("module-effect")
    version = "6.0.9-5"

    description {
        bukkitApi("1.18")
        bukkitNodes = mapOf("api-version" to "1.18")
        dependencies {
            name("MiraiMC")
        }
        contributors {
            name("Kylepoops")
        }
    }
}

repositories {
    mavenCentral()
    maven("https://repo.tabooproject.org/storages/public/releases")
    maven("https://papermc.io/repo/repository/maven-public/")
}

dependencies {
    compileOnly(fileTree("libs"))
    compileOnly("io.papermc.paper:paper-api:1.18.2-R0.1-SNAPSHOT")
    compileOnly("com.google.code.gson:gson:2.8.5")
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

configure<JavaPluginExtension> {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
