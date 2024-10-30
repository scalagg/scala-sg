import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

dependencies {
    compileOnly("gg.scala.cgs:common:1.4.2")
}

tasks {
    withType<ShadowJar> {
        archiveFileName = "SGShared.jar"
    }
}
