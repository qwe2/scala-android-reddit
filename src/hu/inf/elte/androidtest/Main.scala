package hu.inf.elte.androidtest

import android.content.{Context, Intent}
import android.net.Uri
import android.view.{ViewGroup, View}
import android.widget.{ListView, ArrayAdapter}
import org.scaloid.common._

import scala.collection.mutable.ArrayBuffer
import scala.collection.JavaConverters._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}
import scala.language.postfixOps

class Main extends SActivity {
  implicit val tag = LoggerTag("reddit-app")
  lazy val reddit = new Reddit()

  val posts = new ArrayBuffer[Reddit.PostData]()
  lazy val postAdapter = new RedditAdapter(posts)

  private def setSubreddit(subreddit: String) = {
    posts.clear()

    reddit.getSubreddit(subreddit) onComplete {
      case Success(res) =>
        runOnUiThread { () =>
          val data = res.data.children map (_.data)
          posts.append(data :_*)
          postAdapter.notifyDataSetChanged()
        }
      case Failure(ex) =>
        error("Error while fetching data", ex)
    }
  }

  onCreate {
    contentView = new SVerticalLayout {
      style {
        case e: SEditText => e padding  5.dip
        case t: STextView => t textSize 5.sp
        case b: SButton   => b padding  2.dip
      }

      val edit = SEditText()
      SButton("Go!") onClick setSubreddit(edit.getText.toString)

      SListView() adapter postAdapter
    } padding 20.dip
  }
}

