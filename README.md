# Aaira Station Food Ordering App

## What is this?

This is an app written for my university project (TME3413 22/23 : Software Engineering Lab) from
team ABCDE

## How to install the app?

This app is not in Google play, so other methods must be used to circumvent it

1. From apk

   The apk the app is included in [app/release.zip](app/release.zip)

   Since my apk signature is not certified by google, this app will be flagged by Google Play
   Protect. You'll have disable it when prompted.

2. From Android studio

   The app can be also install from the computer using a USB connection TL:DR use the *Debug via
   USB* feature to install the app from the computer

   For a video example, watch [this video](https://www.youtube.com/watch?v=UuLdD7oyML8)

    1. Connect the your phone to your computer via USB
    2. Enable *USB debugging* on your phone via developer settings
    3. When your phone shows up in the top of the screen, click on the play button to start
       debugging

   ![](README%20Images/Phone%20on%20Debug.png)

## How to navigate the codebase?

This codebase follows the MVVM architecture, and have lots of folders to group things together

- The *core* folder are for utility functions
- The *data* folder are for the data access objects, or repositories
- The *di* folder stores the Dagger-Hilt (a library) object for dependency Injection
- The *domain* folder are where the interfaces are contained
- The 3 features (*feature_menu, feature_order and feature_settings*) folder are where the main
  logic of the program lies
    - In each feature, there is another *domain* folder, which contains use cases and data classes
    - There is also a presentation folder where the UI logic resides