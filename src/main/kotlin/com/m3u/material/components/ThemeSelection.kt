package com.m3u.material.components

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.DarkMode
import androidx.compose.material.icons.rounded.LightMode
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlurEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.m3u.material.model.LocalSpacing
import com.m3u.material.model.LocalTheme
import com.m3u.material.model.SugarColors
import com.m3u.material.model.Theme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ThemeSelection(
    theme: Theme,
    selected: Boolean,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
    modifier: Modifier = Modifier,
    leftContentDescription: String,
    rightContentDescription: String,
) {
    val spacing = LocalSpacing.current
    val alpha by animateFloatAsState(
        if (selected) 0f else 0.4f,
        label = "alpha"
    )
    val elevation by animateIntAsState(
        if (selected) 16 else 0,
        label = "elevation"
    )

    val zoom by animateFloatAsState(
        if (selected) 1f else 0.8f,
        label = "zoom"
    )

    val blurRadius by animateFloatAsState(
        if (selected) 0f else 16f,
        label = "blurRadius"
    )


    val feedback = LocalHapticFeedback.current
    Box(
        contentAlignment = Alignment.Center
    ) {
        OutlinedCard(
            colors = CardDefaults.outlinedCardColors(
                containerColor = theme.background,
                contentColor = theme.onBackground
            ),
            elevation = CardDefaults.outlinedCardElevation(
                defaultElevation = elevation.dp
            ),
            modifier = modifier
                .graphicsLayer {
                    scaleX = zoom
                    scaleY = zoom
                }
                .size(96.dp)
                .padding(spacing.extraSmall)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(spacing.small),
                modifier = Modifier
                    .combinedClickable(
                        onClick = {
                            if (selected) return@combinedClickable
                            feedback.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                            onClick()
                        },
                        onLongClick = {
                            feedback.performHapticFeedback(HapticFeedbackType.LongPress)
                            onLongClick()
                        }
                    )
                    .graphicsLayer {
                        if (blurRadius != 0f) renderEffect = BlurEffect(blurRadius, blurRadius)
                    }
                    .drawWithContent {
                        drawContent()
                        drawRect(
                            color = Color.Black.copy(
                                alpha = alpha
                            )
                        )
                    }
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(spacing.small)
                ) {
                    ColorPiece(
                        containerColor = theme.primary,
                        contentColor = theme.onPrimary,
                        left = true,
                        contentDescription = leftContentDescription
                    )
                    ColorPiece(
                        containerColor = theme.tint,
                        contentColor = theme.onTint,
                        left = false,
                        contentDescription = rightContentDescription
                    )
                }
                Text(
                    text = theme.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = theme.onPrimary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(theme.primary)
                )
            }
        }

        Crossfade(selected, label = "icon") { selected ->
            if (!selected) {
                Icon(
                    imageVector = when (theme.isDark) {
                        true -> Icons.Rounded.DarkMode
                        false -> Icons.Rounded.LightMode
                    },
                    contentDescription = "",
                    tint = when (theme.isDark) {
                        true -> SugarColors.Tee
                        false -> SugarColors.Yellow
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThemeAddSelection(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val theme = LocalTheme.current
    Box(
        contentAlignment = Alignment.Center
    ) {
        OutlinedCard(
            colors = CardDefaults.outlinedCardColors(
                containerColor = theme.surface,
                contentColor = theme.onSurface
            ),
            elevation = CardDefaults.outlinedCardElevation(
                defaultElevation = LocalSpacing.current.none
            ),
            modifier = modifier
                .graphicsLayer {
                    scaleX = 0.8f
                    scaleY = 0.8f
                }
                .aspectRatio(1f)
                .padding(LocalSpacing.current.extraSmall),
            onClick = onClick,
            content = {}
        )

        Icon(
            imageVector = Icons.Rounded.Add,
            contentDescription = "",
            tint = theme.onSurface
        )
    }
}

@Composable
private fun ColorPiece(
    containerColor: Color,
    contentColor: Color,
    left: Boolean,
    contentDescription: String
) {
    val spacing = LocalSpacing.current
    ElevatedCard(
        colors = CardDefaults.elevatedCardColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        shape = RoundedCornerShape(
            topStart = spacing.small,
            topEnd = spacing.small,
            bottomStart = if (left) spacing.none else spacing.small,
            bottomEnd = if (!left) spacing.none else spacing.small
        ),
        modifier = Modifier
            .aspectRatio(1f)
            .padding(spacing.extraSmall)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = contentDescription,
                style = MaterialTheme.typography.bodyLarge
                    .copy(
                        fontSize = if (left) 16.sp
                        else 12.sp
                    ),
                color = contentColor
            )
        }
    }
}