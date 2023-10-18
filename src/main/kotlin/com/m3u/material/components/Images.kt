package com.m3u.material.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import coil.compose.SubcomposeAsyncImage
import com.m3u.material.model.LocalScalable
import com.m3u.material.model.LocalSpacing
import com.m3u.material.model.LocalTheme

@Composable
fun Image(
    model: Any?,
    modifier: Modifier = Modifier,
    errorPlaceholder: String? = null,
    shape: Shape = RectangleShape,
    contentDescription: String? = null,
    contentScale: ContentScale = ContentScale.Fit,
) {
    val scalable = LocalScalable.current
    val spacing = with(scalable) {
        LocalSpacing.current.scaled
    }
    SubcomposeAsyncImage(
        model = model,
        contentDescription = contentDescription,
        modifier = modifier.clip(shape),
        contentScale = contentScale,
        loading = {
            Surface(
                modifier = Modifier.fillMaxSize(),
                shape = shape,
                color = LocalTheme.current.secondary
            ) {

            }
        },
        error = {
            Box(
                modifier = Modifier
                    .clip(shape)
                    .background(LocalTheme.current.secondary)
                    .padding(spacing.small),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = errorPlaceholder.orEmpty(),
                    style = MaterialTheme.typography.h5,
                    fontSize = with(scalable) {
                        MaterialTheme.typography.h5.fontSize.scaled
                    }
                )
            }
        }
    )
}