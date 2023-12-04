[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-24ddc0f5d75046c5622901739e7c5dd533143b0c8e959d652212380cedb1ea36.svg)](https://classroom.github.com/a/DC1SF4uZ)
# Homework 6 - Responding to Change

## Authors
1) Matthew Galitz, ykk6rh, galitz-matt
2) Dor Frechter, huz4kk, dorfrechter

## To Run
1) Install JavaFX SDK (version 17.0.9)
2) Add VM arguments below to run configuration in MainApplication.java:<br>
<code>--module-path "C:\path\to\javafx-sdk-17.0.9\lib" --add-modules javafx.controls,javafx.fxml</code>
3) Run MainApplication.main()

## Contributions

List the primary contributions of each author. It is recommended to update this with your contributions after each coding session.:

### Matthew Galitz

* Developed back-end of app
  * Business logic, controllers, services

### Dor Frechter

* Developed database driver and the front end of the app
  * FXML files and style sheets

## Issues
We did not implement a search button for the course search screen,
instead we had 3 text fields that act as filters which automatically
updates the ListView of courses each time the user enters a character. 
We felt this gave our application a more dynamic feel such that the
application immediately reacts to input. Also, all courses that currently
have reviews are shown when the filters are empty.
This allows users to conveniently view the available courses, and
reinforces the intuition of the filters. 