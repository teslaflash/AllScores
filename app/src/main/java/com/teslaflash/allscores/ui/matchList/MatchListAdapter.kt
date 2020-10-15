package com.teslaflash.allscores.ui.matchList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chauthai.swipereveallayout.ViewBinderHelper
import com.teslaflash.allscores.R
import com.teslaflash.allscores.database.Scores
import com.teslaflash.allscores.database.ScoresMatch
import kotlinx.android.synthetic.main.itemview_match.view.*

class MatchListAdapter : ListAdapter<ScoresMatch, MatchListAdapter.ScoresViewHolder>(diffCallback) {

    open interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setDeleteListener(listener: OnItemClickListener?) {
        mListenerDeletor = listener
    }

    fun setOnClickerListener(listener: OnItemClickListener?){
        mListenerClicker = listener
    }

    private var viewBinderHelper: ViewBinderHelper = ViewBinderHelper()

    private var mListenerDeletor: OnItemClickListener? = null
    private var mListenerClicker: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoresViewHolder =
        ScoresViewHolder(parent,listenerDeletor = mListenerDeletor, listenerClicker = mListenerClicker)

        companion object {

            private val diffCallback = object : DiffUtil.ItemCallback<ScoresMatch>() {
                override fun areItemsTheSame(oldItem: ScoresMatch, newItem: ScoresMatch): Boolean =
                    oldItem.id == newItem.id

                override fun areContentsTheSame(oldItem: ScoresMatch, newItem: ScoresMatch): Boolean =
                    oldItem == newItem
            }
        }

    override fun onBindViewHolder(holder: ScoresViewHolder, position: Int) {
        viewBinderHelper.bind(holder.swiper,getItem(position).id.toString())
        holder.bindTo(getItem(position))
    }



    inner class ScoresViewHolder(parent: ViewGroup, listenerDeletor: OnItemClickListener?, listenerClicker: OnItemClickListener?) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.itemview_match, parent, false)) {

        private val deletor = itemView.deletor
        private val title = itemView.title
        private val date = itemView.date
        private val price = itemView.price
        private val lastTransaction = itemView.lastTransaction
        private val frontLayout = itemView.front_layout
        val swiper = itemView.swiper!!
        var scoresMatch: ScoresMatch? = null


        init {
            deletor.setOnClickListener { listenerDeletor?.let { if (adapterPosition != RecyclerView.NO_POSITION) it.onItemClick(adapterPosition) } }
        }

        init {
            frontLayout.setOnClickListener {listenerClicker?.let { if (adapterPosition != RecyclerView.NO_POSITION) it.onItemClick(adapterPosition) }}
        }

        fun bindTo(item: ScoresMatch?) {

            this.scoresMatch = item
            title.text = item?.title.toString()
            date.text = item?.date.toString()
            price.text = item?.price.toString()

        }
    }

}