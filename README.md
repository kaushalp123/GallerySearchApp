# Cab9 Driver App V3 Android
### GALLERY APP WHICH DISPLAYS TOP IMAGES OF THE WEEK FROM THE IMGUR GALLERY BASED ON USER SEARCH

### Code Language
This project is developed in native android using Kotlin language.

### Design Support
This app uses the androidX design support libraries and android Jetpack libraries as native libraries

### Architecture Design Pattern
I have used **Model-View-ViewModel(MVVM)** pattern for building this app.  
The reason i chose MVVM over other patterns is it provides an easy way to structure the project codes. 
MVVM is widely accepted design pattern in android and recommended by Google, since it provides modularity, testability, and a more clean and maintainable codebase.  

It is composed of the following three components:  
**Model:** It represents the data and the business logic of the Android Application. It consists of the business logic - local and remote data source, model classes, repository.  
**View:** UI layer(Activities and fragments). It provides the visualization of the data. It sends the user action to the ViewModel but does not get the response back directly. To get the response, it has to subscribe to the observables which ViewModel exposes to it. 
**ViewModel:** It is a bridge between the View and Model(business logic).It does not have any clue which View has to use it as it does not have a direct reference to the View.It interacts with the Model and exposes the observable that can be observed by the View.

### External Libraries used in this app

#Coroutines
Used to execute the block of code as a separate process by not blocking the current operations of the app.
I chose this way because its very simple to implement and the code is very concise and performance wise also it is very effective.

#Retrofit
Used to make the Rest API calls
Chose this library as i have used it in all my projects and very much familiar with it and also its very easy to implement and effective in handling different type of api api requests.

#moshi library
This library is used to parse the json api response into custom model classes this is replacement for Gson converter library. It is much faster and uses less memory than Gson and its perfectly suited for Kotlin.

#Glide
Glide is used to load the images into our image views(image loader) form the url and is recommended by Google.

#threetenbp
ThreeTen-Backport provides a backport of the Java SE 8 date-time classes to Java SE 6 and 7. I have used this library to convert the time which is long format to our required format with local time zone.

#App Flow
I have followed the single activity multiple fragment approach in this project, the search bar and the search button is in a main activity and the grid view and list view display of the images 
are inside the respective fragments. The api call is made using coroutines inside the main activity and the state flow inside the view model gets the result and the two fragments uses observables which collects the result and display the results on the UI.

