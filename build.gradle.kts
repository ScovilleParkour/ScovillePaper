plugins {
    kotlin("jvm") version "2.4.0-Beta1"
    id("com.gradleup.shadow") version "9.4.1"
    id("xyz.jpenilla.run-paper") version "3.0.2"
    kotlin("plugin.serialization") version "2.1.20"
}

group = "dev.meluhdy"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/") {
        name = "papermc-repo"
    }
    maven("https://oss.sonatype.org/content/groups/public/") {
        name = "sonatype"
    }
    maven("https://repo.onarandombox.com/content/groups/public/") {
        name = "onarandombox"
    }
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.11-R0.1-SNAPSHOT")
    compileOnly(files(project.findProperty("melodiaPath") as String))
    compileOnly("net.luckperms:api:5.4")
}

tasks {
    runServer {
        // Configure the Minecraft version for our task.
        // This is the only required configuration besides applying the plugin.
        // Your plugin's jar (or shadowJar if present) will be used automatically.
        minecraftVersion("1.21")
    }
}

val targetJavaVersion = 25
kotlin {
    jvmToolchain(targetJavaVersion)
}

tasks.build {
    dependsOn("shadowJar")
}

tasks.processResources {
    val props = mapOf("version" to version)
    inputs.properties(props)
    filteringCharset = "UTF-8"
    filesMatching("paper-plugin.yml") {
        expand(props)
    }
}

tasks.shadowJar {
    dependencies {
        exclude(dependency("org.jetbrains.kotlin:.*"))
        exclude(dependency("org.jetbrains.kotlinx:kotlinx-serialization-core"))
    }
}