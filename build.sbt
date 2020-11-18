name := """project-soen6441"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.13.1"

libraryDependencies += guice

libraryDependencies ++= Seq(
  javaWs
)

libraryDependencies ++= Seq(
    "com.google.code.gson" % "gson" % "2.2.4"
)

libraryDependencies ++= Seq("org.mockito" % "mockito-core" % "3.5.13" % Test)
