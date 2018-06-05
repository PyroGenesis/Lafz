# Lafz
A simple server based voice to text transcription solution

**Lafz uses the [CMU Sphinx](https://cmusphinx.github.io/) (CMUS) libraries to transcribe recorded voice into text. This is still a draft application nowhere near the level of daily usage**

### Functional Modules
* __Login system:__ A login system which uses server authentication for the first time the user logs in and saves the credentials of all the users in the applications SQLite database which can be used for authentication when the user is offline. Also the user does not have any registration rights .i.e. new IDs can only be registered from server side.
* __ID Generation:__ Basic ID generation system based on the login ID and patient number.
* __Live Chat system:__ The main functionality of the app which uses CMUS. Here the user records the conversation in .wav format and it is stored on his/her device.
* __File Chooser:__ It lets the user choose the audio file they wish to upload from the device storage to be transcribed and translated.
* __Result Page:__ It waits for the response from the server and displays the transcription and translation results on the application.
* __Predefined:__ Used to add patient data manually to the server and SQLite DB or modify existing data. The data can neither be added nor edited if the user is offline.

All audio files, transcriptions, and translations are stored on the server.
All audio files are also stored in the Lafz folder in Internal Memory

### Team members:  
Burhanuddin Mustafa Lakdawala *(Team Leader)* @PyroGenesis  
Yash Tomar @TheNovaCore  
Rahul Gupta @guptarahul18  
Farhan Khan  
Arif Khan  
