public class User {
    private String fullName, email, password;
    private static String date;
    private static String username;

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
}