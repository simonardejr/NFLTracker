package com.example.aula1.view.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.aula1.R
import com.example.aula1.databinding.GameItemBinding
import com.example.aula1.model.Games
import com.example.aula1.view.GameDetailActivity
import com.google.android.gms.common.internal.service.Common
import kotlin.collections.ArrayList


class GameAdapter(private val items: ArrayList<Games>) : RecyclerView.Adapter<GameAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val itemBinding = GameItemBinding.bind(view)
        private val viewContext = view

        fun bind(game: Games) {
            val homeTeamImg = viewContext.context.resources.getIdentifier(game.homeTeamAbbreviation.lowercase(), "drawable", "com.example.aula1")
            val awayTeamImg = viewContext.context.resources.getIdentifier(game.awayTeamAbbreviation.lowercase(), "drawable", "com.example.aula1")

            itemBinding.homeTeamTextView.text = game.homeTeamNickName
            itemBinding.homeTeamScoreTextView.text = game.homeTeamScore.toString().padStart(2, '0')
            itemBinding.awayTeamTextView.text = game.awayTeamNickName
            itemBinding.awayTeamScoreTextView.text = game.awayTeamScore.toString().padStart(2, '0')
            itemBinding.homeTeamImg.setImageResource(homeTeamImg)
            itemBinding.awayTeamImg.setImageResource(awayTeamImg)
            itemBinding.dateTextView.text = game.dateBR

//            val sharedPreferences = viewContext.context.getSharedPreferences("com.example.aula1.PREFS", MODE_PRIVATE)
//            val userTeam = sharedPreferences.getString("userTeam", null)
//            if(game.homeTeamAbbreviation == userTeam || game.awayTeamAbbreviation == userTeam) {
//                itemBinding.cardView.setCardBackgroundColor(Color.rgb(220,220,220))
//            } else {
//                itemBinding.cardView.setCardBackgroundColor(Color.WHITE)
//            }

            val oldColor = itemBinding.versus.currentTextColor

            when {
                game.homeTeamScore > game.awayTeamScore -> {
                    itemBinding.homeTeamTextView.setTypeface(null, Typeface.BOLD)
                    // itemBinding.homeTeamTextView.setTextColor(Color.rgb(102,153,0))
                    itemBinding.homeTeamScoreTextView.setTypeface(null, Typeface.BOLD)
                    itemBinding.homeTeamScoreTextView.setTextColor(Color.rgb(102,153,0))

                    itemBinding.awayTeamTextView.setTypeface(null, Typeface.NORMAL)
                    // itemBinding.awayTeamTextView.setTextColor(oldColor)
                    itemBinding.awayTeamScoreTextView.setTypeface(null, Typeface.NORMAL)
                    itemBinding.awayTeamScoreTextView.setTextColor(oldColor)
                }
                game.homeTeamScore < game.awayTeamScore -> {
                    itemBinding.homeTeamTextView.setTypeface(null, Typeface.NORMAL)
                    // itemBinding.homeTeamTextView.setTextColor(oldColor)
                    itemBinding.homeTeamScoreTextView.setTypeface(null, Typeface.NORMAL)
                    itemBinding.homeTeamScoreTextView.setTextColor(oldColor)

                    itemBinding.awayTeamTextView.setTypeface(null, Typeface.BOLD)
                    // itemBinding.awayTeamTextView.setTextColor(Color.rgb(102,153,0))
                    itemBinding.awayTeamScoreTextView.setTypeface(null, Typeface.BOLD)
                    itemBinding.awayTeamScoreTextView.setTextColor(Color.rgb(102,153,0))
                }
                else -> {
                    itemBinding.homeTeamScoreTextView.setTypeface(null, Typeface.NORMAL)
                    itemBinding.homeTeamScoreTextView.setTextColor(oldColor)

                    itemBinding.awayTeamScoreTextView.setTypeface(null, Typeface.NORMAL)
                    itemBinding.awayTeamScoreTextView.setTextColor(oldColor)
                }
            }
        }
    }

    fun update(games: List<Games>) {
        items.clear()
        items.addAll(games)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.game_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])

        val gameId = items[position].gameId
        val context = holder.itemView.context
        var winner = ""
        var winnerAbbreviation = ""
        when {
            items[position].homeTeamScore > items[position].awayTeamScore -> {
                winner = items[position].homeTeamNickName
                winnerAbbreviation = items[position].homeTeamAbbreviation
            }
            items[position].homeTeamScore < items[position].awayTeamScore -> {
                winner = items[position].awayTeamNickName
                winnerAbbreviation = items[position].awayTeamAbbreviation
            }
            else -> {
                winner = "jogo empatado."
            }
        }

        holder.itemView.setOnClickListener {
            val intent = Intent(context, GameDetailActivity::class.java)

            intent.putExtra("gameId", gameId)
            intent.putExtra("winner", winner)
            intent.putExtra("winnerAbbreviation", winnerAbbreviation)
            context.startActivity(intent)
        }


    }

    override fun getItemCount(): Int = items.size



}