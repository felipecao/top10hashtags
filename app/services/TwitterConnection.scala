package services

import javax.inject.Singleton

import twitter4j.conf.ConfigurationBuilder
import twitter4j.{Paging, ResponseList, Status, TwitterFactory}
import play.api.Logger

trait TwitterConnection {
  val MaxTweets = 2000
  val FirstPage = 1
  def retrieveTweets(handle: String, limit: Int): ResponseList[Status]
}

@Singleton
class TwitterConnectionImpl extends TwitterConnection{

  def retrieveTweets(handle: String, total: Int): ResponseList[Status] = {
    val cb = new ConfigurationBuilder()

    Logger.info("System properties: " + sys.props)
    Logger.info("Env properties: " + sys.env)

    cb.setDebugEnabled(true)
      .setOAuthConsumerKey(sys.props("consumerKey"))
      .setOAuthConsumerSecret(sys.props("consumerSecret"))
      .setOAuthAccessToken(sys.props("accessToken"))
      .setOAuthAccessTokenSecret(sys.props("accessTokenSecret"))

    val tf = new TwitterFactory(cb.build())
    val twitter = tf.getInstance()
    val limit = math.min(total, MaxTweets)

    return twitter.getUserTimeline(handle, new Paging(FirstPage, limit))
  }
}
