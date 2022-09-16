# Goodnight - Sleep Apnia Detection App

## Inspiration
The triangle challenge for change is an **_unprecedented category_** which really appealed to us. We decided to pursue the **Health Challenge by McMaster University** as **Sleep Apnea** is an issue which affects a _significant_ number of individuals around the world. Statistically, approximately 20-30% of men and 10-15% of women around the world are affected by this sleep disorder. The most shocking aspect of this statistic is that a majority of individuals living with sleep apnea are **unaware** that they possess this order, and hence cannot seek proper treatment.

## What it does
Our solution to this problem was an android application that is able to track the **breathing rate** of an individual over a period of time. While the individual's sleep is being recorded, the app is able to process the data of the individual's sleep to determine the number **apneic episodes**. Based on the number of apneic episodes, the individual can be classified as being healthy or having mild, moderate, or severe sleep apnea. The Android application also keeps track of the individual's **movements** and informs the user if whether the movement within their sleep as low, medium, and high. ![alt text](./MainScreen.PNG)

## How we built it
The entire app was built on **Android Studio**. In order to make the app compatible with the phone's microphone, Android's **MediaRecorder API** was implemented. In order to ensure the MediaRecorder API was reading in a WAV file, the Android Audio Converter Library was used to convert **.AAC files** to **.WAV files**. Using the results from the test dataset provided by Dr. Om Bhatt of McMaster University, a **classification algorithm** was written from scratch to diagnose an individual with sleep apnea. The front end of the application interfaced with backend using XML Layouts with modification to the Gradle build scripts.

In order to assist with the algorithm, Python's **MatPlotLib** and **SciPy** were used to model and visualize the data to provide us with a better understanding of the trends within the testing datasets.

## Challenges we ran into
The biggest challenge we ran into was working with a .WAV file to analyze the data. As it is significantly difficult to **quantify** a .WAV file, being able to find the tools to crack the file was a tough challenge, which nevertheless, was overcome. 

Another significant challenge we encountered was **downsizing** the data. After quantifying the data, we were provided with approximately **44100 data points** of the amplitude within a single second. Hence parsing through a file with over a million data points was extremely challenging and once again, was a challenge that was overcome and is reflected within the final product. 

## Accomplishments that we're proud of
The biggest accomplishment for us was successfully building an app within 24 hours. Moreover, the app that we built has a significant practical application and has the potential to make a difference in the world.

## What we learned
As two of the team members attended their first hackathon, this was a great learning experience for them. Namely, both individuals were exposed to using **Git** as a version control system. Furthermore, both individuals got experience developing software in a **practical setting** and applying it to a **real-world setting**. 

For the remaining two members, this was their first hackathon in which they were solving a problem within the field of medicine. For this reason, the entire team was exposed to approaching problems from a **biomedical perspective**. Moreover, the entire team obtained essential experience using Android Studio as well as performing research to make the software development process more feasible. 

## What's next for Goodnight - Sleep Apnea Detection App
The next steps to further increase the functionality of the Goodnight app is to integrate the **muse headband** for tracking the brain signals of the user in order to predict their sleeping patterns. This can help detect when, and how long the user experiences **REM** sleep, which is when they get the most rest. Tracking this data gives the user information on the quality of their sleep and how much rest they are getting each night. **Machine learning** can also be integrated into this design to minimize discrepancies in the data, giving more accurate results.
 
Furthermore, data from a **Fitbit** can be integrated to raise the accuracy of our sleep apnea detector app by collecting information from its inbuilt sensors. Fitbit plans to feature a **blood oxygen sensor** in its newer models in the near future, which can prove to be quite beneficial. Determining the levels of oxygen in the blood gives us an idea of the users breathing pattern, normal levels meaning they are still breathing, and low levels meaning they have paused. 
