package core.designsystem.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.busschedule.designsystem.R

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
)

val bigLogo = TextStyle(
    fontFamily = FontFamily(Font(R.font.riasans)),
    fontWeight = FontWeight.ExtraBold,
    fontSize = 40.sp,
    color = Primary
)

val logo = TextStyle(
    fontFamily = FontFamily(Font(R.font.riasans)),
    fontWeight = FontWeight.ExtraBold,
    fontSize = 20.sp,
    color = Primary
)

val sbTitle = TextStyle(
    fontFamily = FontFamily(Font(R.font.roboto)),
    fontWeight = FontWeight.SemiBold,
    fontSize = 24.sp,
)
val sbTitle2 = TextStyle(
    fontFamily = FontFamily(Font(R.font.roboto)),
    fontWeight = FontWeight.SemiBold,
    fontSize = 20.sp,
)
val sbTitle3 = TextStyle(
    fontFamily = FontFamily(Font(R.font.roboto)),
    fontWeight = FontWeight.SemiBold,
    fontSize = 16.sp,
)
val sbTitle4 = TextStyle(
    fontFamily = FontFamily(Font(R.font.roboto)),
    fontWeight = FontWeight.SemiBold,
    fontSize = 14.sp,
)

val sbTextBtn = TextStyle(
    fontFamily = FontFamily(Font(R.font.roboto)),
    fontWeight = FontWeight.SemiBold,
    fontSize = 12.sp,
)

val mTextBtn = TextStyle(
    fontFamily = FontFamily(Font(R.font.roboto)),
    fontWeight = FontWeight.Medium,
    fontSize = 16.sp,
)

val mButton = TextStyle(
    fontSize = 18.sp,
    fontFamily = FontFamily(Font(R.font.roboto)),
    fontWeight = FontWeight(700),
    color = Primary3,
)

val mTitle = TextStyle(
    fontFamily = FontFamily(Font(R.font.roboto)),
    fontWeight = FontWeight.Medium,
    fontSize = 16.sp,
)
val mBody = TextStyle(
    fontFamily = FontFamily(Font(R.font.roboto)),
    fontWeight = FontWeight.Medium,
    fontSize = 14.sp,
)
val mBody2 = TextStyle(
    fontFamily = FontFamily(Font(R.font.roboto)),
    fontWeight = FontWeight.Medium,
    fontSize = 12.sp,
)

val mFooter = TextStyle(
    fontFamily = FontFamily(Font(R.font.roboto)),
    fontWeight = FontWeight.Normal,
    fontSize = 18.sp,
)

val rTopBar = TextStyle(
    fontFamily = FontFamily(Font(R.font.roboto)),
    fontWeight = FontWeight.Normal,
    fontSize = 22.sp,
)

val rTitle = TextStyle(
    fontFamily = FontFamily(Font(R.font.roboto)),
    fontWeight = FontWeight.Normal,
    fontSize = 20.sp,
)

val rTextBox = TextStyle(
    fontFamily = FontFamily(Font(R.font.roboto)),
    fontWeight = FontWeight.Normal,
    fontSize = 16.sp,
)

val rTextBtn2 = TextStyle(
    fontFamily = FontFamily(Font(R.font.roboto)),
    fontWeight = FontWeight.Normal,
    fontSize = 14.sp,
)

val rTextLabel = TextStyle(
    fontFamily = FontFamily(Font(R.font.roboto)),
    fontWeight = FontWeight.Normal,
    fontSize = 12.sp,
)
val rFooter = TextStyle(
    fontFamily = FontFamily(Font(R.font.roboto)),
    fontWeight = FontWeight.Normal,
    fontSize = 10.sp,
)

val normal10sp = TextStyle(
    fontFamily = FontFamily(Font(R.font.roboto)),
    fontWeight = FontWeight.Medium,
    fontSize = 10.sp,
)