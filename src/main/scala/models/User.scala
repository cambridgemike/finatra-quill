package com.twitter.manderson.mvc.models

import com.twitter.manderson.mvc.modules.Context


case class FullName(first: String, last: String) {
  def serialize = first + " " + last
}

case class User(
  override val id: Int = -1,
  username: String,
  name: Option[FullName] = None
) extends BaseModel

object User {
  def schema(context: Context) = {
    import context._
    quote { query[User].schema(
        _.entity("users")
         .columns(_.username -> "account")
      ) 
    }
  }
}

class UserStore(val context: Context) extends ModelStore[User] {
  import context._

  private val tweetsForUser = quote {
    (user: User) =>
      Tweet.schema(context).filter(_.userId == user.id)
  }

  def tweets(userIds: Set[Int]) = {
    context.run(quote {
      for {
        user <- User.schema(context)
        tweet <- Tweet.schema(context).filter(_.userId == user.id)
      } yield (user)
    })
  }

  def save(user: User) = {
    context.run(quote(
      User.schema(context).insert(lift(user)).returning(_.id)
    ))
  }

  def all() = {
    context.run(User.schema(context))
  }

  def findById(id: Int) = {
    context.run(quote(User.schema(context).filter(_.id == lift(id))))
  }
}