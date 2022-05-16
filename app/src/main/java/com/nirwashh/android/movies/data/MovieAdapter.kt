package com.nirwashh.android.movies.data

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nirwashh.android.movies.databinding.MovieItemBinding
import com.nirwashh.android.movies.model.Movie
import com.squareup.picasso.Picasso

class MovieAdapter(listMain: ArrayList<Movie>, contextMAct: Context) : RecyclerView.Adapter<MovieAdapter.MovieHolder>() {
    var movies = listMain
    var context = contextMAct
    inner class MovieHolder(val b: MovieItemBinding, contextHolder: Context) :
        RecyclerView.ViewHolder(b.root) {
        val context = contextHolder
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieAdapter.MovieHolder {
        val binding = MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieHolder(binding, context)
    }

    override fun onBindViewHolder(holder: MovieAdapter.MovieHolder, position: Int) {
        with(holder) {
            with(movies[position]) {
                b.tvTitle.text = title
                b.tvYear.text = year
                Picasso.get().load(posterUrl).fit().centerCrop().into(b.imgPoster)
            }
        }


    }

    override fun getItemCount() = movies.size

    fun updateAdapter(newMovie: ArrayList<Movie>) {
        movies.clear()
        movies.addAll(newMovie)
        notifyDataSetChanged()
    }


}