package com.example.healthfuelness;

public class User {
    private String fullName, email, password;
    private static String date, date1, date2, date3, date4, date5, date6;
    private static String username;
    private static int currentDateOrNot; // -1: previous; 0: current; 1: future

    public User(String fullName, String email, String password) {
        this.email = email;
        this.fullName = fullName;
        this.password = password;
    }

    public static void setDate(String date) {
        User.date = date;
    }
    public static String getDate() {
        return date;
    }

    public static void setDate1(String date) {
        User.date1 = date;
    }
    public static String getDate1() {
        return date1;
    }

    public static void setDate2(String date) {
        User.date2 = date;
    }
    public static String getDate2() {
        return date2;
    }

    public static void setDate3(String date) {
        User.date3 = date;
    }

    public static String getDate3() {
        return date3;
    }

    public static void setDate4(String date) {
        User.date4 = date;
    }

    public static String getDate4() {
        return date4;
    }

    public static void setDate5(String date) {
        User.date5 = date;
    }

    public static String getDate5() {
        return date5;
    }

    public static void setDate6(String date) {
        User.date6 = date;
    }

    public static String getDate6() {
        return date6;
    }

    public static void setUsername(String username) {
        User.username = username;
    }

    public static String getUsername() {
        return username;
    }

    public static void setCurrentDateOrNot(int currentDateOrNot) {
        User.currentDateOrNot = currentDateOrNot;
    }

    public static int getCurrentDateOrNot() {
        return currentDateOrNot;
    }
}