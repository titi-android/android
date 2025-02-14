package com.busschedule.register.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.busschedule.util.entity.BusType
import core.designsystem.component.WidthSpacer
import core.designsystem.svg.IconPack
import core.designsystem.svg.myiconpack.IcClose
import core.designsystem.theme.BusIcGreen
import core.designsystem.theme.Primary
import core.designsystem.theme.TextMColor
import core.designsystem.theme.TextWColor
import core.designsystem.theme.mTitle

@Composable
fun BusBox(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    name: String,
    type: String,
    onDelete: () -> Unit,
) {
    val busType = BusType.find(type)
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = TextWColor),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(width = 1.dp, color = TextMColor),
        onClick = {}
    ) {
        Row(
            modifier = Modifier.padding(start = 6.dp, end = 8.dp, top = 4.dp, bottom = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = "ic_bus",
                modifier = Modifier.size(16.dp),
                tint = busType.iconColor
            )
            WidthSpacer(width = 8.dp)
            Text(
                text = "${name}번",
                style = mTitle.copy(
                    color = TextMColor,
                    lineHeight = 19.sp,
                    lineHeightStyle = LineHeightStyle(
                        alignment = LineHeightStyle.Alignment.Center,
                        trim = LineHeightStyle.Trim.None
                    )
                )
            )
            WidthSpacer(width = 4.dp)
            Icon(
                imageVector = IconPack.IcClose,
                contentDescription = "ic_close",
                modifier = Modifier
                    .size(14.dp)
                    .clickable { onDelete() },
                tint = TextMColor
            )
        }
    }
}

@Composable
@Preview()
fun TextTest(modifier: Modifier = Modifier) {
    Column {
        Icon(imageVector = Icons.Outlined.CheckCircle, contentDescription = "ic_check", tint = Primary)
        Icon(imageVector = Icons.Default.CheckCircle, contentDescription = "ic_check", tint = BusIcGreen)
    }
}
