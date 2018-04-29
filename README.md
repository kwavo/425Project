# 425Project

This is our app, Quizop!

This is an app that tests your knowledge in various categories. When you open the app, you are presented with a screen to login or sign up. You can sign up with any email and password as long as the password is greater than 6 characters. Once you sign up or login, you are greeted with a welcome screen which is where you can pick a category to quiz yourself on. General knowledge is a compilation of all the categories. Once you start the quiz, you have 10 questions and at the end you are presented with a leaderboard sorted by top score. 

The questions were pulled from an API called openTableDB (https://opentdb.com/api_config.php). This API did not require an API key. We had to parse the JSON data that was returned when you call this API. This required researching JSON objects and how to work with them which we did a little bit in class. 

The leaderboard was created using Firebase. We have used Firebase before and it was easy to implement the user authentication using Firebase's tools. For the leaderboard, we used the substring before the '@' as the username. Firebase setup comes from https://www.sitepoint.com/creating-a-cloud-backend-for-your-android-app-using-firebase/

There are some things we want to improve on in the future such as a redesign of the leaderboard that includes functions like sorting or searching. We also want to implement new game modes such as a timed game mode and some sort of head to head mode. We would also like to add more parameters when started a quiz such as specifying how many questions you want to answer or what kinds of questions (multiple choice vs true/false, etc).

All of the testing was done on either a Pixel 2XL or Nexus 6P which were both running Android 8.1 (API 27).

Created by: 
Jonathan Kwak (kwak12@purdue.edu)
Myles Robinson (robin249@purdue.edu)