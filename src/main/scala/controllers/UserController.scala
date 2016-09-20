package com.twitter.manderson.mvc.controllers

import com.twitter.finatra.http.Controller
import com.twitter.finagle.http.{Request, Response}
import com.twitter.manderson.mvc.modules.Context
import com.twitter.manderson.mvc.models._
import javax.inject.Inject
import com.twitter.inject.Logging

class UserController @Inject()(
  context: Context
) extends Controller with Logging {

  private val store = new UserStore(context)

  sealed trait UserUpdate

  case class TweepUpdate(
    handle: String
  ) extends UserUpdate

  case class EmployeeUpdate(
    account: String
  ) extends UserUpdate

  get("/") { request : Request =>
    store.all()
  }

  post("/") { request: Request =>
    val mike = User(username = "cambridgemike", name = Some(FullName("Mike", "Anderson")))
    store.save(mike)
  }

  get("/requesttest") { request : UserUpdate =>
    var resp = request match {
      case TweepUpdate(handle)     => "handle is " + handle
      case EmployeeUpdate(account) => "account is " + account
    }

    response.ok.json(resp)
  }
}