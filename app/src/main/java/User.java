public class User {
    private String fullName, email, password;
    private static String date;
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