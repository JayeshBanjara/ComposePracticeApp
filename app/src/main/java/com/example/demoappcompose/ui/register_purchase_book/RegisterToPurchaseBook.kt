package com.example.demoappcompose.ui.register_purchase_book

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.navigation.NavController
import com.example.demoappcompose.R
import com.example.demoappcompose.ui.VerticalSpacer
import com.example.demoappcompose.ui.components.CustomDropDown
import com.example.demoappcompose.ui.components.CustomTextField
import com.example.demoappcompose.ui.components.CustomTopAppBar
import com.example.demoappcompose.ui.components.MainButton
import com.example.demoappcompose.ui.screenPadding
import com.example.demoappcompose.utility.toast

@Composable
fun RegisterToPurchaseBook(navController: NavController) {
    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = "Register to purchase book",
                showBack = true,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    ) { innerPadding ->
        Surface(modifier = Modifier.padding(innerPadding)) {

            val scrollState = rememberScrollState()
            val localFocusManager = LocalFocusManager.current
            val context = LocalContext.current
            var mExpanded by remember { mutableStateOf(false) }
            val items = listOf("Book 1", "Book 2", "Book 3")
            var mSelectedText by remember { mutableStateOf(items[0]) }
            var firstName by remember { mutableStateOf("") }
            var firstNameError by remember { mutableStateOf(false) }
            var lastName by remember { mutableStateOf("") }
            var lastNameError by remember { mutableStateOf(false) }
            var email by remember { mutableStateOf("") }
            var emailError by remember { mutableStateOf(false) }
            var mobileNum by remember { mutableStateOf("") }
            var emptyNumError by remember { mutableStateOf(false) }
            var address by remember { mutableStateOf("") }
            var addressError by remember { mutableStateOf(false) }
            var price by remember { mutableStateOf("₹ 100.00") }
            var qtyExpanded by remember { mutableStateOf(false) }
            val qtyItems = listOf("1", "2", "3", "4", "5")
            var selectedQty by remember { mutableStateOf(qtyItems[0]) }
            var discount by remember { mutableStateOf("₹ 2.00") }
            var courierCharge by remember { mutableStateOf("₹ 10.00") }
            var finalAmount by remember { mutableStateOf("₹ 108.00") }

            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .padding(screenPadding())
            ) {

                CustomDropDown(
                    mExpanded = mExpanded,
                    items = items,
                    mSelectedText = mSelectedText,
                    onClick = {
                        mExpanded = mExpanded.not()
                    },
                    onDismissRequest = {
                        mExpanded = false
                    }
                ) { label ->
                    mSelectedText = label
                    mExpanded = false
                }

                VerticalSpacer(size = 15)

                CustomTextField(modifier = Modifier
                    .fillMaxWidth(),
                    text = firstName,
                    keyboardType = KeyboardType.Text,
                    capitalization = KeyboardCapitalization.Words,
                    imeAction = ImeAction.Next,
                    isError = firstNameError,
                    placeholderText = stringResource(id = R.string.enter_first_name),
                    errorText = "Please enter first name",
                    onNext = {
                        localFocusManager.moveFocus(FocusDirection.Down)
                    },
                    onValueChange = {
                        if (it.length <= 50) firstName = it
                    })

                VerticalSpacer(size = 15)

                CustomTextField(modifier = Modifier
                    .fillMaxWidth(),
                    text = lastName,
                    keyboardType = KeyboardType.Text,
                    capitalization = KeyboardCapitalization.Words,
                    imeAction = ImeAction.Next,
                    isError = lastNameError,
                    placeholderText = stringResource(id = R.string.enter_last_name),
                    errorText = "Please enter last name",
                    onNext = {
                        localFocusManager.moveFocus(FocusDirection.Down)
                    },
                    onValueChange = {
                        if (it.length <= 50) lastName = it
                    })

                VerticalSpacer(size = 15)

                CustomTextField(modifier = Modifier
                    .fillMaxWidth(),
                    text = email,
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next,
                    isError = emailError,
                    errorText = "Please enter valid email",
                    placeholderText = stringResource(R.string.enter_your_mail),
                    onNext = {
                        localFocusManager.moveFocus(FocusDirection.Down)
                    },
                    onValueChange = { if (it.length <= 50) email = it })

                VerticalSpacer(size = 15)

                CustomTextField(modifier = Modifier
                    .fillMaxWidth(),
                    text = mobileNum,
                    placeholderText = stringResource(R.string.enter_your_mobile_number),
                    keyboardType = KeyboardType.NumberPassword,
                    imeAction = ImeAction.Next,
                    isError = emptyNumError,
                    errorText = stringResource(R.string.please_enter_valid_mobile_number),
                    onNext = {
                        localFocusManager.moveFocus(FocusDirection.Down)
                    },
                    onValueChange = {
                        if (it.length <= 10) mobileNum = it
                    })

                VerticalSpacer(size = 15)

                CustomTextField(modifier = Modifier
                    .fillMaxWidth(),
                    text = address,
                    placeholderText = stringResource(R.string.enter_address),
                    keyboardType = KeyboardType.Text,
                    /*minLines = 3,
                    maxLines = 3,*/
                    imeAction = ImeAction.Done,
                    isError = addressError,
                    errorText = stringResource(R.string.please_enter_address),
                    onNext = {
                        localFocusManager.moveFocus(FocusDirection.Down)
                    },
                    onValueChange = { address = it })

                VerticalSpacer(size = 15)

                CustomTextField(modifier = Modifier
                    .fillMaxWidth(),
                    text = price,
                    placeholderText = "Enter price",
                    keyboardType = KeyboardType.NumberPassword,
                    readOnly = true,
                    imeAction = ImeAction.Next,
                    isError = false,
                    errorText = "",
                    onNext = {
                        localFocusManager.moveFocus(FocusDirection.Down)
                    },
                    onValueChange = { price = it })

                VerticalSpacer(size = 15)

                CustomDropDown(
                    mExpanded = qtyExpanded,
                    items = qtyItems,
                    mSelectedText = selectedQty,
                    onClick = {
                        qtyExpanded = qtyExpanded.not()
                    },
                    onDismissRequest = {
                        qtyExpanded = false
                    }
                ) { label ->
                    selectedQty = label
                    qtyExpanded = false
                }

                VerticalSpacer(size = 15)

                CustomTextField(modifier = Modifier
                    .fillMaxWidth(),
                    text = discount,
                    placeholderText = "Enter discount",
                    keyboardType = KeyboardType.NumberPassword,
                    readOnly = true,
                    imeAction = ImeAction.Next,
                    isError = false,
                    errorText = "",
                    onNext = {
                        localFocusManager.moveFocus(FocusDirection.Down)
                    },
                    onValueChange = { discount = it })

                VerticalSpacer(size = 15)

                CustomTextField(modifier = Modifier
                    .fillMaxWidth(),
                    text = courierCharge,
                    placeholderText = "Enter courier charge",
                    keyboardType = KeyboardType.NumberPassword,
                    readOnly = true,
                    imeAction = ImeAction.Next,
                    isError = false,
                    errorText = "",
                    onNext = {
                        localFocusManager.moveFocus(FocusDirection.Down)
                    },
                    onValueChange = { courierCharge = it })

                VerticalSpacer(size = 15)

                CustomTextField(modifier = Modifier
                    .fillMaxWidth(),
                    text = finalAmount,
                    placeholderText = "Enter final amount",
                    keyboardType = KeyboardType.NumberPassword,
                    readOnly = true,
                    imeAction = ImeAction.Done,
                    isError = false,
                    errorText = "",
                    onNext = {
                        localFocusManager.moveFocus(FocusDirection.Down)
                    },
                    onValueChange = { finalAmount = it })

                VerticalSpacer(size = 15)

                MainButton(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = "Submit"
                ) {
                    firstNameError = firstName.isEmpty()
                    lastNameError = lastName.isEmpty()
                    emailError = email.isEmpty()
                    emptyNumError = mobileNum.length < 10
                    addressError = address.isEmpty()

                    if (firstNameError.not() and lastNameError.not() and emptyNumError.not() and emailError.not() and addressError.not()) {
                        context.toast(message = "Registered for the book purchase")
                        navController.popBackStack()
                    }
                }
            }
        }
    }
}