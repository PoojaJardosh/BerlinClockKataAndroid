# Berlin Clock Android App ⏰

A modern Android application that converts 24-hour digital current time into the **Berlin Clock** format. Built with **Kotlin**, **Jetpack Compose**, and **MVVM** principles.

<img width="200" height="500" alt="image" src="https://github.com/user-attachments/assets/ba10ac09-0ec3-4726-a516-0d943f365af5" />


The clock is read from the top row to the bottom. 

The top row - 4 lamps - Yellow - Blinks every second and indicate yellow for even seconds ; Gray - Off
The second row - 4 lamps - Red - Each lamp represents 5 Hours
The third row - 4 lamps - Red - Each lamp represents 1 Hour
The fourth row - 11 lamps - Red (Every third lamp) , Yellow (Rest lamps) - Each lamp represents five minutes
THr fifth row - 4 lamps - Yellow - Each lamp represents one minute


# Logic Overview
The Berlin Clock logic is separated into two key domain classes:
The core logic is handled by the `BerlinClockConverter` in the domain layer, which transforms `LocalTime` into a structured `BerlinClockState`.
​
 ### 1. Time Calculation
 *   **Seconds:** `seconds % 2 == 0` determines the blinking state of the top yellow lamp.
 *   **Hours:** 
     *   **5-Hour Row:** `hours / 5` gives the number of lit red lamps.
     *   **1-Hour Row:** `hours % 5` gives the number of lit red lamps in the second row.
 *   **Minutes:**
     *   **5-Minute Row:** `minutes / 5` gives the total lit lamps. Every 3rd lamp (index 3, 6, 9) is colored **Red** to indicate quarters (15, 30, 45 mins), while others are **Yellow**.
     *   **1-Minute Row:** `minutes % 5` gives the number of lit yellow lamps in the final row.
​
 ### 2. Reactive Updates
 The `BerlinClockViewModel` maintains a `StateFlow` that emits a new `BerlinClockState` every second. It uses `viewModelScope` and a coroutine loop to fetch the current time from `TimeProvider` and update the UI state continuously.

## 📱 Features

*   **Live Clock Updates:** Synchronizes with the system time to provide real-time updates every second.
*   **Modern Declarative UI:** Built entirely with **Jetpack Compose**, ensuring a smooth and reactive user experience.
*   **Clean Architecture:** Follows a strict separation of concerns (Domain, UI, Presentation) for better maintainability and testability.
*   **Reactive State Management:** Utilizes `StateFlow` and Coroutines to push time updates from the ViewModel to the UI layer efficiently.
*   **Accurate Color Representation:** Implements the classic Berlin Clock color scheme (Yellow/Red) with specific logic for quarter-hour indicators.
*   **High Testability:** Business logic is decoupled from the Android framework, allowing for comprehensive Unit Testing using TDD.

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
└── data   
     └── RealTimeProviderImpl.kt


## 🚀 How to Run
Clone the repository:

Bash

git clone [https://github.com/PoojaJardosh/BerlinClockKataAndroid.git](https://github.com/PoojaJardosh/BerlinClockKataAndroid)
Open the project in Android Studio.

Sync Gradle files.

Run on an Emulator or Physical Device.

Developed by Pooja Jardosh
