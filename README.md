# Goodnight - Sleep Apnia Detection App

## Inspiration
The triangle challenge for change is an unprecedented category which really appealed to us. We decided to pursue the Health Challenge by McMaster University as Sleep Apnea is an issue which affects a significant number of individuals around the world. Statistically, approximately 20-30% of men and 10-15% of women around the world are affected by this sleep disorder. The most shocking aspect of this statistic is that a majority of individuals living with sleep apnea are unaware that they possess this order, and hence cannot seek proper treatment.

## What it does
Our solution to this problem was an android application that is able to track the breathing rate of an individual over an unknown period of time. While the individual's sleep is being recorded, the app is able to process the data of the individual's sleep to determine the number apneic episodes. Based on the number of apneic episodes, the individual can be classified as being healthy or having mild, moderate, or severe sleep apnea. The Android application also keeps track of the individual's movements and informs the user if whether the movement within their sleep as low, medium, and high.

## How we built it
The entire app was built on Android Studio. In order to make the app compatible with the phone's microphone, Android's MediaRecorder API was implemented. In order to ensure the MediaRecorder API was reading in a WAV file, the Android Audio Converter Library was used to convert .AAC file to a .WAV file. Using the results from the test dataset provided by Dr. Om Bhatt of McMaster University, a classification algorithm was written from scratch to diagnose an individual with sleep apnea. The front end of the application interfaced with backend using XML Layouts with modification to the Gradle build scripts.

In order to assist with the algorithm, Python's MatPlotLib and SciPy were used to model and visualize the data to provide us with a better understanding of the trends within the testing datasets.

## Challenges we ran into
