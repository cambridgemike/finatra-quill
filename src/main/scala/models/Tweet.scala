package com.twitter.manderson.mvc.models

import com.twitter.manderson.mvc.modules.Context


case class Tweet(
  override val id: Int = -1,
  body: String,
  userId: Int
) extends BaseModel

object Tweet {
  def schema(context: Context) = {
    import context._
    quote { query[Tweet].schema(
        _.entity("tweets")
      ) 
    }
  }
}

class TweetStore(val context: Context) {
  import context._

  def save(tweet: Tweet) = {
    context.run(Tweet.schema(context).insert(lift(tweet)).returning(_.id))
  }
}