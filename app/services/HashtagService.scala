package services

import twitter4j.{ResponseList, Status}

import scala.collection.JavaConversions._
import javax.inject.Singleton

trait HashtagService {
  def extractTop10HashtagsFromTweets(userTweets: ResponseList[Status]): Seq[String]
}

@Singleton
class HashtagServiceImpl extends HashtagService {
  override def extractTop10HashtagsFromTweets(userTweets: ResponseList[Status]): Seq[String] = {
    val pattern = "#[a-zA-Z0-9]*".r // matches the '#' sign followed by whatever text

    return userTweets
      .flatMap(s => pattern.findAllIn(s.getText))
      .groupBy(_.toString().toLowerCase)
      .keys
      .take(10)
      .toList
  }
}
