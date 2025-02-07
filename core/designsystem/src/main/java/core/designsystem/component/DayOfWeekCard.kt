package core.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import core.designsystem.theme.Primary
import core.designsystem.theme.TextWColor
import core.designsystem.theme.mTitle
import core.designsystem.theme.sbTitle3

@Composable
fun RowScope.DayOfWeekCard(text: String, isSelected: Boolean, isShadow: Boolean, onClick: () -> Unit, ) {
    val containerColor = if (isSelected) Primary else TextWColor
    val style = if (isSelected) sbTitle3.copy(color = TextWColor) else mTitle.copy(Primary)
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.elevatedCardElevation(if (isShadow) 8.dp else 0.dp),
        colors = CardDefaults.cardColors(containerColor = containerColor),
        modifier = Modifier
            .weight(1f)
            .aspectRatio(1f),
        onClick = { onClick() }
    ) {
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = text, style = style)
        }
    }
}