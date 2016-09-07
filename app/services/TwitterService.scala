package services

import javax.inject.{Inject, Singleton}

trait TwitterService {
  def top10Hashtags(handle: String, limit: Int): Seq[String]
}

@Singleton
class TwitterServiceImpl @Inject() (twitterConnection: TwitterConnectionImpl, hashtagService: HashtagServiceImpl) extends TwitterService {
  override def top10Hashtags(handle: String, limit: Int): Seq[String] = {
    val userTweets = twitterConnection.retrieveTweets(handle, limit)

    return hashtagService.extractTop10HashtagsFromTweets(userTweets)
  }
}
