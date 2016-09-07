package controllers

import javax.inject.Inject

import play.api.libs.json.Json
import play.api.mvc._
import services.{TwitterServiceImpl, TwitterConnectionImpl}

class TwitterController @Inject() (twitterService: TwitterServiceImpl) extends Controller{

  def read(handle: String, limit: Int) = Action { implicit request =>
    val hashtags = twitterService.top10Hashtags(handle, limit)
    Ok(Json.toJson(hashtags))
  }
}
