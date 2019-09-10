package me.mfathy.task.factory

import me.mfathy.task.domain.model.Genre
import java.util.concurrent.ThreadLocalRandom

/**
 * Created by Mohammed Fathy.
 * dev.mfathy@gmail.com
 *
 * Dummy Data Factory.
 */
object DataFactory {
    fun randomInt(): Int = ThreadLocalRandom.current().nextInt(0, 5)

    fun randomDouble(): Double = ThreadLocalRandom.current().nextDouble(0.0, 5.0)

    fun randomString(): String = java.util.UUID.randomUUID().toString()

    fun randomBoolean(): Boolean = ThreadLocalRandom.current().nextBoolean()

    fun makeGenres(): List<Genre> = arrayOf(
        "Action",
        "Adventure",
        "Animation",
        "Comedy",
        "Crime",
        "Documentary",
        "Drama",
        "Family",
        "Fantasy",
        "History",
        "Horror",
        "Music",
        "Mystery",
        "Romance",
        "Science",
        "Fiction",
        "Science Fiction",
        "TV Movie",
        "Thriller",
        "War",
        "Western"
    ).map {
        Genre(it, 0)
    }
}