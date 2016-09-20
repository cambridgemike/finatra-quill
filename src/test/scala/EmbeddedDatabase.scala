package com.twitter.manderson.mvc.tests

import com.twitter.manderson.mvc.modules.Context

import com.twitter.finagle.mysql._
import com.twitter.finagle.mysql.util.{EmbeddableMysql, SqlSource}
import com.twitter.util.{Future, Await}
import io.getquill._
import java.net.URL

class TestContext extends Context(new FinagleMysqlContextConfig(com.typesafe.config.ConfigFactory.load) {
  val embeddedDatabase = new EmbeddableMysql("user", "pass", "test")
  embeddedDatabase.start()
  override def client = embeddedDatabase.createClient()
}) {
  private def executeQueries(queries: Iterator[String]) : Future[Unit] = {
    queries.foldLeft(Future.Done) { (f, query) => 
      f before executeQuery(query).unit
    }
  }

  private def executeSql(urls: Seq[URL]) = {
    urls.foldLeft(Future.Done) { (f, url) =>
      f before executeQueries(SqlSource.fromUrl(url))
    }
  }

  def resetDb() = {
    Await.result(executeSql(Seq(
      getClass.getResource("/drop-db.sql"),
      getClass.getResource("/schema.sql")
    )))
  }
}