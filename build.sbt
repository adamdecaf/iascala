name := "iascala"

version := "1.0"

scalaVersion := "2.9.1"

libraryDependencies ++= Seq(
      "org.scalaz" %% "scalaz-core" % "6.0.4",
      "commons-io" % "commons-io" % "2.1",
      "com.mongodb.casbah" %% "casbah" % "2.1.5-1",
      "com.novus" %% "salat-core" % "0.0.8-SNAPSHOT",
      "org.specs2" %% "specs2" % "1.9" % "test",
      "redis.clients" % "jedis" % "2.0.0",
      "net.debasishg" % "sjson_2.9.0-1" % "0.14"
      )
