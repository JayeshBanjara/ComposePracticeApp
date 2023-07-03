package com.example.demoappcompose.ui.create_question.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import com.example.demoappcompose.R
import com.example.demoappcompose.data.responses.questions.HeadingData
import com.example.demoappcompose.ui.HorizontalSpacer
import com.example.demoappcompose.ui.theme.Blue
import com.example.demoappcompose.ui.theme.GreyDark
import com.example.demoappcompose.ui.theme.GreyLight
import com.example.demoappcompose.ui.theme.HintColor
import com.example.demoappcompose.ui.theme.TitleColor

@Composable
fun QuestionTitleDropDown(
    modifier: Modifier,
    mExpanded: Boolean,
    items: List<HeadingData>,
    selectedHeading: HeadingData,
    onClick: () -> Unit,
    onDismissRequest: () -> Unit,
    onItemSelect: (heading: HeadingData) -> Unit
) {
    var mTextFieldSize by remember { mutableStateOf(Size.Zero)}

    Box(
        modifier = modifier
            .clickable { onClick() }
            .clip(shape = RoundedCornerShape(5.dp))
            .background(Color.White)
            .padding(10.dp),
        contentAlignment = Alignment.Center
    ) {
        BasicTextField(
            modifier = Modifier
                .clickable { onClick() }
                .fillMaxWidth()
                .background(Color.White)
                .onGloballyPositioned { coordinates ->
                    // This value is used to assign to
                    // the DropDown the same width
                    mTextFieldSize = coordinates.size.toSize()
                },
            value = selectedHeading.headingName,
            readOnly = true,
            onValueChange = {

            },
            textStyle = TextStyle(
                color = TitleColor,
                fontSize = 14.sp,
                fontWeight = FontWeight.W500,
                fontFamily = FontFamily(Font(R.font.quicksand_medium))
            ),
            decorationBox = { innerTextField ->
                Row(modifier = Modifier.fillMaxWidth().clickable { onClick() }) {
                    if (selectedHeading == null) {
                        Text(
                            text = stringResource(id = R.string.select_your_post),
                            color = HintColor,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.W600,
                            fontFamily = FontFamily(Font(R.font.quicksand_medium))
                        )
                    }
                }
                innerTextField()
            }
        )

        Row(modifier = Modifier
            .align(Alignment.TopEnd).clickable {  onClick() }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_drop_down),
                contentDescription = ""
            )
            HorizontalSpacer(size = 5)
        }
        
        DropdownMenu(
            expanded = mExpanded,
            onDismissRequest = { onDismissRequest() },
            modifier = Modifier
                .width(with(LocalDensity.current) { mTextFieldSize.width.toDp() })
        ) {
            items.forEach { heading ->
                DropdownMenuItem(onClick = {
                    onItemSelect(heading)
                },
                text = {
                    Text(text = heading.headingName)
                })
            }
        }
    }
}