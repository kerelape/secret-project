plugins {
    id("fabric-loom")
    kotlin("jvm").version(System.getProperty("kotlin_version"))
}

base {
    archivesName.set(project.extra["archives_base_name"] as String)
}

version = project.extra["mod_version"] as String
group = project.extra["maven_group"] as String

repositories {}

dependencies {
    val minecraftVersion = project.extra["minecraft_version"] as String
    minecraft("com.mojang", "minecraft", minecraftVersion)

    val yarnMapping = project.extra["yarn_mappings"] as String
    mappings("net.fabricmc", "yarn", yarnMapping, null, "v2")

    val loaderVersion = project.extra["loader_version"] as String
    modImplementation("net.fabricmc", "fabric-loader", loaderVersion)

    val fabricVersion = project.extra["fabric_version"] as String
    modImplementation("net.fabricmc.fabric-api", "fabric-api", fabricVersion)

    val fabricKotlinVersion = project.extra["fabric_language_kotlin_version"] as String
    modImplementation("net.fabricmc", "fabric-language-kotlin", fabricKotlinVersion)

    modImplementation("org.eclipse.jgit", "org.eclipse.jgit", "3.5.0.201409260305-r")

    modImplementation("org.jetbrains.kotlinx", "kotlinx-coroutines-core", "1.6.4")
}

tasks {
    val javaVersion = JavaVersion.toVersion((project.extra["java_version"] as String).toInt())

    withType<JavaCompile> {
        options.encoding = "UTF-8"
        sourceCompatibility = javaVersion.toString()
        targetCompatibility = javaVersion.toString()
        options.release.set(javaVersion.toString().toInt())
    }

    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            jvmTarget = javaVersion.toString()
        }
    }

    jar {
        from("LICENSE") {
            rename { "${it}_${base.archivesName.get()}" }
        }
    }

    processResources {
        filesMatching("fabric.mod.json") {
            expand(
                mutableMapOf(
                    "version" to project.extra["mod_version"] as String,
                    "fabricloader" to project.extra["loader_version"] as String,
                    "fabric_api" to project.extra["fabric_version"] as String,
                    "fabric_language_kotlin" to project.extra["fabric_language_kotlin_version"] as String,
                    "minecraft" to project.extra["minecraft_version"] as String,
                    "java" to project.extra["java_version"] as String,
                ),
            )
        }
        filesMatching("*.mixins.json") {
            expand(
                mutableMapOf(
                    "java" to project.extra["java_version"] as String,
                ),
            )
        }
    }

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(javaVersion.toString()))
        }
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
        withSourcesJar()
    }
}
