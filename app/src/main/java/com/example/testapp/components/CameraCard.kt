package com.example.testapp.components

import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.testapp.R
import com.example.testapp.models.Camera
import com.example.testapp.ui.theme.starIconColor
import com.skydoves.landscapist.glide.GlideImage
import kotlin.math.roundToInt


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CameraCard(camera: Camera) {

    val sizePx = with(LocalDensity.current) { 80.dp.toPx() } //отвечает за то насколько будет сдвиг свайпа
    val anchors = mapOf(0f to 0, -sizePx to 1)
    val swipeableState = rememberSwipeableState(0)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 5.dp, end = 5.dp)
            .swipeable(
                state = swipeableState,
                anchors = anchors,
                thresholds = { _, _ -> FractionalThreshold(0.3f) },
                orientation = Orientation.Horizontal
            ))
    {

        Row {

        }
        IconButton(
            onClick = { },
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 20.dp)
                .border(1.dp, color = Color.Gray.copy(0.5f), shape = CircleShape))
        {
            Icon(painter = painterResource(id = R.drawable.star_icon), contentDescription = "", tint = starIconColor)
        }

        Card(
            shape = RoundedCornerShape(5),
            modifier = Modifier
                .padding(5.dp)
                .offset { IntOffset(swipeableState.offset.value.roundToInt(), 0) })
        {
            Box(modifier = Modifier) {
                Column {
                    GlideImage(
                        imageModel = if (camera.snapshot.isNotEmpty()) camera.snapshot else "",
                        modifier = Modifier.fillMaxWidth(),
                        requestOptions = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL) ,
                        contentScale = ContentScale.FillWidth)

                    Text(text = camera.name, modifier = Modifier.padding(15.dp))
                }

                Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.padding(10.dp)) {
                    if (camera.rec) {
                        Text("REC", color = Color.Red)
                    }
                    if (camera.favorites) {
                        Icon(imageVector = Icons.Filled.Star, contentDescription = "", tint = Color.Yellow)
                    }
                }
            }
        }
    }




}

