package hu.inf.elte.androidtest

import java.util

import android.content.Intent
import android.net.Uri
import android.widget.{ListView, ArrayAdapter}
import org.scaloid.common._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

class Main extends SActivity {
  implicit val tag = LoggerTag("reddit-app")
  lazy val reddit = new Reddit()

  val posts = new util.ArrayList[String]()
  lazy val postAdapter = new ArrayAdapter(ctx, android.R.layout.simple_list_item_1, posts)

  private def setSubreddit(subreddit: String) = {
    posts.clear()

    reddit.getSubreddit(subreddit) onComplete {
      case Success(res) =>
        runOnUiThread { () =>
          res.data.children map (_.data.title) foreach posts.add
          postAdapter.notifyDataSetChanged()
        }
      case Failure(ex) =>
        ex.printStackTrace()
    }
  }

  onCreate {
    contentView = new SVerticalLayout {
      style {
        case e: SEditText => e padding 5.dip
        case t: STextView => t textSize 5.sp
        case b: SButton => b padding 2.dip
      }

      val edit = SEditText()
      SButton("Go!") onClick setSubreddit(edit.getText.toString)

      SListView() adapter postAdapter
    } padding 20.dip
  }
}

