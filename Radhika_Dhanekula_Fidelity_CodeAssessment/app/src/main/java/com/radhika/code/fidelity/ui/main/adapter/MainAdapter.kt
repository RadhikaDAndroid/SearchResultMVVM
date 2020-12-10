package com.radhika.code.fidelity.ui.main.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.radhika.code.fidelity.R
import com.radhika.code.fidelity.data.model.SearchResult
import kotlinx.android.synthetic.main.item_layout.view.*


class MainAdapter(
    private val searchResults: ArrayList<SearchResult>
) : RecyclerView.Adapter<MainAdapter.DataViewHolder>() {

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(user: SearchResult) {
            itemView.textViewtitle.text = user.title
            itemView.textViewEpisodes.text = "Episodes:  ".plus(user.episodes.toString())
            itemView.texturllink.text = itemView.context.getString(R.string.moredetails)
            itemView.textviewsynopsis.text = user.synopsis
            itemView.textviewrating.text = "Rating:  ".plus(user.rated.toString())
            itemView.texturllink.setOnClickListener {
                showOutofAppDialog(user.url)
            }
            Glide.with(itemView.imageViewAvatar.context)
                .load(user.imageUrl)
                .into(itemView.imageViewAvatar)
        }

        private fun showOutofAppDialog(url: String?) {
            val outofAppAlertDialog = AlertDialog.Builder(itemView.context)
                .setMessage(R.string.outofappmessage)
                .setPositiveButton(R.string.yes) { dialog, _ ->
                    loadurlInWebviewBrowser(url)
                    dialog.dismiss()
                }
                .setNegativeButton(R.string.no) { dialog, _ ->
                    dialog.dismiss()
                }
            outofAppAlertDialog.show()
        }

        private fun loadurlInWebviewBrowser(url: String?) {
            var weburl = url
            weburl?.let {
                if (!it.startsWith("https://") && !it.startsWith("http://"))
                    weburl = "http://$url"
            }
            val intent = createIntentForBrowser(Uri.parse(weburl))
            intent?.let { itemView.context.startActivity(intent) }
        }

        private fun createIntentForBrowser(uri: Uri): Intent? {
            val intent = Intent()
            intent.action = Intent.ACTION_VIEW
            intent.data = uri
            intent.resolveActivity(itemView.context.packageManager)?.let { return intent }
                ?: return null
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_layout, parent,
                false
            )
        )

    override fun getItemCount(): Int = searchResults.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(searchResults[position])

    fun addData(list: List<SearchResult>) {
        searchResults.clear()
        searchResults.addAll(list)
    }

}