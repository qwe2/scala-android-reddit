package hu.inf.elte.androidtest

import android.content.{Context, Intent}
import android.net.Uri
import android.view.{ViewGroup, View}
import android.widget.ArrayAdapter
import org.scaloid.common.STextView

import scala.collection.mutable.ArrayBuffer
import scala.collection.JavaConverters._

class RedditAdapter(posts: ArrayBuffer[Reddit.PostData])(implicit ctx: Context)
  extends ArrayAdapter[Reddit.PostData](ctx, 0, posts.asJava) {

  override def getView(position: Int, convertView: View, parent: ViewGroup): View = {
    implicit val ctx = parent.getContext
    val item = posts(position)
    new STextView(item.title) onClick {
      val browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(item.url))
      ctx.startActivity(browserIntent)
    }
  }
}
