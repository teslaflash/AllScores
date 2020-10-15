package com.teslaflash.allscores.ui.playerList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chauthai.swipereveallayout.ViewBinderHelper
import com.teslaflash.allscores.R
import com.teslaflash.allscores.database.ScoresPlayer
import kotlinx.android.synthetic.main.itemview_match.view.*
import kotlinx.android.synthetic.main.itemview_match.view.deletor
import kotlinx.android.synthetic.main.itemview_match.view.front_layout
import kotlinx.android.synthetic.main.itemview_match.view.lastTransaction
import kotlinx.android.synthetic.main.itemview_match.view.swiper
import kotlinx.android.synthetic.main.itemview_player.view.*

class PlayerListAdapter : ListAdapter<ScoresPlayer, PlayerListAdapter.ScoresViewHolder>(diffCallback) {

    open interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setDeleteListener(listener: OnItemClickListener?) {
        mListenerDeletor = listener
    }

    fun setOnClickerListener(listener: OnItemClickListener?){
        mListenerClicker = listener
    }

    fun setOnIncreaseListener(listener: OnItemClickListener){
        mListenerIncrease = listener
    }

    fun setOnDecreaseListener(listener: OnItemClickListener){
        mListenerDecrease = listener
    }

    private var viewBinderHelper: ViewBinderHelper = ViewBinderHelper()

    private var mListenerDeletor: OnItemClickListener? = null
    private var mListenerClicker: OnItemClickListener? = null
    private var mListenerIncrease: OnItemClickListener? = null
    private var mListenerDecrease: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoresViewHolder =
        ScoresViewHolder(parent,listenerDeletor = mListenerDeletor, listenerClicker = mListenerClicker, listenerIncreaser = mListenerIncrease, listenerDecreaser = mListenerDecrease)

        companion object {

            private val diffCallback = object : DiffUtil.ItemCallback<ScoresPlayer>() {
                override fun areItemsTheSame(oldItem: ScoresPlayer, newItem: ScoresPlayer): Boolean =
                    oldItem.id == newItem.id

                override fun areContentsTheSame(oldItem: ScoresPlayer, newItem: ScoresPlayer): Boolean =
                    oldItem == newItem
            }
        }

    override fun onBindViewHolder(holder: ScoresViewHolder, position: Int) {
        viewBinderHelper.bind(holder.swiper,getItem(position).id.toString())
        holder.bindTo(getItem(position))
    }



    inner class ScoresViewHolder(parent: ViewGroup, listenerDeletor: OnItemClickListener?, listenerClicker: OnItemClickListener?,
                                 listenerIncreaser: OnItemClickListener?, listenerDecreaser: OnItemClickListener?) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.itemview_player, parent, false)) {

        private val deletor = itemView.deletor
        private val name = itemView.name
        private val balance = itemView.balance
        private val carma = itemView.carma
        private val lastTransaction = itemView.lastTransaction
        private val frontLayout = itemView.front_layout
        private val increase = itemView.plusBT
        private val decrease = itemView.minusBT

        val swiper = itemView.swiper!!
        var scoresPlayer: ScoresPlayer? = null


        init {
            deletor.setOnClickListener { listenerDeletor?.let { if (adapterPosition != RecyclerView.NO_POSITION) it.onItemClick(adapterPosition) } }
        }

        init {
            frontLayout.setOnClickListener {listenerClicker?.let { if (adapterPosition != RecyclerView.NO_POSITION) it.onItemClick(adapterPosition) }}
        }

        init{
            increase.setOnClickListener {listenerIncreaser?.let { if (adapterPosition != RecyclerView.NO_POSITION) it.onItemClick(adapterPosition) }}
        }

        init{
            decrease.setOnClickListener {listenerDecreaser?.let { if (adapterPosition != RecyclerView.NO_POSITION) it.onItemClick(adapterPosition) }}
        }

        fun bindTo(item: ScoresPlayer?) {

            this.scoresPlayer = item
            name.text = item?.name.toString()
            balance.text = item?.balance.toString()
            carma.text = item?.fuckUps.toString()

        }
    }

}