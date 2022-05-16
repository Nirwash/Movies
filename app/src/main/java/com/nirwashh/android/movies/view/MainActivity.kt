package com.nirwashh.android.movies.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.nirwashh.android.movies.data.MovieAdapter
import com.nirwashh.android.movies.databinding.ActivityMainBinding
import com.nirwashh.android.movies.model.Movie
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    private lateinit var b: ActivityMainBinding
    private lateinit var adapter: MovieAdapter
    private lateinit var movies: ArrayList<Movie>
    private lateinit var requestQueue: RequestQueue
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityMainBinding.inflate(layoutInflater)
        setContentView(b.root)

        b.recyclerView.apply {
            hasFixedSize()
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = adapter
        }
        movies = ArrayList()

        b.btnSearch.setOnClickListener {
            if (b.edSearch.text.isNullOrEmpty()) {
                getMovies("batman")
                adapter = MovieAdapter(movies, this@MainActivity)
                b.recyclerView.adapter = adapter
            } else {
                getMovies(b.edSearch.text.toString())
                adapter.updateAdapter(movies)
                b.recyclerView.adapter = adapter
            }
        }


    }

    private fun getMovies(searchText: String) {
        val url = "https://www.omdbapi.com/?apikey=b1540569&s=$searchText"
        requestQueue = Volley.newRequestQueue(this)
        val request = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->
                val jsonArray = response?.getJSONArray("Search")
                if (jsonArray != null) {
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val title = jsonObject.getString("Title")
                        val year = jsonObject.getString("Year")
                        val posterUrl = jsonObject.getString("Poster")
                        val movie = Movie(title, posterUrl, year)
                        movies.add(movie)
                    }
                }
            }) { error -> error?.printStackTrace() }
        requestQueue.add(request)
    }
}