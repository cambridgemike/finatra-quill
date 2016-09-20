package com.twitter.manderson.mvc.tests

import org.scalatest.FunSuite
import com.twitter.manderson.mvc.models._
import com.twitter.util.Await
import org.scalatest.{BeforeAndAfterAll, BeforeAndAfterEach}

class UserSuite extends FunSuite with BeforeAndAfterEach with BeforeAndAfterAll {

  private var testContext: TestContext = _
  private var userStore: UserStore = _
  private var tweetStore: TweetStore = _

  override def beforeAll() {
    testContext = new TestContext()
    userStore = new UserStore(testContext)
    tweetStore = new TweetStore(testContext)
  }

  override def beforeEach() {
    testContext.resetDb()
  }

  val user = User(username="cambridgemike")
  test("User.save") {
    val userId = Await.result(userStore.save(user))
    assert(userId > 0)
  }

  test("User.findById") {
    val userId = Await.result(userStore.save(user))
    val findUser = Await.result(userStore.findById(userId))
    assert(findUser.length == 1)
  }

  test("User.tweets") {
    val userId = Await.result(userStore.save(user))
    val tweet = Tweet(body="setting up my twttr", userId = userId)
    Await.result(tweetStore.save(tweet))
    val userTweets = Await.result(userStore.tweets(Set(userId)))

    assert(userTweets.length == 1)
  }

  // test("User.findByIds") {
  //   val userId = Await.result(userStore.save(user))
  //   val users = Await.result(userStore.findByIds(Set(userId)))

  //   assert(users.length == 1)
  // }
}