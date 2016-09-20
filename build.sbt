name := "quilltest"

version := "1.0"

resolvers ++= Seq(
  Resolver.mavenLocal,
  Resolver.sonatypeRepo("releases"),
  "Twitter Maven" at "https://maven.twttr.com",
  // Used for the mysql dependencies
  "Twitter Artifactory" at "https://artifactory.twitter.biz/libs-releases-local/" 
)

libraryDependencies ++= Seq(
  "mysql" % "mysql-connector-mxj-gpl" % "5.0.11",
  "com.twitter.mysql" % "mysql-connector-mxj-db-files" % "5.0.12.3",
  "mysql" % "mysql-connector-java" % "5.1.36.t1",
  "io.getquill" %% "quill-finagle-mysql" % "0.10.0",
  "com.twitter" %% "finatra-http" % "2.3.0",
  "org.scalatest" %% "scalatest" % "3.0.0",
  "com.twitter" %% "finagle-mysql" % "6.37.0",
  "com.twitter.mysql-testing-utils" % "mysql-testing-utils" % "0.0.1-SNAPSHOT",
  "joda-time" % "joda-time" % "2.9.4",
  "ch.qos.logback" % "logback-classic" % "1.1.7"
)

scalaVersion := "2.11.8"
