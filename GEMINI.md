# Android Minesweeper

An Android-based clone of the classic Minesweeper game.

## Project Overview

*   **Purpose:** A mobile implementation of Minesweeper featuring classic gameplay, difficulty levels, and score tracking.
*   **Technologies:**
    *   **Language:** Java
    *   **Platform:** Android (Min SDK: 21, Target SDK: 33)
    *   **Build System:** Gradle
    *   **UI Framework:** Standard Android Views
*   **Architecture:** Follows the standard Android project structure. The goal is to separate game logic from the UI components.

## Building and Running

Ensure you have the Android SDK and a compatible JDK installed.

*   **Build Project:**
    ```bash
    ./gradlew assembleDebug
    ```
*   **Install on Device/Emulator:**
    ```bash
    ./gradlew installDebug
    ```
*   **Run Lint:**
    ```bash
    ./gradlew lint
    ```
*   **Clean Build:**
    ```bash
    ./gradlew clean
    ```

## Development Conventions

*   **Structure:**
    *   `src/main/java`: Java source code.
    *   `src/main/res`: Android resources (layouts, strings, etc.).
    *   `doc/`: Project documentation, including `specification.md` and `todo.md`.
    *   `build.gradle`: Project-level build configuration.
    *   `settings.gradle`: Project settings and repository management.
    *   `gradle.properties`: Project-wide Gradle settings (AndroidX, memory, etc.).
*   **Coding Style:** Follow standard Java and Android development best practices.
*   **Documentation:** Refer to `doc/specification.md` for detailed game requirements and `doc/todo.md` for the current development roadmap.

## Project Status

The project is currently in the initial setup phase:
*   [x] Project skeleton created.
*   [x] Basic "Hello World" activity implemented.
*   [x] Specifications and TODO list drafted.
*   [ ] Game board UI design (Next Step).
*   [ ] Core game logic implementation (Pending).
