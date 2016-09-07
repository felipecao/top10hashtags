package services

import javax.inject.Singleton

import twitter4j.conf.ConfigurationBuilder
import twitter4j.{Paging, ResponseList, Status, TwitterFactory}
import play.api.Logger

import scala.util.Properties

trait TwitterConnection {
  val MaxTweets = 2000
  val FirstPage = 1
  def retrieveTweets(handle: String, limit: Int): ResponseList[Status]
}

@Singleton
class TwitterConnectionImpl extends TwitterConnection{

  def retrieveTweets(handle: String, total: Int): ResponseList[Status] = {
    val cb = new ConfigurationBuilder()

    cb.setDebugEnabled(true)
      .setOAuthConsumerKey(Properties.envOrElse("consumerKey", sys.props("consumerKey")))
      .setOAuthConsumerSecret(Properties.envOrElse("consumerSecret", sys.props("consumerSecret")))
      .setOAuthAccessToken(Properties.envOrElse("accessToken", sys.props("accessToken")))
      .setOAuthAccessTokenSecret(Properties.envOrElse("accessTokenSecret", sys.props("accessTokenSecret")))

    val tf = new TwitterFactory(cb.build())
    val twitter = tf.getInstance()
    val limit = math.min(total, MaxTweets)

    return twitter.getUserTimeline(handle, new Paging(FirstPage, limit))
  }
}
