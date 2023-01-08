public class User {
    private String fullName, email, password;
    private static String selectedDescription;
    private static String selectedDate;
    private static int selectedImageId;

    public User(String fullName, String email, String password) {
        this.email = email;
        this.fullName = fullName;
        this.password = password;
    }

    public static void setSelectedDate(String selectedDate) {
        User.selectedDate = selectedDate;
    }

    public static int getSelectedImageId() {
        return selectedImageId;
    }

    public static void setSelectedDescription(String selectedDescription) {
        User.selectedDescription = selectedDescription;
    }

    public static String getSelectedDescription() {
        return selectedDescription;
    }

    public static void setSelectedImageId(int selectedImageId) {
        User.selectedImageId = selectedImageId;
    }

    public static String getSelectedDate() {
        return selectedDate;
    }
}