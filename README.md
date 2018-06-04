# Lafz
A simple voice to text transcription solution

**Lafz uses the [CMU PocketSphinx](https://cmusphinx.github.io/) (CMUPS) libraries to transcribe recorded voice into text. This is still a draft application nowhere near the level of daily usage**

### Functional Modules
* __Login system:__ Very simple register and login system which only uses server auth.
* __ID Generation:__ Basic ID generation system based on the login ID
* __Live Chat system:__ The main functionality of the app which uses CMUPS
  1. __Session:__ Uses CMUPS to transcribe live conversation using a Dictionary, LM file and PTM Acoustic Model
  2. __Comments:__ Uses CMUPS to transcribe a report using a Dictionary, Grammar file and PTM Acoustic Model
* __Summarization:__ Uses very simple keyword search to summarize the transcription and store it in the DB
* __Predefined:__ Used to add patient data manually or modify existing data in DB. Easy way to view entire DB

All audio files, transcriptions, and summarizations are stored in the Lafz folder in Internal Memory

### Team members:  
Burhanuddin Mustafa Lakdawala *(Team Leader)* @PyroGenesis  
Yash Tomar @TheNovaCore  
Rahul Gupta @guptarahul18  
Farhan Khan  
Arif Khan  
