package hu.inf.elte.androidtest

import java.net.{HttpURLConnection, URL}

import spray.json.DefaultJsonProtocol._
import spray.json._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object Reddit {
  case class Listing(kind: String, data: ListingData)
  case class ListingData(modhash: String, children: Array[PostThing])
  case class PostThing(kind: String, data: PostData)
  case class PostData(subreddit: String, id: String, author: String,
                      thumbnail: String, url: String, title: String)

  implicit val postDataFormat = jsonFormat(PostData.apply, "subreddit", "id",
                                  "author", "thumbnail", "url", "title")
  implicit val postThingFormat = jsonFormat(PostThing.apply, "kind", "data")
  implicit val listingDataFormat = jsonFormat(ListingData.apply, "modhash", "children")
  implicit val listingFormat = jsonFormat(Listing.apply, "kind", "data")
}

class Reddit {
  import Reddit._

  private[this] val redditUrl = new URL("http://www.reddit.com/")

  private def urlForSubreddit(subreddit: String) =
    new URL(redditUrl, s"/r/$subreddit.json")

  def getSubreddit(subreddit: String) = {
    val url = urlForSubreddit(subreddit)
    Future {
      val connection = url.openConnection.asInstanceOf[HttpURLConnection]
      connection.setRequestMethod("GET")

      val json = scala.io.Source
                  .fromInputStream(connection.getInputStream)
                  .mkString.parseJson
      json.convertTo[Reddit.Listing]
    }
  }

}
