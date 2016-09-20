package com.twitter.manderson.mvc

import com.twitter.manderson.mvc.modules._
import com.twitter.manderson.mvc.controllers._

import com.google.inject.Provides
import com.twitter.inject.annotations.Flag
import com.twitter.finatra.http.HttpServer
import com.twitter.finatra.http.filters.{CommonFilters, LoggingMDCFilter, TraceIdMDCFilter}
import com.twitter.finatra.http.routing.HttpRouter


object ServerMain extends Server

class Server extends HttpServer {
  val env = flag("env", "devel", "Environment that we are running in: devel, prod (default: devel)")

  override val modules = Seq(
    CredentialProviderModule,
    ContextProviderModule
  )

  override def configureHttp(router: HttpRouter) {
    router.add[UserController]
  }
}