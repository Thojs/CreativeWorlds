import net.minecrell.pluginyml.bukkit.BukkitPluginDescription.Permission

plugins {
    kotlin("jvm")
    alias(libs.plugins.pluginyml.paper)
    alias(libs.plugins.shadow)
    id("maven-publish")
}

group = "me.thojs"
version = "1.1.0-SNAPSHOT"

repositories {
    mavenLocal()
    mavenCentral()
    maven("https://maven.enginehub.org/repo/")
}

dependencies {
    implementation(kotlin("stdlib"))
    compileOnly(libs.paper.api)

    compileOnly(libs.worldedit)

    implementation(libs.cloud.paper)
    implementation(libs.cloud.kotlin)
}

tasks {
    assemble {
        dependsOn(shadowJar)
    }
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

paper {
    name = "CreativeWorlds"
    main = "me.thojs.creativeworlds.paper.CreativeWorlds"
    apiVersion = "1.21"
    authors = listOf("Thojs")

    serverDependencies {
        register("WorldEdit") {
            required = false
        }
    }

    permissions {
        create("creativeworlds.command.unload") {
            default = Permission.Default.OP
        }
    }
}

publishing {
    publications {
        create("maven", MavenPublication::class.java) {
            from(components["java"])
        }
    }
}