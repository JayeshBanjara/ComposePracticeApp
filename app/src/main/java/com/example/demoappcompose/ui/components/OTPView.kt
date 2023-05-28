import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.demoappcompose.ui.HorizontalSpacer
import com.example.demoappcompose.ui.theme.TitleColor

const val PIN_VIEW_TYPE_UNDERLINE = 0
const val PIN_VIEW_TYPE_BORDER = 1

@Composable
fun PinView(
    pinText: String,
    onPinTextChange: (String) -> Unit,
    digitColor: Color = TitleColor,
    digitSize: TextUnit = 16.sp,
    containerSize: Dp = digitSize.value.dp * 2,
    digitCount: Int = 4,
    type: Int = PIN_VIEW_TYPE_UNDERLINE,
) {
    BasicTextField(value = pinText,
        onValueChange = onPinTextChange,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        decorationBox = {
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 80.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                repeat(digitCount) { index ->
                    DigitView(index, pinText, digitColor, digitSize, containerSize, type = type)
                    HorizontalSpacer(size = 5)
                }
            }
        })
}


@Composable
private fun DigitView(
    index: Int,
    pinText: String,
    digitColor: Color,
    digitSize: TextUnit,
    containerSize: Dp,
    type: Int = PIN_VIEW_TYPE_UNDERLINE,
) {
    val modifier = if (type == PIN_VIEW_TYPE_BORDER) {
        Modifier
            .width(containerSize)
            .border(
                width = 1.dp,
                color = digitColor,
                shape = MaterialTheme.shapes.medium
            )
            .padding(bottom = 3.dp)
    } else Modifier.width(containerSize)

    Column(horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        Text(
            text = if (index >= pinText.length) "" else pinText[index].toString(),
            color = digitColor,
            modifier = modifier,
            //style = MaterialTheme.typography.body1,
            fontSize = digitSize,
            textAlign = TextAlign.Center)
        if (type == PIN_VIEW_TYPE_UNDERLINE) {
            Spacer(modifier = Modifier.height(2.dp))
            Box(
                modifier = Modifier
                    .background(digitColor)
                    .height(1.dp)
                    .width(containerSize)
            )
        }
    }
}