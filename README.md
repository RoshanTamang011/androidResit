This is my final assignment for the module Android Application which is an ecommerce application
where you can buy gadgets such as mobile phones, laptops, desktops, cameras etc.
The programming language used for the development is Kotlin and for the backend API, Node.js
is used. The data is stored in NoSQL database MongoDB.

# Run
- First run the backend api using the command: node app
- Install all the dependencies in the backend using npm install command
- Then run the  android application in android studio.

# Features of the application
- Responsive design in different screen sizes.
- Use of shared preferences so that user do not need to login again and again.
- Form validation in both register and login page.
- Google map integration.
- Connection with wear os.
- Login and dashboard in Wear OS.


# Features available for customer
- Register and login feature.
- See the information about the product.
- Add and delete product from the cart.
- Add and delete product from the wishlist.
- User can edit their profile.
- Feature to add profile picture.
- Google map integration.
- Logout feature for user.


# Room Database
The data is fetched from API and stored in the Room database. Then the data is retrieved from the
room database and shown in the application using the recycler view.

# Testing
Two different types of testing has been done in the application i.e.
Unit testing and instrumented testing

# Sensors
I have included 3 different sensors in the application.
- Gyroscope: When ever user tilts their phone in X or Y axis, the fragments changes accordingly.
- Acclerometer : When the user shakes their mobile phone, the user will be logged out.
- Proximity: When any object is near the sensor, the user will be logged out.

# Wear OS
In the wear os, user can login and view their dashboard.
In the dashboard, user can see their number of cart items.

# Mobile and wear os connection
As soon as the user logs in, a notification is sent to wear os too.

# Responsive Design
The design was tested in screen sizes. In the tablet view, all the design are responsive.



