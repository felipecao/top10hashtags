package services

import javax.inject.{Inject, Singleton}

trait TwitterService {
  def top10Hashtags(handle: String, limit: Int): Seq[String]
}

@Singleton
class TwitterServiceImpl @Inject() (twitterConnection: TwitterConnectionImpl, hashtagRanker: HashtagRankerImpl) extends TwitterService {
  override def top10Hashtags(handle: String, limit: Int): Seq[String] = {
    val userTweets = twitterConnection.retrieveTweets(handle, limit)

    return hashtagRanker.extractTop10HashtagsFromTweets(userTweets)
  }
}
