package com.twitter.manderson.mvc.controllers

import com.twitter.finatra.http.Controller
import com.twitter.finagle.http.{Request, Response}
import com.twitter.manderson.mvc.modules.Context
import com.twitter.manderson.mvc.models._
import javax.inject.Inject
import com.twitter.inject.Logging


class TweetController @Inject()(
  context: Context
) extends Controller with Logging {

  private val tweetStore = new TweetStore(context)

  get("/") { request : Request =>
    userStore.all().map(new UserView(_))
  }

  post("/") { request: Request =>
    val mike = User(username = "cambridgemike", name = Some(FullName("Mike", "Anderson")))
    userStore.save(mike)
  }
}