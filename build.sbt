name := "TypeChef Busybox Analysis"

version := "0.4.2"

scalaVersion := "2.13.8"

organization := "de.fosd.typechef"


libraryDependencies += "com.github.scopt" %% "scopt" % "4.1.0"

libraryDependencies += "org.scala-lang.modules" %% "scala-parser-combinators" % "2.1.1"

libraryDependencies += "de.fosd.typechef" %% "frontend" % "0.4.2"

TaskKey[File]("mkrun") <<= (baseDirectory, fullClasspath in Runtime, mainClass in Runtime) map { (base, cp, main) =>
  val template = """#!/bin/sh
java -ea -Xmx2G -Xms128m -Xss10m -classpath "%s" %s "$@"
"""
  val mainStr = ""
  val contents = template.format(cp.files.absString, mainStr)
  val out = base / "run.sh"
  IO.write(out, contents)
  out.setExecutable(true)
  out
}
