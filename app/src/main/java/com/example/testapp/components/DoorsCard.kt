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
import androidx.compose.material.icons.outlined.Edit
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
import com.example.testapp.models.Door
import com.example.testapp.ui.theme.editIconColor
import com.example.testapp.ui.theme.starIconColor
import com.skydoves.landscapist.glide.GlideImage
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DoorsCard(door: Door, renameAction: (item: Door)->Unit) {

    val sizePx = with(LocalDensity.current) { 120.dp.toPx() } //отвечает за то насколько будет сдвиг свайпа
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

        Row(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 15.dp),
            horizontalArrangement = Arrangement.spacedBy(5.dp)) {
            IconButton(
                onClick = { renameAction(door) },
                modifier = Modifier
                    .border(1.dp, color = Color.Gray.copy(0.5f), shape = CircleShape))
            {
                Icon(imageVector = Icons.Outlined.Edit, contentDescription = "", tint = editIconColor)
            }
            IconButton(
                onClick = { },
                modifier = Modifier
                    .border(1.dp, color = Color.Gray.copy(0.5f), shape = CircleShape))
            {
              Icon(painter = painterResource(id = R.drawable.star_icon), contentDescription = "", tint = starIconColor)
            }
        }


        Card(
            shape = RoundedCornerShape(5),
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
                .offset { IntOffset(swipeableState.offset.value.roundToInt(), 0) }
        ) {
            Column {
                if (door.snapshot.isNotEmpty()) {
                    GlideImage(
                        imageModel =  door.snapshot ,
                        modifier = Modifier.fillMaxWidth(),
                        requestOptions = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL) ,
                        contentScale = ContentScale.FillWidth)
                }
                Text(text = door.name, modifier = Modifier.padding(15.dp))
            }
        }
    }
}

