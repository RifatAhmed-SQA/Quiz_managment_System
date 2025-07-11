# Quiz-Management-System

## Project Summary:
This is a simple role-based **Quiz Management System** developed using **Java**. It supports two types of users: `admin` and `student`.

- The **admin** can add multiple-choice questions (MCQs) to a question bank.
- The **student** can take a 10-question quiz based on those MCQs.

The system uses JSON files for data persistence:
- `users.json`: stores user credentials and roles.
- `quiz.json`: stores all quiz questions and answer keys.

---

## Technologies Used
- Java
- JSON.simple
- IntelliJ IDEA 
- Gradle 
- Console-based I/O

---

## Features

### Admin
- Login with username/password.
- Add MCQs with 4 options and an answer key.
- Save new questions to `quiz.json`.

### Student
- Login with student credentials.
- Take a 10-question random quiz.
- Score calculation with feedback:
  - 8–10: Excellent
  - 5–7: Good
  - 3–4: Very Poor
  - 0–2: Failed

---

## Project Video Demo
https://github.com/user-attachments/assets/6578b22d-c214-4453-9668-4aa3b1ad9fa1



