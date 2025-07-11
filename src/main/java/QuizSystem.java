import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import java.io.*;
import java.util.*;

public class QuizSystem {
    static final String USERS_FILE = "users.json";
    static final String QUIZ_FILE = "quiz.json";
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            JSONArray users = loadUsers();

            System.out.print("Enter your username: ");
            String uname = sc.nextLine().trim();
            System.out.print("Enter your password: ");
            String pwd = sc.nextLine().trim();

            JSONObject loggedUser = null;
            for (Object obj : users) {
                JSONObject user = (JSONObject) obj;
                String username = (String) user.get("username");
                String password = (String) user.get("password");

                if (username.equals(uname) && password.equals(pwd)) {
                    loggedUser = user;
                    break;
                }
            }

            if (loggedUser == null) {
                System.out.println("Invalid credentials.");
                return;
            }

            String role = (String) loggedUser.get("role");
            if ("admin".equalsIgnoreCase(role)) {
                runAdmin();
            } else {
                runStudent();
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    static void runAdmin() throws Exception {
        System.out.println("Welcome admin! Please create new questions in the question bank.");
        JSONArray questions = loadQuiz();

        while (true) {
            System.out.print("Press 's' to start adding questions, or 'q' to quit: ");
            String input = sc.nextLine();
            if (input.equalsIgnoreCase("q")) break;

            JSONObject question = new JSONObject();
            System.out.print("Enter question: ");
            question.put("question", sc.nextLine());

            for (int i = 1; i <= 4; i++) {
                System.out.print("Enter option " + i + ": ");
                question.put("option " + i, sc.nextLine());
            }

            System.out.print("Enter answer key (1-4): ");
            question.put("answerkey", Integer.parseInt(sc.nextLine()));

            questions.add(question);
            saveQuiz(questions);
            System.out.println(" Question saved successfully!");
        }
    }

    static void runStudent() throws Exception {
        JSONArray questions = loadQuiz();
        if (questions.size() < 10) {
            System.out.println(" Not enough questions (need at least 10).");
            return;
        }

        System.out.println(" Welcome to the quiz! Each MCQ is 1 mark, no negative marking.Are you ready?");
        System.out.print("Press 's' to start: ");
        if (!sc.nextLine().equalsIgnoreCase("s")) return;

        Collections.shuffle(questions);
        int score = 0;

        for (int i = 0; i < 10; i++) {
            JSONObject q = (JSONObject) questions.get(i);
            System.out.println("\n[" + (i + 1) + "] " + q.get("question"));
            for (int j = 1; j <= 4; j++) {
                System.out.println(j + ". " + q.get("option " + j));
            }

            System.out.print("Your answer: ");
            int answer = Integer.parseInt(sc.nextLine());
            if (answer == ((Long) q.get("answerkey")).intValue()) {
                score++;
            }
        }

        System.out.println("\n🏁 Your Score: " + score + " out of 10");
        if (score >= 8) {
            System.out.println(" Excellent! You have got " + score + " out of 10");
        } else if (score >= 5) {
            System.out.println(" Good. You have got " + score + " out of 10");
        } else if (score >= 3) {
            System.out.println(" Very poor! You have got " + score + " out of 10");
        } else {
            System.out.println(" Very sorry, you are failed. You have got " + score + " out of 10");
        }

        System.out.print("Start again? (s/q): ");
        if (sc.nextLine().equalsIgnoreCase("s")) runStudent();
    }

    // Load users.json from resources (for login)
    static JSONArray loadUsers() throws Exception {
        InputStream input = QuizSystem.class.getClassLoader().getResourceAsStream(USERS_FILE);
        if (input == null) {
            System.err.println("⚠ File not found in resources: " + USERS_FILE);
            return new JSONArray();
        }
        return (JSONArray) new JSONParser().parse(new InputStreamReader(input));
    }

    // Load quiz.json from external location (modifiable)
    static JSONArray loadQuiz() throws Exception {
        File file = new File(QUIZ_FILE);
        if (!file.exists() || file.length() == 0) return new JSONArray();
        FileReader reader = new FileReader(file);
        return (JSONArray) new JSONParser().parse(reader);
    }

    // Save quiz.json (external modifiable file)
    static void saveQuiz(JSONArray array) throws Exception {
        FileWriter writer = new FileWriter(QUIZ_FILE);
        writer.write(array.toJSONString());
        writer.flush();
        writer.close();
    }
}
