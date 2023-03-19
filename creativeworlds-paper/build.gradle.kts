plugins {
    kotlin("jvm")
    id("io.papermc.paperweight.userdev") version "1.3.8"
    id("net.minecrell.plugin-yml.bukkit") version "0.5.2"
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("maven-publish")
}

group = "me.thojs"
version = "1.0"

repositories {
    mavenLocal()
    maven("https://maven.enginehub.org/repo/")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    paperDevBundle("1.19.2-R0.1-SNAPSHOT")

    compileOnly("com.sk89q.worldedit:worldedit-core:7.2.9")

    implementation("me.thojs:kommandhandler-core:1.0")
    implementation("me.thojs:kommandhandler-bukkit:1.0")
}

tasks {
    assemble {
        dependsOn(shadowJar)
        dependsOn(reobfJar)
    }

    compileKotlin {
        kotlinOptions.jvmTarget = JavaLanguageVersion.of(17).toString()
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