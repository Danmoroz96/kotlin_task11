# kotlin_task11
![Screenshot 2025-11-28 222012](https://github.com/user-attachments/assets/30b78cc8-7db9-4694-9273-ea917803f749)
![Screenshot 2025-11-28 222037](https://github.com/user-attachments/assets/ae7cff4e-d347-4da8-a59c-145b8c630bfa)
![Screenshot 2025-11-28 222102](https://github.com/user-attachments/assets/7ed78a15-0596-4fc2-bab5-014f260abc84)
Multi-Screen Navigation App (Task 11)

An Android application built with Kotlin and Jetpack Compose that demonstrates how to implement navigation between multiple screens and pass data (arguments) between them.

Overview

This app consists of three distinct screens that simulate a user flow:

Home Screen: Collects the user's name.

Second Screen: Collects two integer numbers.

Third Screen: Displays the user's name and the calculated sum of the two numbers.

Features

1. Navigation with Arguments

Uses androidx.navigation.compose to handle screen transitions.

Passes the User Name from Screen 1 to Screen 2.

Passes the User Name AND Two Numbers from Screen 2 to Screen 3.

2. Deep Linking / Routing

Route 1: home_screen

Route 2: second_screen/{name}

Route 3: third_screen/{name}/{num1}/{num2}

3. Back Stack Management

Back Navigation: Standard "Back" buttons pop the current screen off the stack.

Go Home: The final screen features a "Go Home" button that clears the back stack up to the start destination, preventing the user from hitting "Back" and seeing the input forms again.

Navigation Flow

[Home Screen] --(Name)--> [Second Screen] --(Name, Num1, Num2)--> [Third Screen]
      ^                            |                                      |
      |________________(Back)______|                                      |
      |                                                                   |
      |___________________________(Go Home)_______________________________|


Dependencies Used

androidx.navigation:navigation-compose:2.7.7 - The core library for Compose navigation.

androidx.compose.material3 - Material Design UI components.

How to Test

Screen 1: Enter your name (e.g., "Alice") and click Next.

Screen 2: You will see "Hello, Alice!". Enter two numbers (e.g., "10" and "5") and click Next.

Screen 3: You will see the result: "10 + 5 = 15".

Click Go Home to return to the start, or Back to change the numbers.
