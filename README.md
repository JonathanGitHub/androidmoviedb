androidmoviedb


Pitfalls to avoid:
* Memory Leaks for Async API calls
* Context vs ApplicationContext
* Parsing Json in Non-UI Thread


Features to include in MVP:

* Endless scrolling/API Pager = X
* Universal Status Bar/Toolbar for different actitivies
* CoordinatorLayout for MovieDetail Page


Post MVP:
* Use smooth Activity UI transition
* Use coordinatorLayout
* Use EventBus or RxJava to asynchronously refresh UI when data changed in other parts of the app
* Use Fragment to replace activities
* Use ViewPager/Fragment so that users can swipe MovieDetails Page
* Use Retrofit, replace Ion
* Use MVP to replace MVC
* Use customized views instead of plain xml(boiler plate)
* Use RxJava
* Offline support using Realm library