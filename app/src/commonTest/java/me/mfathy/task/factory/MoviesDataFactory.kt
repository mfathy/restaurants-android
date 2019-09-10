package me.mfathy.task.factory

import me.mfathy.task.data.model.GenreEntity
import me.mfathy.task.data.model.MovieEntity
import me.mfathy.task.data.model.PosterEntity
import me.mfathy.task.interactors.restaurants.GetRestaurants
import me.mfathy.task.domain.model.Genre
import me.mfathy.task.domain.model.Movie
import me.mfathy.task.domain.model.Poster

/**
 * Created by Mohammed Fathy.
 * dev.mfathy@gmail.com
 *
 */
object MoviesDataFactory {

    fun makeGenre(): Genre = Genre(
        "Action",
        DataFactory.randomInt()
    )

    fun makeGenreEntity(): GenreEntity =
        GenreEntity(
            DataFactory.randomString(),
            DataFactory.randomInt()
        )

    fun makePoster(): Poster = Poster(
        DataFactory.randomString(),
        DataFactory.randomString()
    )

    fun makePosterEntity(): PosterEntity =
        PosterEntity(
            DataFactory.randomString(),
            DataFactory.randomString()
        )

    fun makeMovie(): Movie = Movie(
        rating = DataFactory.randomDouble(),
        id = DataFactory.randomInt(),
        ratingCount = DataFactory.randomInt(),
        title = DataFactory.randomString(),
        genres = listOf(makeGenre()),
        poster = makePoster()
    )

    fun makeMovieEntity(): MovieEntity =
        MovieEntity(
            rating = DataFactory.randomDouble(),
            id = DataFactory.randomInt(),
            ratingCount = DataFactory.randomInt(),
            title = DataFactory.randomString(),
            genres = listOf(makeGenreEntity()),
            poster = makePosterEntity()
        )

    fun makeGetMoviesParams(): GetRestaurants.Params {
        return GetRestaurants.Params(
            DataFactory.randomString(),
            DataFactory.randomInt(),
            DataFactory.randomInt()
        )
    }
}