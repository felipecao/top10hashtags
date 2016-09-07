package services

import twitter4j.{ResponseList, Status}

import scala.collection.JavaConversions._
import javax.inject.Singleton

trait HashtagRanker {
  def extractTop10HashtagsFromTweets(userTweets: Seq[Status]): Seq[String]
}

@Singleton
class HashtagRankerImpl extends HashtagRanker {
  override def extractTop10HashtagsFromTweets(userTweets: Seq[Status]): Seq[String] = {
    val pattern = "#[a-zA-Z0-9]*".r // matches the '#' sign followed by whatever text

    return userTweets
      .flatMap(s => pattern.findAllIn(s.getText))
      .groupBy(_.toString())
      .keys
      .take(10)
      .toList
  }
}
