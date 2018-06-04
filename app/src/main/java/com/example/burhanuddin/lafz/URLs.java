package com.example.burhanuddin.lafz;

public class URLs {
    public static String ROOT_IP;

    public static String URL_LOGIN;
    public static String URL_SEND_TRIM1_INFO;
    public static String URL_SEND_TRIM2_INFO;
    public static String URL_SEND_TRIM3_INFO;
    public static String URL_SEND_AUDIO_FILES;
    public static String URL_GET_LOGIN_DETAILS;
    public static String URL_GET_TRIM1_DETAILS;
    public static String URL_GET_TRIM2_DETAILS;
    public static String URL_GET_TRIM3_DETAILS;
    public static String URL_GET_TRANSCRIPTION;
    public static String URL_GET_TRANSLATION;

    public static void setURLs()
    {
        URL_LOGIN = "http://" + ROOT_IP + "/Lafz_Server/checkUser.php";
        URL_SEND_TRIM1_INFO = "http://" + ROOT_IP + "/Lafz_Server/Data_PHP_Files/sendTrim1Info.php";
        URL_SEND_TRIM2_INFO = "http://" + ROOT_IP + "/Lafz_Server/Data_PHP_Files/sendTrim2Info.php";
        URL_SEND_TRIM3_INFO = "http://" + ROOT_IP + "/Lafz_Server/Data_PHP_Files/sendTrim3Info.php";
        URL_SEND_AUDIO_FILES = "http://" + ROOT_IP + "/Lafz_Server/audio_server/upload.php";
        URL_GET_LOGIN_DETAILS = "http://" + ROOT_IP + "/Lafz_Server/Admin_Screens/user_details.php";
        URL_GET_TRIM1_DETAILS = "http://" + ROOT_IP + "/Lafz_Server/Data_PHP_Files/getTrim1Info.php";
        URL_GET_TRIM2_DETAILS = "http://" + ROOT_IP + "/Lafz_Server/Data_PHP_Files/getTrim2Info.php";
        URL_GET_TRIM3_DETAILS = "http://" + ROOT_IP + "/Lafz_Server/Data_PHP_Files/getTrim3Info.php";
        URL_GET_TRANSCRIPTION = "http://" + ROOT_IP + "/Lafz_Server/audio_server/transcription.php";
        URL_GET_TRANSLATION = "http://" + ROOT_IP + "/Lafz_Server/audio_server/translation.php";
    }

}