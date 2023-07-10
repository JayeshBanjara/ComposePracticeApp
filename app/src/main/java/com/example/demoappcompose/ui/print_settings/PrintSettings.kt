package com.example.demoappcompose.ui.print_settings

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.navigation.NavController
import com.example.demoappcompose.R
import com.example.demoappcompose.ui.VerticalSpacer
import com.example.demoappcompose.ui.components.CustomDropDown
import com.example.demoappcompose.ui.components.CustomTextField
import com.example.demoappcompose.ui.components.CustomTopAppBar
import com.example.demoappcompose.ui.components.MainButton
import com.example.demoappcompose.ui.screenPadding
import com.example.demoappcompose.ui.theme.Blue
import java.util.Calendar
import java.util.Date

@Composable
fun PrintSettings(navController: NavController) {
    Scaffold(topBar = {
        CustomTopAppBar(
            title = "Print Settings",
            showBack = true,
            onBackClick = {
                navController.popBackStack()
            }
        )
    }) { innerPadding ->

        val localFocusManager = LocalFocusManager.current
        val scrollState = rememberScrollState()
        /*var imgName by remember { mutableStateOf("") }
        var imgError by remember { mutableStateOf(false) }*/
        var instituteName by remember { mutableStateOf("") }
        var instituteNameError by remember { mutableStateOf(false) }
        var examName by remember { mutableStateOf("") }
        var examNameError by remember { mutableStateOf(false) }
        var chapterNumber by remember { mutableStateOf("") }
        var chapterNumberError by remember { mutableStateOf(false) }
        var examTime by remember { mutableStateOf("") }
        var examTimeError by remember { mutableStateOf(false) }
        var examMarks by remember { mutableStateOf("") }
        var examMarksError by remember { mutableStateOf(false) }
        var examDate by remember { mutableStateOf("") }
        var examDateError by remember { mutableStateOf(false) }
        val waterMarkTypes = listOf("Text", "Institute Logo")
        var waterMarkError by remember { mutableStateOf(false) }
        val (selectedWaterMarkType, setSelectedWaterMark) = remember { mutableStateOf(waterMarkTypes[0]) }
        var waterMarkText by remember { mutableStateOf("") }
        val logoSizes = listOf("25%", "50%", "75%", "100%")
        val (selectedLogoSize, setSelectedLogoSize) = remember { mutableStateOf(logoSizes[0]) }
        var endPaperMsg by remember { mutableStateOf("") }
        var endPaperMsgError by remember { mutableStateOf(false) }
        var pageFooter by remember { mutableStateOf("") }
        var pageFooterError by remember { mutableStateOf(false) }
        var pageFooterCheckedState by remember { mutableStateOf(false) }
        var isFontSizeExpanded by remember { mutableStateOf(false) }
        val items = listOf("12", "14", "16", "18", "20")
        var selectedFontSize by remember { mutableStateOf(items[2]) }
        var pageBorderCheckedState by remember { mutableStateOf(false) }
        val paperTypes = listOf(
            "PDF Only Exam Paper",
            "PDF Exam Paper & Solution with only Answer",
            "Material"
            /*"Worksheet"*/
        )
        var paperTypesError by remember { mutableStateOf(false) }
        val (selectedPaperTypes, setSelectedPaperTypes) = remember { mutableStateOf(paperTypes[0]) }


        // Declaring integer values
        // for year, month and day
        val mYear: Int
        val mMonth: Int
        val mDay: Int

        // Initializing a Calendar
        val mCalendar = Calendar.getInstance()

        // Fetching current year, month and day
        mYear = mCalendar.get(Calendar.YEAR)
        mMonth = mCalendar.get(Calendar.MONTH)
        mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

        mCalendar.time = Date()

        // Fetching the Local Context
        val mContext = LocalContext.current

        // Declaring DatePickerDialog and setting
        // initial values as current values (present year, month and day)
        val mDatePickerDialog = DatePickerDialog(
            mContext,
            { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDay: Int ->

                val tempSelectedDay = if (selectedDay < 10) {
                    "0$selectedDay"
                } else {
                    selectedDay
                }

                val tempSelectedMonth = if (selectedMonth < 10) {
                    "0${selectedMonth + 1}"
                } else {
                    "${selectedMonth + 1}"
                }

                examDate = "$tempSelectedDay/$tempSelectedMonth/$selectedYear"

            }, mYear, mMonth, mDay
        )

        /*val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickVisualMedia(),
            onResult = { uri ->
                if (uri != null) {
                    imgName = uri.toString()
                }
            }
        )*/

        Box(
            modifier = Modifier.fillMaxSize()
        ) {

            Image(
                painter = painterResource(id = R.drawable.screen_bg),
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )

            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .padding(
                        start = screenPadding(),
                        top = innerPadding.calculateTopPadding(),
                        end = screenPadding(),
                        bottom = screenPadding()
                    )
            ) {

                VerticalSpacer(size = 5)

                /*HeaderText(text = "Institute Logo Title (Size: 180 x 40 mm)")
                VerticalSpacer(size = 5)
                CustomTextField(
                    modifier = Modifier.fillMaxWidth(),
                    text = imgName,
                    trailingIcon = painterResource(id = R.drawable.ic_upload),
                    onTrailingIconClick = {
                        singlePhotoPickerLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    },
                    readOnly = true,
                    placeholderText = "Image Upload",
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next,
                    isError = imgError,
                    errorText = "",
                    onNext = { localFocusManager.moveFocus(FocusDirection.Down) },
                    onValueChange = { imgName = it }
                )

                VerticalSpacer(size = 8)*/

                HeaderText(text = "Institute Name")
                VerticalSpacer(size = 5)
                CustomTextField(
                    modifier = Modifier.fillMaxWidth(),
                    text = instituteName,
                    placeholderText = "Ravi Education",
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next,
                    isError = instituteNameError,
                    errorText = "Please enter institute name",
                    onNext = { localFocusManager.moveFocus(FocusDirection.Down) },
                    onValueChange = {
                        instituteName = it
                        instituteNameError = false
                    }
                )

                VerticalSpacer(size = 8)

                HeaderText(text = "Exam Name")
                VerticalSpacer(size = 5)
                CustomTextField(
                    modifier = Modifier.fillMaxWidth(),
                    text = examName,
                    placeholderText = "Monthly Exam",
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next,
                    isError = examNameError,
                    errorText = "Please enter exam name",
                    onNext = { localFocusManager.moveFocus(FocusDirection.Down) },
                    onValueChange = {
                        examName = it
                        examNameError = false
                    }
                )

                VerticalSpacer(size = 8)

                HeaderText(text = "Chapter Number")
                VerticalSpacer(size = 5)
                CustomTextField(
                    modifier = Modifier.fillMaxWidth(),
                    text = chapterNumber,
                    placeholderText = "1, 2, 3 OR All",
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next,
                    isError = chapterNumberError,
                    errorText = "Please enter chapter number",
                    onNext = { localFocusManager.moveFocus(FocusDirection.Down) },
                    onValueChange = {
                        chapterNumber = it
                        chapterNumberError = false
                    }
                )

                VerticalSpacer(size = 8)

                HeaderText(text = "Exam Time")
                VerticalSpacer(size = 5)
                CustomTextField(
                    modifier = Modifier.fillMaxWidth(),
                    text = examTime,
                    placeholderText = "30 Min/1 Hour/1 Hr 30 Min/2 Hours",
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next,
                    isError = examTimeError,
                    errorText = "Please enter exam time",
                    onNext = { localFocusManager.moveFocus(FocusDirection.Down) },
                    onValueChange = {
                        examTime = it
                        examTimeError = false
                    }
                )

                VerticalSpacer(size = 8)

                HeaderText(text = "Exam Marks")
                VerticalSpacer(size = 5)
                CustomTextField(
                    modifier = Modifier.fillMaxWidth(),
                    text = examMarks,
                    placeholderText = "Exam Marks",
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next,
                    isError = examMarksError,
                    errorText = "Please enter exam marks",
                    onNext = { localFocusManager.moveFocus(FocusDirection.Down) },
                    onValueChange = {
                        examMarks = it
                        examMarksError = false
                    }
                )

                VerticalSpacer(size = 8)

                HeaderText(text = "Exam Date")
                VerticalSpacer(size = 5)
                CustomTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            mDatePickerDialog.show()
                        },
                    text = examDate,
                    placeholderText = "DD/MM/YYYY",
                    readOnly = true,
                    enabled = false,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next,
                    isError = examDateError,
                    errorText = "Please enter exam date",
                    onNext = { localFocusManager.moveFocus(FocusDirection.Down) },
                    onValueChange = { }
                )

                VerticalSpacer(size = 8)

                HeaderText(text = "Water Mark")
                VerticalSpacer(size = 5)
                HorizontalRadioGroup(
                    mItems = waterMarkTypes,
                    selected = selectedWaterMarkType,
                    setSelected = setSelectedWaterMark
                )
                if (selectedWaterMarkType == "Institute Logo") {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.Start
                    ) {
                        HorizontalRadioGroup(
                            mItems = logoSizes,
                            selected = selectedLogoSize,
                            setSelected = setSelectedLogoSize
                        )
                        VerticalSpacer(size = 5)
                    }
                }
                if (selectedWaterMarkType == "Text") {
                    CustomTextField(
                        modifier = Modifier.fillMaxWidth(),
                        text = waterMarkText,
                        placeholderText = "Water Mark",
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next,
                        isError = waterMarkError,
                        errorText = "Please enter water mark",
                        onNext = { localFocusManager.moveFocus(FocusDirection.Down) },
                        onValueChange = {
                            waterMarkText = it
                            waterMarkError = false
                        }
                    )
                }

                VerticalSpacer(size = 8)

                HeaderText(text = "Message for end of the Paper")
                VerticalSpacer(size = 5)
                CustomTextField(
                    modifier = Modifier.fillMaxWidth(),
                    text = endPaperMsg,
                    placeholderText = "ALL THE BEST",
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next,
                    isError = endPaperMsgError,
                    errorText = "Please enter message",
                    onNext = { localFocusManager.moveFocus(FocusDirection.Down) },
                    onValueChange = {
                        endPaperMsg = it
                        endPaperMsgError = false
                    }
                )
                VerticalSpacer(size = 8)

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Checkbox(
                        checked = pageFooterCheckedState,
                        onCheckedChange = { pageFooterCheckedState = it },
                        colors = CheckboxDefaults.colors(
                            checkedColor = Blue,
                            uncheckedColor = Blue
                        )
                    )
                    HeaderText(text = "Page Footer")
                }
                if (pageFooterCheckedState) {
                    Column {
                        VerticalSpacer(size = 5)
                        CustomTextField(
                            modifier = Modifier.fillMaxWidth(),
                            text = pageFooter,
                            placeholderText = "Page Footer",
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next,
                            isError = pageFooterError,
                            errorText = "Please enter page footer",
                            onNext = { localFocusManager.moveFocus(FocusDirection.Down) },
                            onValueChange = {
                                pageFooter = it
                                pageFooterError = false
                            }
                        )
                    }
                }

                VerticalSpacer(size = 8)

                HeaderText(text = "Font Size")
                VerticalSpacer(size = 5)
                CustomDropDown(
                    mExpanded = isFontSizeExpanded,
                    items = items,
                    mSelectedText = selectedFontSize,
                    onClick = {
                        isFontSizeExpanded = isFontSizeExpanded.not()
                    },
                    onDismissRequest = {
                        isFontSizeExpanded = false
                    }
                ) { label ->
                    selectedFontSize = label
                    isFontSizeExpanded = false
                }

                VerticalSpacer(size = 8)

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Checkbox(
                        checked = pageBorderCheckedState,
                        onCheckedChange = { pageBorderCheckedState = it },
                        colors = CheckboxDefaults.colors(
                            checkedColor = Blue,
                            uncheckedColor = Blue
                        )
                    )
                    HeaderText(text = "Page Border")
                }

                VerticalSpacer(size = 8)

                VerticalRadioGroup(
                    mItems = paperTypes,
                    selected = selectedPaperTypes,
                    setSelected = setSelectedPaperTypes
                )

                VerticalSpacer(size = 8)

                MainButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Download PDF Exam Paper"
                ) {

                }
            }
        }
    }
}