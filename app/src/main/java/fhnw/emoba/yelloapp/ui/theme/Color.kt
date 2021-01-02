package fhnw.emoba.yelloapp.ui.theme

import androidx.compose.ui.graphics.Color

val purple200 = Color(0xFFBB86FC)
val purple500 = Color(0xFF6200EE)
val purple700 = Color(0xFF3700B3)

val teal200   = Color(0xFF03DAC5)

val blue50     = getColor("#E3F2FD")
val blue100    = getColor("#BBDEFB")
val blue200    = getColor("#90CAF9")
val blue300    = getColor("#64B5F6")
val blue400    = getColor("#42A5F5")
val blue500    = getColor("#2196F3")
val blue600    = getColor("#1E88E5")
val blue700    = getColor("#1976D2")
val blue800    = getColor("#1565C0")
val blue900    = getColor("#0D47A1")
val blueA100   = getColor("#82B1FF")
val blueA200   = getColor("#448AFF")
val blueA400   = getColor("#2979FF")
val blueA700   = getColor("#2962FF")
val blueA800 = getColor("#00265f")

val lightBlue50   = getColor("#E1F5FE")
val lightBlue100  = getColor("#B3E5FC")
val lightBlue200  = getColor("#81D4FA")
val lightBlue300  = getColor("#4FC3F7")
val lightBlue400  = getColor("#29B6F6")
val lightBlue500  = getColor("#03A9F4")
val lightBlue600  = getColor("#039BE5")
val lightBlue700  = getColor("#0288D1")
val lightBlue800  = getColor("#0277BD")
val lightBlue900  = getColor("#01579B")
val lightBlueA100 = getColor("#80D8FF")
val lightBlueA200 = getColor("#40C4FF")
val lightBlueA400 = getColor("#00B0FF")
val lightBlueA700 = getColor("#0091EA")


val amber50   = getColor("#FFF8E1")
val amber100  = getColor("#FFECB3")
val amber200  = getColor("#FFE082")
val amber300  = getColor("#FFD54F")
val amber400  = getColor("#FFCA28")
val amber500  = getColor("#FFC107")
val amber600  = getColor("#FFB300")
val amber700  = getColor("#FFA000")
val amber800  = getColor("#FF8F00")
val amber900  = getColor("#FF6F00")
val amberA100 = getColor("#FFE57F")
val amberA200 = getColor("#FFD740")
val amberA400 = getColor("#FFC400")
val amberA700 = getColor("#FFAB00")

val gray50  = getColor("#FAFAFA")
val gray100 = getColor("#F5F5F5")
val gray200 = getColor("#EEEEEE")
val gray300 = getColor("#E0E0E0")
val gray400 = getColor("#BDBDBD")
val gray500 = getColor("#9E9E9E")
val gray600 = getColor("#757575")
val gray700 = getColor("#616161")
val gray800 = getColor("#424242")
val gray900 = getColor("#212121")

val black900 = getColor("#161616")

private fun getColor(colorString: String): Color {
    return Color(android.graphics.Color.parseColor(colorString))
}