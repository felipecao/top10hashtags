package posos

case class Tweet (text: String) {

  def hashtags: Seq[String] = {
    return text.split("#[a-zA-Z0-9]*")
  }
}
