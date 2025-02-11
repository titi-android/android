package com.busschedule.setting.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import core.designsystem.component.HeightSpacer
import core.designsystem.component.WidthSpacer
import core.designsystem.svg.IconPack
import core.designsystem.svg.myiconpack.IcEdit
import core.designsystem.theme.Primary
import core.designsystem.theme.rTitle

@Composable
fun ProfileCard(image: String, text: String, onEdit: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Card(shape = CircleShape, modifier = Modifier.size(120.dp)) {
            Image(
                painter = painterResource(id = core.designsystem.R.drawable.ic_launcher_background),
                contentDescription = "image_profile",
                modifier = Modifier.fillMaxSize()
            )
        }
        HeightSpacer(height = 12.dp)
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = text, style = rTitle.copy(Primary))
            WidthSpacer(width = 10.dp)
            Icon(
                imageVector = IconPack.IcEdit,
                contentDescription = "ic_edit",
                tint = Primary,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}