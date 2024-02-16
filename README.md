# Vacation Planner Android App

This project was an opportunity to introduce me to programming for android mobile devices using a software development kit (SDK). In this course, I learned how to install and utilize an SDK, build a basic android mobile application using a graphical user interface (GUI), adapt applications to different mobile devices, save data, execute and debug mobile applications using emulators, and deploy an android mobile application.

Through it, I was able to:

* Build a planner to schedule custom vacations with associated excursions, validation, and CRUD functionality
* Develop functionality for user experience by creating date scheduling, alerts, and sharing capabilities
* Write a user guide and signed APK in order to deploy app to the Google Play store

## BASIC INSTRUCTIONS

CLone the project:
`git clone https://github.com/saksben/d308-mobile-application-development.git`

Open in IDE (built for Android Studio: Flamingo)

Run the program in an android emulator through Android Studio or through a wired/wireless connection on a physical android device

## USE INSTRUCTIONS

From the home screen, click on the `Vacation Planner` button to reach the Vacation List page, where any added vacations will populate

To add sample data (if you don't want to enter vacations), click the three dots at the top right corner of the menu bar, and select `Add Sample Data`. Navigate back to the Home Page by clicking the back button and then click the `Vacation Planner` button again to reach the Vacation List page where the sample data will be displayed (will be updated to refresh in the future)

To add a vacation, click the floating action button with a `+` sign in the bottom right corner of the Vacation List page. This will bring you to the Vacation Details page.
From here, add the vacation title, hotel name, start date, and end date. Save the data to the database by clicking the three dots in the menu bar, then select `Save Vacation`.
All associated excursions will be listed under the vacation information.
To set an alert, click the three dots in the menu bar and select `Alert Start`, `Alert End`, or `Alert Full` to set a notification that displays on the vacation's start date, end date, or both.
To share all of the vacation's information (including excursion data), click the three dots in the menu bar and select `Share`, then select which sharing method you would like to use.

If you want to update the vacation information, start at the Vacation List page and select the desired vacation. Update the information desired. Click the three dots in the menu bar, then select `Save Vacation`.

To delete a vacation, start at the Vacation List page and select teh desired vacation. Click the three dots in the menu bar, then select `Delete Vacation`.

Validation: a vacation's end date must be after its start date.

To add an excursion, start on the Vacation Details page of the desired vacation with which the excursion will be associated. Click the floating action button with a `+` sign in the bottom right corner.
This takes you to the Excursion Details page. From here, enter the excursion title and date. Save the data to the database by clicking the three dots in the menu bar, then select `Save Excursion`.
To add an alert, click the three dots in the menu bar and select `Set Alert` to set a notification that displays on the excursion's date.

Validation: an excursion's date must be set within its vacation's start and end dates.
