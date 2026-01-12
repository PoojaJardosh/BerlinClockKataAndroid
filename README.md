# Berlin Clock Android App ⏰

A modern Android application that converts 24-hour digital time into the **Berlin Clock (Mengenlehreuhr)** format. Built with **Kotlin**, **Jetpack Compose**, and **Clean Architecture** principles.

## 📱 Features

* **Time Conversion:** Converts inputs like `12:56:01` into the correct Berlin Clock lamp representation in string.
* **Input Validation:** Ensures strict adherence to the 24-hour `HH:mm:ss` format.
* **State Management:** Handles UI states (Initial, Loading, Success, Error) reactively.
* **Configuration Handling:** Preserves user input across screen rotations.
* **UX Enhancements:** Auto-dismisses keyboard on action; scrollable output for smaller screens.

## 🛠 Tech Stack & Architecture

This project follows the **MVVM (Model-View-ViewModel)** architectural pattern and utilizes modern Android development best practices.

* **Language:** [Kotlin](https://kotlinlang.org/)
* **UI Toolkit:** [Jetpack Compose](https://developer.android.com/jetpack/compose)
* **Dependency Injection:** [Hilt](https://dagger.dev/hilt/)
* **Concurrency:** [Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) & [Flow](https://kotlinlang.org/docs/flow.html)
* **Testing:**
    * **TDD Approach** for business logic.
    * **JUnit 4** & **Mockk** for unit testing.

## 📂 Project Structure

```text
com.example.berlinclock
├── di                  // Hilt Dependency Injection Modules
├── domain              // Business Logic (Pure Kotlin)
│   ├── BerlinClockValidator.kt
│   └── BerlinClockConverter.kt
└── ui                  // Presentation Layer (Compose + MVVM)
    └── clock
        ├── BerlinClockActivity.kt
        ├── BerlinClockScreen.kt
        ├── BerlinClockViewModel.kt
        └── BerlinClockUiState.kt

## 🧪 Logic Overview
The Berlin Clock logic is separated into two key domain classes:

Validator: Verifies if the input string matches the HH:mm:ss regex pattern and logical time bounds (e.g., hours 00-23).

Converter: Parses the time and generates the 5-row string representation:

Row 1 (Seconds): Yellow lamp blinks every 2 seconds.

Row 2 (5 Hours): Red lamps for every 5 hours.

Row 3 (1 Hour): Red lamps for every 1 hour remainder.

Row 4 (5 Minutes): Yellow/Red lamps for every 5 minutes.

Row 5 (1 Minute): Yellow lamps for every 1 minute remainder.

## 🚀 How to Run
Clone the repository:

Bash

git clone [https://github.com/PoojaJardosh/BerlinClockKataAndroid.git](https://github.com/PoojaJardosh/BerlinClockKataAndroid)
Open the project in Android Studio.

Sync Gradle files.

Run on an Emulator or Physical Device.

Developed by Pooja Jardosh
