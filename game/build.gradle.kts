import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

repositories {
    maven("https://repo.lunarclient.dev")
    maven("https://repo.glaremasters.me/repository/concuncan/")
}

dependencies {
    api(project(":shared"))
    kapt("gg.scala.commons:bukkit:3.4.5")
    compileOnly("gg.scala.cgs:game:1.4.2")

    compileOnly("com.lunarclient:apollo-api:1.0.6")
    compileOnly("com.lunarclient:apollo-extra-adventure4:1.0.6")

    compileOnly("com.grinderwolf:slimeworldmanager-plugin:2.2.1")
}

tasks {
    withType<ShadowJar> {
        archiveFileName = "SGGame.jar"
    }
}
