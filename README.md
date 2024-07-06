# <img src="https://github.com/AdamDawi/Weather-App/assets/49430055/7737e2f0-9d86-4843-83d5-581ea0240725" width="60" height="60" align="center" /> Weather App

Discover detailed and accurate weather forecasts with Weather App. Utilizing advanced location services and real-time weather data, Weather App ensures you are always informed about the weather conditions in your area.

## ⭐️ Features

### Main Screen
- **Current Weather:** Displays the current weather, temperature, and an icon representing the weather condition.
- **Weather Forecast:** Shows a 9-day weather forecast, including the past 2 days and the next 6 days, with maximum and minimum temperatures and weather icons.
- **Location-Based Updates:** Automatically fetches weather data based on your current location.
- **Live Updates:** Updates weather data every 5 seconds using Kotlin's Flow and Coroutines. The updates pause when the app is in the background to save resources.
- **City Display:** Shows the city name based on your coordinates.

### Weather Details
- **Temperature Chart:** Beautiful chart displaying the maximum temperature and temperature changes over the past 7 hours, created using Jetpack Compose Canvas.
- **Current Conditions:** Displays the current temperature alongside the chart.
- **Additional Data Cards:**
  - **Rain:** Displays current rainfall data with an icon.
  - **Wind Speed:** Shows current wind speed data with an icon.
  - **Humidity:** Displays current humidity levels with an icon.
  - **Cloud Cover:** Shows current cloud cover with an icon.
- **Sunrise and Sunset:** A detailed card showing sunrise and sunset times and the current position of the sun on an arc, created using Jetpack Compose Canvas.

### Theming
- **Theme Switcher:** Toggle between dark mode and light mode using MaterialTheme.

## ⚙️ Technologies

### 📱 App:
- **Kotlin:** Primary language for app development.
- **Jetpack Compose:** For building responsive and modern UI.
- **Flow and Coroutines:** For handling real-time data updates and background tasks.
- **MVVM Clean Architecture:** Separates the project into layers with use cases, repositories, and view models.
- **Retrofit2:** For fetching weather data from the API.
- **Dagger Hilt:** For dependency injection.
- **Timber:** For logging.
- **Material Design:** For creating an intuitive and visually appealing interface.

### ✅ Testing:
- **JUnit:** For unit testing.
- **Mockito:** For mocking dependencies in tests.
- **Coroutines Test:** For testing coroutines.
- **Fake Repositories and APIs:** For testing.
- **Dagger Hilt:** For dependency injection in tests.
- **Compose UI Testing:** For UI and end-to-end testing.

## Installation

1. Clone the repository:
```bash
git clone https://github.com/AdamDawi/Weather-App
```
2. Open the project in Android Studio.
3. Be sure the versions in gradle are same as on github

## Here are some overview pictures:
| Light mode  | Dark mode |
| ------------- | ------------- |
| ![33](https://github.com/AdamDawi/Weather-App/assets/49430055/33dc8694-c3c6-40ce-8682-fd85e6b51838)  | ![11](https://github.com/AdamDawi/Weather-App/assets/49430055/cdfe9f56-69b0-4952-9255-1c27ccc663fb)  |
| ![44](https://github.com/AdamDawi/Weather-App/assets/49430055/de1c424b-319c-4582-baf6-886ff18d5508)  | ![22](https://github.com/AdamDawi/Weather-App/assets/49430055/da258e0e-5b3a-466d-a2d6-3a09a94979d8)  |


## Requirements
Minimum Version: Android 8.0 (API level 26) or later📱

Target Version: Android 14 (API level 34) or later📱

## Author

Adam Dawidziuk🧑‍💻
