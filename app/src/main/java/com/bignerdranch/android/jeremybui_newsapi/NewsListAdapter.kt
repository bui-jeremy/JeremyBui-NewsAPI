package com.bignerdranch.android.jeremybui_newsapi

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.*
import java.net.URL

class NewsListAdapter(private val articles: List<Article>, private val onClick: (Article) -> Unit) : RecyclerView.Adapter<NewsListAdapter.ArticleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_article_list, parent, false)
        return ArticleViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.bind(articles[position])
    }

    override fun getItemCount(): Int = articles.size

    class ArticleViewHolder(view: View, val onClick: (Article) -> Unit) : RecyclerView.ViewHolder(view) {
        private val title: TextView = view.findViewById(R.id.newsTitle)
        private val description: TextView = view.findViewById(R.id.newsDescription)
        private val image: ImageView = view.findViewById(R.id.newsImage)
        private var currentArticle: Article? = null

        init {
            view.setOnClickListener {
                currentArticle?.let(onClick)
            }
        }

        fun bind(article: Article) {
            currentArticle = article
            title.text = article.title
            description.text = article.description ?: ""
            article.urlToImage?.let { loadImage(it) } ?: image.setImageResource(R.drawable.placeholder) // Set a placeholder if URL is null
        }

        private fun loadImage(url: String) {
            GlobalScope.launch(Dispatchers.IO) {
                try {
                    val inputStream = URL(url).openStream()
                    val bitmap = BitmapFactory.decodeStream(inputStream)
                    withContext(Dispatchers.Main) {
                        image.setImageBitmap(bitmap)
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        image.setImageResource(R.drawable.placeholder) // Set a placeholder on error
                    }
                }
            }
        }
    }
}
