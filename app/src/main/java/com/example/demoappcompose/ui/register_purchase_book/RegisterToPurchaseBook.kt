package com.example.demoappcompose.ui.register_purchase_book

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.demoappcompose.R
import com.example.demoappcompose.data.responses.purchase_book.Book
import com.example.demoappcompose.ui.HorizontalSpacer
import com.example.demoappcompose.ui.VerticalSpacer
import com.example.demoappcompose.ui.components.CustomDropDown
import com.example.demoappcompose.ui.components.CustomTextField
import com.example.demoappcompose.ui.components.CustomTopAppBar
import com.example.demoappcompose.ui.components.Loader
import com.example.demoappcompose.ui.components.MainButton
import com.example.demoappcompose.ui.components.ScreenBackground
import com.example.demoappcompose.ui.navigation.Screens
import com.example.demoappcompose.ui.popUpToTop
import com.example.demoappcompose.ui.screenPadding
import com.example.demoappcompose.utility.UiState
import com.example.demoappcompose.utility.toast
import kotlinx.coroutines.launch

@Composable
fun RegisterToPurchaseBook(
    navController: NavController, purchaseBookViewModel: PurchaseBookViewModel
) {
    Scaffold(topBar = {
        CustomTopAppBar(title = "Register to purchase book", showBack = true, onBackClick = {
            navController.popBackStack()
        })
    }) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize()) {

            ScreenBackground()

            Box(
                modifier = Modifier.padding(innerPadding), contentAlignment = Alignment.Center
            ) {
                val scrollState = rememberScrollState()
                val localFocusManager = LocalFocusManager.current
                val context = LocalContext.current
                val coroutineScope = rememberCoroutineScope()
                var mExpanded by remember { mutableStateOf(false) }
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
                var city by remember { mutableStateOf("") }
                var cityError by remember { mutableStateOf(false) }
                var state by remember { mutableStateOf("") }
                var stateError by remember { mutableStateOf(false) }
                var qtyExpanded by remember { mutableStateOf(false) }
                val qtyItems = listOf("1", "2", "3", "4", "5")
                var selectedQty by remember { mutableStateOf(qtyItems[0]) }
                var bookError by remember { mutableStateOf(false) }

                val getBooksState by remember { purchaseBookViewModel.getBookState }.collectAsStateWithLifecycle()
                when (getBooksState) {

                    is UiState.Empty -> {}

                    is UiState.UnAuthorised -> {
                        LaunchedEffect(Unit) {
                            val errorMessage = (getBooksState as UiState.UnAuthorised).errorMessage
                            context.toast(message = errorMessage)
                            navController.navigate(Screens.LoginScreen.route) {
                                popUpToTop(navController)
                            }
                        }
                    }

                    is UiState.Error -> {
                        val errorMessage = (getBooksState as UiState.Error).errorMessage
                        LaunchedEffect(Unit) {
                            context.toast(errorMessage)
                        }
                    }

                    is UiState.Loading -> {
                        Loader()
                    }

                    is UiState.Success -> {

                        Box(
                            modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                        ) {

                            val book = Book(
                                bId = 0,
                                bookName = "Select Book",
                                courierCharge = 0.0,
                                discount = 0.0,
                                finalAmount = 0.0,
                                price = 0.0,
                                status = 0
                            )

                            val bookList =
                                (getBooksState as UiState.Success).data.booksData.bookList
                            var selectedBook by remember { mutableStateOf(book) }

                            Column(
                                modifier = Modifier
                                    .verticalScroll(scrollState)
                                    .padding(screenPadding())
                            ) {

                                BooksDropDown(mExpanded = mExpanded,
                                    items = bookList,
                                    selectedBook = selectedBook,
                                    onClick = {
                                        mExpanded = mExpanded.not()
                                    },
                                    onDismissRequest = {
                                        mExpanded = false
                                    }) { book ->
                                    selectedBook = book
                                    mExpanded = false
                                }
                                if (bookError) {
                                    Row {
                                        HorizontalSpacer(size = 5)
                                        Text(
                                            text = "Please select book", color = Color.Red,
                                            modifier = Modifier.fillMaxWidth(),
                                        )
                                    }
                                }

                                VerticalSpacer(size = 15)

                                CustomTextField(modifier = Modifier.fillMaxWidth(),
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

                                CustomTextField(modifier = Modifier.fillMaxWidth(),
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

                                CustomTextField(modifier = Modifier.fillMaxWidth(),
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

                                CustomTextField(modifier = Modifier.fillMaxWidth(),
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

                                CustomTextField(modifier = Modifier.fillMaxWidth(),
                                    text = address,
                                    placeholderText = stringResource(R.string.enter_address),
                                    keyboardType = KeyboardType.Text,
                                    imeAction = ImeAction.Next,
                                    isError = addressError,
                                    errorText = stringResource(R.string.please_enter_address),
                                    onNext = {
                                        localFocusManager.moveFocus(FocusDirection.Down)
                                    },
                                    onValueChange = { address = it })

                                VerticalSpacer(size = 15)

                                CustomTextField(modifier = Modifier.fillMaxWidth(),
                                    text = city,
                                    placeholderText = stringResource(R.string.enter_city),
                                    keyboardType = KeyboardType.Text,
                                    imeAction = ImeAction.Next,
                                    isError = cityError,
                                    errorText = stringResource(R.string.please_enter_city),
                                    onNext = {
                                        localFocusManager.moveFocus(FocusDirection.Down)
                                    },
                                    onValueChange = { city = it })

                                VerticalSpacer(size = 15)

                                CustomTextField(modifier = Modifier.fillMaxWidth(),
                                    text = state,
                                    placeholderText = stringResource(R.string.enter_state),
                                    keyboardType = KeyboardType.Text,
                                    imeAction = ImeAction.Done,
                                    isError = stateError,
                                    errorText = stringResource(R.string.please_enter_state),
                                    onNext = {
                                        localFocusManager.moveFocus(FocusDirection.Down)
                                    },
                                    onValueChange = { state = it })

                                VerticalSpacer(size = 15)

                                CustomTextField(modifier = Modifier.fillMaxWidth(),
                                    text = (selectedBook.price * selectedQty.toDouble()).toString(),
                                    placeholderText = "Enter price",
                                    keyboardType = KeyboardType.NumberPassword,
                                    readOnly = true,
                                    imeAction = ImeAction.Next,
                                    isError = false,
                                    errorText = "",
                                    onNext = {
                                        localFocusManager.moveFocus(FocusDirection.Down)
                                    },
                                    onValueChange = {
                                        selectedBook.price = it.toDouble()
                                    })

                                VerticalSpacer(size = 15)

                                CustomDropDown(mExpanded = qtyExpanded,
                                    items = qtyItems,
                                    mSelectedText = selectedQty,
                                    onClick = {
                                        qtyExpanded = qtyExpanded.not()
                                    },
                                    onDismissRequest = {
                                        qtyExpanded = false
                                    }) { label ->
                                    selectedQty = label
                                    qtyExpanded = false

                                    selectedBook.finalAmount = ((selectedBook.price * selectedQty.toDouble()) + selectedBook.courierCharge) - selectedBook.discount
                                }

                                VerticalSpacer(size = 15)

                                CustomTextField(modifier = Modifier.fillMaxWidth(),
                                    text = (selectedBook.discount * selectedQty.toDouble()).toString(),
                                    placeholderText = "Enter discount",
                                    keyboardType = KeyboardType.NumberPassword,
                                    readOnly = true,
                                    imeAction = ImeAction.Next,
                                    isError = false,
                                    errorText = "",
                                    onNext = {
                                        localFocusManager.moveFocus(FocusDirection.Down)
                                    },
                                    onValueChange = {
                                        selectedBook.discount = it.toDouble()
                                    })

                                VerticalSpacer(size = 15)

                                CustomTextField(modifier = Modifier.fillMaxWidth(),
                                    text = selectedBook.courierCharge.toString(),
                                    placeholderText = "Enter courier charge",
                                    keyboardType = KeyboardType.NumberPassword,
                                    readOnly = true,
                                    imeAction = ImeAction.Next,
                                    isError = false,
                                    errorText = "",
                                    onNext = {
                                        localFocusManager.moveFocus(FocusDirection.Down)
                                    },
                                    onValueChange = {})

                                VerticalSpacer(size = 15)

                                CustomTextField(modifier = Modifier.fillMaxWidth(),
                                    text = selectedBook.finalAmount.toString(),
                                    placeholderText = "Enter final amount",
                                    keyboardType = KeyboardType.NumberPassword,
                                    readOnly = true,
                                    imeAction = ImeAction.Done,
                                    isError = false,
                                    errorText = "",
                                    onNext = {
                                        localFocusManager.moveFocus(FocusDirection.Down)
                                    },
                                    onValueChange = {})

                                VerticalSpacer(size = 15)

                                MainButton(
                                    modifier = Modifier.fillMaxWidth(), text = "Submit"
                                ) {
                                    firstNameError = firstName.isEmpty()
                                    lastNameError = lastName.isEmpty()
                                    emailError = email.isEmpty()
                                    emptyNumError = mobileNum.length < 10
                                    addressError = address.isEmpty()
                                    bookError = selectedBook.bId == 0

                                    if (bookError.not() and firstNameError.not() and lastNameError.not() and emptyNumError.not() and emailError.not() and addressError.not()) {
                                        coroutineScope.launch {
                                            purchaseBookViewModel.registerToPurchaseBook(
                                                bookId = selectedBook.bId.toString(),
                                                qty = selectedQty.toInt(),
                                                firstName = firstName,
                                                lastName = lastName,
                                                mobileNo = mobileNum,
                                                email = email,
                                                fullAddress = address,
                                                city = city,
                                                state = state
                                            )
                                        }
                                    }
                                }
                            }

                            val uiState by remember { purchaseBookViewModel.uiState }.collectAsStateWithLifecycle()
                            when (uiState) {
                                is UiState.Empty -> {}

                                is UiState.UnAuthorised -> {
                                    LaunchedEffect(Unit) {
                                        val errorMessage =
                                            (uiState as UiState.UnAuthorised).errorMessage
                                        context.toast(message = errorMessage)
                                        navController.navigate(Screens.LoginScreen.route) {
                                            popUpToTop(navController)
                                        }
                                    }
                                }

                                is UiState.Error -> {
                                    val errorMessage = (uiState as UiState.Error).errorMessage
                                    LaunchedEffect(Unit) {
                                        context.toast(errorMessage)
                                    }
                                }

                                is UiState.Loading -> {
                                    Loader()
                                }

                                is UiState.Success -> {
                                    val message = (uiState as UiState.Success).data.message
                                    LaunchedEffect(Unit) {
                                        context.toast(message)
                                        navController.popBackStack()
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}