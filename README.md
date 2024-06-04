# Project Architecture

## Overview

This project uses an architecture based on the MVVM (Model-View-ViewModel) pattern. This architecture is widely used in Android applications as it promotes a clear separation of responsibilities and facilitates testing.

## Details

### Model

The model represents the data and business logic of the application. In this project, the model is represented by the `SiweResult`, `DisconnectUserResult`, `ModalResult`, `SessionAuthenticateResult` classes. These classes are used to represent the application's data.

### View

The view is responsible for displaying data to the user and handling user interactions. In this project, the views are represented by Compose components such as `SignInScreen` and `HomeScreen`. These screens use `StateFlow` to observe state changes and update the user interface accordingly.

### ViewModel

The ViewModel acts as a bridge between the model and the view, exposing data observably to the view. In this project, the ViewModels are represented by the `SignInViewModel` and `HomeViewModel` classes. These classes use `Flow` to handle asynchronous operations and `MutableStateFlow` to represent and update the user interface state.

### Usecases

The use cases, such as `GetModalEvents` or `GetUserSession`, encapsulate the business logic of the application. They are used by the ViewModels to interact with the application's data.

## Library Choices

### Hilt

Hilt is used for dependency injection management in the application. It simplifies dependency injection by providing predefined dependency containers that follow the application lifecycle.

### Compose

Compose is used to build the application's user interface. It offers a declarative approach to building user interfaces, making the code more concise and easier to read.

### Coroutine

Coroutines are used to manage asynchronous operations in the application. They allow writing asynchronous code sequentially, making the code easier to understand and maintain.

### Flow and MutableStateFlow

`Flow` is used to handle asynchronous operations, and `MutableStateFlow` is used to represent and update the user interface state. Together, they enable efficient management of asynchronous operations and user interface state in the application.
