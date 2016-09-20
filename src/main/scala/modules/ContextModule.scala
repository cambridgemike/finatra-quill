package com.twitter.manderson.mvc.modules

import com.twitter.manderson.mvc.models.FullName
import com.google.inject.Provides
import com.twitter.inject.TwitterModule
import com.twitter.inject.annotations.Flag
import io.getquill._
import javax.inject.Singleton

class Context(config: FinagleMysqlContextConfig) extends FinagleMysqlContext[SnakeCase](config) with DatabaseExtensions

trait DatabaseExtensions { this: Context =>
  implicit val encodeCustom = MappedEncoding[FullName, String](_.serialize)
  implicit val decodeCustom = MappedEncoding[String, FullName](FullName(_, ""))
}

object ContextProviderModule extends TwitterModule {
  @Singleton
  @Provides
  def provideDatabaseStore(@Flag("env") env: String, creds: Credentials) : Context = {
    new Context(new FinagleMysqlContextConfig(com.typesafe.config.ConfigFactory.load) {
      override def dest = creds.databaseHost
      override def user = creds.username
      override def password = creds.password
      override def database = creds.database
    })
  }
}
