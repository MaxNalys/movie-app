package com.task.movieapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.task.data.repository.MovieRepository
import dagger.hilt.android.AndroidEntryPoint
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var movieRepository: MovieRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Greeting("Android")
        }

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val trending = movieRepository.getTrendingMovies(page = 1)
                Log.d("API_TEST", "Trending movies: ${trending.size} items")
                trending.forEach {
                    Log.d("API_TEST", "Movie: ${it.title}, genres: ${it.genreIds}")
                }

                val popular = movieRepository.getPopularMovies(page = 1)
                Log.d("API_TEST", "Popular movies: ${popular.size} items")

                val genres = movieRepository.getGenres()
                Log.d("API_TEST", "Genres: ${genres.map { it.name }}")
            } catch (e: Exception) {
                Log.e("API_TEST", "Error fetching API: ${e.message}")
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview
@Composable
fun GreetingPreview() {
    Greeting("Android")
}