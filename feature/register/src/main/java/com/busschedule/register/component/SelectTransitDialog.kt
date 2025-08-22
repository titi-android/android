package com.busschedule.register.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import core.designsystem.component.HeightSpacer
import core.designsystem.component.WidthSpacer
import core.designsystem.shadow.titiShadow
import core.designsystem.svg.MyIconPack
import core.designsystem.svg.myiconpack.ImageBus
import core.designsystem.svg.myiconpack.ImageSubway
import core.designsystem.theme.TextWColor
import core.designsystem.theme.sbTitle3

@Composable
@Preview
fun SelectTransitDialog(
    onDismissRequest: () -> Unit = {},
    navigateToSelectRegion: () -> Unit = {},
    navigateToSubway: () -> Unit = {},
) {

    Dialog(onDismissRequest = onDismissRequest ) {
        Row {
            TransitImageCard {
                onDismissRequest()
                navigateToSelectRegion()
            }
            WidthSpacer(16.dp)
            TransitImageCard(imageVector = MyIconPack.ImageSubway, text = "지하철") {
                onDismissRequest()
                navigateToSubway()
            }

        }
    }
}


@Composable
fun RowScope.TransitImageCard(
    imageVector: ImageVector = MyIconPack.ImageBus,
    text: String = "버스",
    onNavigate: () -> Unit = {},
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = TextWColor,
            contentColor = Color.Black,
        ),
        modifier = Modifier
            .weight(1f)
            .aspectRatio(0.93f)
            .titiShadow(
                color = Color.Black.copy(alpha = 0.18f),
                blurRadius = 4.dp,
                borderRadius = 12.dp
            ),
        onClick = { onNavigate() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 14.dp, horizontal = 22.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                imageVector = imageVector,
                contentDescription = "image_bus",
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .aspectRatio(1f)
            )
            HeightSpacer(16.dp)
            Text(text = text, style = sbTitle3)
        }
    }
}