package hr.foi.rampu.snackalchemist.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hr.foi.rampu.snackalchemist.R
import hr.foi.rampu.snackalchemist.dc.Article


class ArticlesAdapter (
        private val articlesList: List<Article>
    ) : RecyclerView.Adapter<ArticlesAdapter.ArticleViewHolder>() {

        inner class ArticleViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            private var title: TextView = view.findViewById(R.id.tv_articles_list_item_title)
            private var text: TextView = view.findViewById(R.id.tv_articles_list_item_text)
            private var dateTime: TextView = view.findViewById(R.id.tv_articles_list_item_date)
            private var imageView: ImageView = view.findViewById(R.id.iv_articles_list_item_image)


            fun bind(article: Article) {
                title.text = article.title
                text.text = article.text
                dateTime.text = article.date
                imageView.setImageResource(article.imageResId)

            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.articles_list_item, parent, false)
            return ArticleViewHolder(view)
        }

        override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
            holder.bind(articlesList[position])
        }

        override fun getItemCount() = articlesList.size
    }


