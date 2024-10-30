import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

dependencies {
    api(project(":shared"))
    kapt("gg.scala.commons:bukkit:3.4.3")

    compileOnly("gg.scala.cgs:lobby:1.4.2")
    compileOnly("gg.scala.tangerine:tangerine:1.2.2")
}

tasks {
    withType<ShadowJar> {
        archiveFileName = "SGLobby.jar"
    }
}

