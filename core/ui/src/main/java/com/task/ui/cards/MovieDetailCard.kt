package com.task.ui.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.task.designsystem.constants.Elevations
import com.task.designsystem.constants.Shapes.CARD_SHAPE
import com.task.designsystem.constants.Paddings.LARGE_PADDING
import com.task.designsystem.constants.Paddings.MEDIUM_PADDING
import com.task.designsystem.constants.Paddings.SMALL_PADDING
import com.task.designsystem.constants.Paddings.TINY_PADDING
import com.task.designsystem.constants.Ratio
import com.task.designsystem.theme.MovieAppTheme

@Composable
fun MovieDetailCard(
    posterUrl: String,
    title: String,
    releaseDate: String,
    genres: List<String>,
    overview: String,
    rating: Float,
    voteCount: Int,
    modifier: Modifier = Modifier,
    onCardClick: () -> Unit
) {
    Card(
        shape = CARD_SHAPE,
        elevation = CardDefaults.cardElevation(defaultElevation = Elevations.CARD_ELEVATION),
        modifier = modifier
            .fillMaxWidth()
            .clickable { onCardClick() }
    ) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .padding(LARGE_PADDING)
        ) {
            AsyncImage(
                model = posterUrl,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(Ratio.IMAGE_RATIO)
                    .clip(CARD_SHAPE)
            )

            Spacer(modifier = Modifier.height(MEDIUM_PADDING))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(SMALL_PADDING))

                Text(
                    text = releaseDate,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(SMALL_PADDING))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
                genres.forEach { genre ->
                    Text(
                        text = genre,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .background(
                                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                                shape = CARD_SHAPE
                            )
                            .padding(horizontal = SMALL_PADDING, vertical = TINY_PADDING)
                            .padding(end = SMALL_PADDING)
                    )
                }
            }

            Spacer(modifier = Modifier.height(MEDIUM_PADDING))

            Text(
                text = overview,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 6,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(MEDIUM_PADDING))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                val fullStars = rating.toInt()
                val halfStar = (rating % 1 >= 0.5f)
                repeat(fullStars) {
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                if (halfStar) {
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
                    )
                }

                Spacer(modifier = Modifier.width(SMALL_PADDING))

                Text(
                    text = "($voteCount votes)",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MovieDetailCardPreview() {
    MovieAppTheme {
        MovieDetailCard(
            posterUrl = "https://m.media-amazon.com/images/M/MV5BMDFkYTc0MGEtZmNhMC00ZDI5LWFmNTEtODM1ZTI2ZDJmZjBhXkEyXkFqcGdeQXVyNDYyMDk5MTU@._V1_FMjpg_UX1000_.jpg",
            title = "Scream VI",
            releaseDate = "2023",
            genres = listOf("Horror", "Thriller"),
            overview = "Following the latest Ghostface killings, the four survivors leave Woodsboro behind and start a fresh chapter.",
            rating = 7.374f,
            voteCount = 684,
            onCardClick = {}
        )
    }
}