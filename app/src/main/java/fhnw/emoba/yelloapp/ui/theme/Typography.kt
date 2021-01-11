package fhnw.emoba.yelloapp.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.sp
import fhnw.emoba.R


private val Lato = fontFamily(
        font(R.font.lato_extra_light, FontWeight.ExtraLight),
        font(R.font.lato_hai,         FontWeight.Thin),
        font(R.font.lato_lig,         FontWeight.Light),
        font(R.font.lato_reg,         FontWeight.Normal),
        font(R.font.lato_bol,         FontWeight.Bold),
        font(R.font.lato_bla,         FontWeight.Black)
)

private val Ubuntu = fontFamily(
        font(R.font.ubuntu_light_italic, FontWeight.ExtraLight),
        font(R.font.ubuntu_light,        FontWeight.Thin),
        font(R.font.ubuntu_regular,      FontWeight.Light),
        font(R.font.ubuntu_medium,       FontWeight.Normal),
        font(R.font.ubuntu_bold,         FontWeight.Bold),
        font(R.font.ubuntu_bold_italic,  FontWeight.Black)
)

private val JosefinSans = fontFamily(
        font(R.font.josefinsans_extra_light, FontWeight.ExtraLight),
        font(R.font.josefinsans_thin,         FontWeight.Thin),
        font(R.font.josefinsans_light,         FontWeight.Light),
        font(R.font.josefinsans_regular,         FontWeight.Normal),
        font(R.font.josefinsans_semi_bold,         FontWeight.Bold),
        font(R.font.josefinsans_bold,         FontWeight.Black)
)


// Set of Material typography styles to start with
val typography = Typography(
        // Einbinden eines Custom Fonts
//        defaultFontFamily = Lato,
//        defaultFontFamily = Ubuntu,
        defaultFontFamily = JosefinSans,

        h1 = TextStyle(
//                fontSize      = 96.sp,
                fontSize      = 96.sp,
//                fontWeight    = FontWeight.Light,
                lineHeight    = 117.sp,
                letterSpacing = (-1.5).sp
        ),
        h2 = TextStyle(
                fontSize      = 50.sp,
//                fontWeight    = FontWeight.Light,
                lineHeight    = 73.sp,
                letterSpacing = (-0.5).sp,
                fontStyle = FontStyle.Italic,
        ),
        h3 = TextStyle(
                fontSize      = 48.sp,
//                fontWeight    = FontWeight.Normal,
                lineHeight    = 59.sp
        ),
        h4 = TextStyle(
                fontSize      = 30.sp,
//                fontWeight    = FontWeight.SemiBold,
                lineHeight    = 37.sp
        ),
        h5 = TextStyle(
                fontSize      = 24.sp,
//                fontWeight    = FontWeight.SemiBold,
                lineHeight    = 29.sp,
                fontStyle = FontStyle.Italic
        ),
        h6 = TextStyle(
                fontSize      = 18.sp,
                fontWeight    = FontWeight.SemiBold,
                fontStyle     = FontStyle.Italic,
                lineHeight    = 24.sp
        ),
        subtitle1 = TextStyle(
                fontSize      = 16.sp,
//                fontWeight    = FontWeight.SemiBold,
                lineHeight    = 24.sp,
                letterSpacing = 0.15.sp
        ),
        subtitle2 = TextStyle(
                fontSize      = 14.sp,
//                fontWeight    = FontWeight.Bold,
                lineHeight    = 24.sp,
                letterSpacing = 0.1.sp
        ),
        body1 = TextStyle(
                fontSize      = 16.sp,
//                fontWeight    = FontWeight.Normal,
                lineHeight    = 28.sp,
                letterSpacing = 0.15.sp
        ),
        body2 = TextStyle(
                fontSize      = 14.sp,
//                fontWeight    = FontWeight.Medium,
                lineHeight    = 20.sp,
                letterSpacing = 0.25.sp
        ),
        button = TextStyle(
                fontSize      = 15.sp,
                fontWeight    = FontWeight.SemiBold,
                lineHeight    = 16.sp,
                letterSpacing = 1.sp
        ),
        caption = TextStyle(
                fontSize      = 12.sp,
//                fontWeight    = FontWeight.Bold,
                lineHeight    = 16.sp,
                letterSpacing = 0.4.sp,
                fontStyle = FontStyle.Italic,
        ),
        overline = TextStyle(
                fontSize      = 12.sp,
//                fontWeight    = FontWeight.SemiBold,
                lineHeight    = 16.sp,
                letterSpacing = 1.sp
        )
)