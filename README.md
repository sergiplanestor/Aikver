# Aikver

## Description - About Aikver
App to discover, store and share with your friends or family tv shows or movies! 
You will be able to find out new tv shows or related content from your favourite movies, all this within a fun user interface.

You might create your own groups and share it all with them, also you might recommend content directly to a person from group, set comments on content and much more!

## How have been made - About code

- **Language**:
This app have been developed totally in Kotlin.

- **Arquitecture**:
  Following the principles of Clean Code and using a MVVM pattern this app presents a modular and scalable arquitecture. You can find 5 layers splited in 2 modules:
  #### Module 1: App
  ____
  **Presentation layer:** where all UI components are placed like widgets, Activities / Fragment or ViewModels.
  
  **Framework layer:** all Android Framerworks implementation's are located here such as Firebase or Shared Preference data source.
  #### Module 2: Core:
  ____
  **Data layer:** here are placed all datasources, models from service (called entites) and the repository interface.
  
  **Domain layer:** all business logic such as repository interface implementation, models and mapper (from entity to domain model) are located in this layer.
  
  **Interactors layer:** this layer contains all use case used in app.
      
- **Dependency Injectors**:
The dependency injections have been resolved using Koin DLS. With it, all use case, viewModels and repositories have been injected.
- **External Libraries:**
  - Services:
  All network calls have been made using *Retrofit 2* and *OkHttp3*.
  - Images:
  In order to load, cache and display images have been used  *Glide 4.0*.
  - Animations:
  *Lottie* have been the library used to run animations and also have been used *Shimmer* in order to not block the UI when loading data on dashboard.
  
- **Insights:**
  In order to can get report of crashings and other errors, *Crashlytics* have been implemented.
