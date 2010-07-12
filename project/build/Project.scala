import sbt._


object AkkaRepositories {
  val AkkaRepo        = MavenRepository("Akka Repository", "http://scalablesolutions.se/akka/repository")
  val GuiceyFruitRepo = MavenRepository("GuiceyFruit Repo", "http://guiceyfruit.googlecode.com/svn/repo/releases/")
  val JBossRepo       = MavenRepository("JBoss Repo", "https://repository.jboss.org/nexus/content/groups/public/")
  val SunJDMKRepo     = MavenRepository("Sun JDMK Repo", "http://wp5.e-taxonomy.eu/cdmlib/mavenrepo")
  val JavaNetRepo     = MavenRepository("java.net Repo", "http://download.java.net/maven/2")
  val CodehausRepo    = MavenRepository("Codehaus Snapshots", "http://snapshots.repository.codehaus.org")

  val sonatypeSnapshotRepo = MavenRepository("Sonatype OSS Repo", "http://oss.sonatype.org/content/repositories/releases")
}


class Project(info: ProjectInfo) extends DefaultWebProject(info) {
  import AkkaRepositories._

  // Module configurations
  val netLagModuleConfig      = ModuleConfiguration("net.lag", AkkaRepo)
  val sbinaryModuleConfig     = ModuleConfiguration("sbinary", AkkaRepo)
  val redisModuleConfig       = ModuleConfiguration("com.redis", AkkaRepo)
  val atmosphereModuleConfig  = ModuleConfiguration("org.atmosphere", sonatypeSnapshotRepo)
  val facebookModuleConfig    = ModuleConfiguration("com.facebook", AkkaRepo)
  val jsr166xModuleConfig     = ModuleConfiguration("jsr166x", AkkaRepo)
  val sjsonModuleConfig       = ModuleConfiguration("sjson.json", AkkaRepo)
  val voldemortModuleConfig   = ModuleConfiguration("voldemort.store.compress", AkkaRepo)
  val cassandraModuleConfig   = ModuleConfiguration("org.apache.cassandra", AkkaRepo)
  val guiceyFruitModuleConfig = ModuleConfiguration("org.guiceyfruit", GuiceyFruitRepo)
  val jbossModuleConfig       = ModuleConfiguration("org.jboss", JBossRepo)
  val nettyModuleConfig       = ModuleConfiguration("org.jboss.netty", JBossRepo)
  val jgroupsModuleConfig     = ModuleConfiguration("jgroups", JBossRepo)
  val jmsModuleConfig         = ModuleConfiguration("javax.jms", SunJDMKRepo)
  val jdmkModuleConfig        = ModuleConfiguration("com.sun.jdmk", SunJDMKRepo)
  val jmxModuleConfig         = ModuleConfiguration("com.sun.jmx", SunJDMKRepo)
  val jerseyModuleConfig      = ModuleConfiguration("com.sun.jersey", JavaNetRepo)
  val jerseyContrModuleConfig = ModuleConfiguration("com.sun.jersey.contribs", JavaNetRepo)
  val grizzlyModuleConfig     = ModuleConfiguration("com.sun.grizzly", JavaNetRepo)
  val liftModuleConfig        = ModuleConfiguration("net.liftweb", ScalaToolsSnapshots)
  val multiverseModuleConfig  = ModuleConfiguration("org.multiverse", CodehausRepo)
  val specsModuleConfig       = ModuleConfiguration("org.scala-tools.testing", ScalaToolsSnapshots)


  // Helper for Akka dependencies
  val AkkaVersion = "0.10"
  def akkaModule(module: String) = "se.scalablesolutions.akka" %% ("akka-" + module) % AkkaVersion


  // Dependencies
  lazy val akkaCore   = akkaModule("core")
  lazy val akkaKernel = akkaModule("kernel")
  lazy val akkaHttp   = akkaModule("http")
  lazy val specs      = "org.scala-tools.testing" %% "specs" % "1.6.5-SNAPSHOT" % "test"

  // redis client dependecy
  val redis = "com.redis" % "redisclient" % "2.8.0.Beta1-1.3-SNAPSHOT" % "compile" from (
            "http://github.com/jboner/akka/blob/master/embedded-repo/com/redis/redisclient/2.8.0.Beta1-1.3-SNAPSHOT/redisclient-2.8.0.Beta1-1.3-SNAPSHOT.jar")

  val JettyVersion  = "7.0.2.v20100331"
  lazy val jserver  = "org.eclipse.jetty"  % "jetty-server" % JettyVersion % "test"
  lazy val jwebapp  = "org.eclipse.jetty"  % "jetty-webapp" % JettyVersion % "test"

}
