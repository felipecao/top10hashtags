package services

import javax.inject.Singleton

import twitter4j.conf.ConfigurationBuilder
import twitter4j._
import play.api.Logger
import collection.JavaConverters._
import scala.collection.mutable.ListBuffer

import scala.util.Properties

trait TwitterConnection {
  val MaxTweets = 2000
  val FirstPage = 1
  def retrieveTweets(handle: String, limit: Int): Seq[Status]
}

@Singleton
class TwitterConnectionImpl extends TwitterConnection{

  def retrieveTweets(handle: String, total: Int): Seq[Status] = {
    val limit = math.min(total, MaxTweets)
    var page = FirstPage
    val tweets = ListBuffer.empty[Status]

    tweets.++=(fetchTweets(handle, page, limit))

    while(tweets.size < limit) {
      page += 1
      tweets.++=(fetchTweets(handle, page, limit))
    }

    return tweets
  }

  private def fetchTweets(handle: String, page: Int, limit: Int): Seq[Status] = {
    val cb = new ConfigurationBuilder()

    cb.setDebugEnabled(true)
      .setOAuthConsumerKey(Properties.envOrElse("consumerKey", sys.props("consumerKey")))
      .setOAuthConsumerSecret(Properties.envOrElse("consumerSecret", sys.props("consumerSecret")))
      .setOAuthAccessToken(Properties.envOrElse("accessToken", sys.props("accessToken")))
      .setOAuthAccessTokenSecret(Properties.envOrElse("accessTokenSecret", sys.props("accessTokenSecret")))

    val tf = new TwitterFactory(cb.build())
    val twitter = tf.getInstance()

    return twitter.getUserTimeline(handle, new Paging(page, limit)).asScala
  }
}
