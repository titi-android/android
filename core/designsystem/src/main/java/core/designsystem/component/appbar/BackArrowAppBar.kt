package core.designsystem.component.appbar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.busschedule.util.ext.noRippleClickable
import core.designsystem.theme.Primary
import core.designsystem.theme.rTopBar

@Composable
fun BackArrowAppBar(title: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 18.dp)
    ) {
        Icon(
            imageVector = Icons.Rounded.ArrowBackIosNew,
            contentDescription = "ic_back_arrow",
            modifier = Modifier
                .size(24.dp)
                .align(Alignment.CenterStart)
                .noRippleClickable { onClick() }
        )
        Text(text = title, modifier = Modifier.align(Alignment.Center), style = rTopBar.copy(Primary))

    }
}