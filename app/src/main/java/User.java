public class User {
    private String fullName, email, password;
    private static int age;
    private static int height;
    private static int weight;
    private static String date;
    private static String username;
    private static int currentDateOrNot; // -1: previous; 0: current; 1: future

    public User(String fullName, int age, int height, int weight, String email, String password) {
        this.email = email;
        this.fullName = fullName;
        this.password = password;
        this.age = age;
        this.height = height;
        this.weight = weight;
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

    //public void setAge(int age) {
       // User.age = age;
   // }

   // public static int getAge() {
      //  return age;
   // }

    //public void setHeight(int height) {
      //  User.height = height;
    //}

   // public static int getHeight() {
       // return height;
    //}

   // public void setWeight(int weight) {
        //User.weight = weight;
   // }

   // public static int getWeight() {
        //return weight;
    //}

    public static void setCurrentDateOrNot(int currentDateOrNot) {
        User.currentDateOrNot = currentDateOrNot;
    }

    public static int getCurrentDateOrNot() {
        return currentDateOrNot;
    }
}