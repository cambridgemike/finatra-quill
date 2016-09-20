package com.twitter.manderson.mvc.modules

import com.google.inject.Provides
import com.twitter.inject.TwitterModule
import com.twitter.inject.annotations.Flag
import javax.inject.Singleton


case class Credentials(
  username: String,
  password: String,
  databaseHost: String,
  database: String
)

object CredentialProviderModule extends TwitterModule {
  @Singleton
  @Provides
  def credentialsProvider(@Flag("env") env: String) : Credentials = {
    env match {
      case "test" => Credentials("", "", "", "")
      case "devel"  => Credentials("mike", "password", "127.0.0.1:3306", "mvc-demo")
    }
  }
}
