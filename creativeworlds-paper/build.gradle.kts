plugins {
    kotlin("jvm")
    id("net.minecrell.plugin-yml.bukkit") version "0.5.2"
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("maven-publish")
}

group = "me.thojs"
version = "1.0.1"

repositories {
    mavenLocal()
    mavenCentral()
    maven("https://maven.enginehub.org/repo/")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    compileOnly("io.papermc.paper:paper-api:1.19.4-R0.1-SNAPSHOT")

    compileOnly("com.sk89q.worldedit:worldedit-core:7.2.9")

    implementation("me.thojs:kommandhandler-core:1.0")
    implementation("me.thojs:kommandhandler-bukkit:1.0")
}

tasks {
    assemble {
        dependsOn(shadowJar)
    }
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

bukkit {
    name = "CreativeWorlds"
    main = "me.thojs.creativeworlds.paper.CreativeWorlds"
    apiVersion = "1.19"
    softDepend = listOf("WorldEdit")
    authors = listOf("Thojs")
}

publishing {
    publications {
        create("maven", MavenPublication::class.java) {
            from(components["java"])
        }
    }
}