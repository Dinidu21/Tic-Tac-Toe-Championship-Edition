# Tic-Tac-Toe Championship Edition

## 🤖 Prepare for a true challenge—our AI never misses! 💡 Think you've got what it takes to outsmart it in Tic-Tac-Toe? 🧠🔥

<p align="center"> <img src="src/main/resources/icons/game.png"> </p>

This is a Java-based implementation of the classic Tic-Tac-Toe game, enhanced with AI capabilities using the Minimax algorithm. The game allows players to play against the AI with two levels of difficulty—Easy and Hard.

## Table of Contents

- [Features](#features)
- [Tech Stack](#tech-stack)
- [AI Algorithm](#ai-algorithm)
- [Screenshots](#screenshots)
- [Installation](#installation)
- [Running the Project Locally](#running-the-project-locally)
- [How to Play](#how-to-play)
- [Folder Structure](#folder-structure)
- [Contributing](#contributing)
---

## Features

- Play Tic-Tac-Toe against AI
- Choose between two levels of difficulty (Easy and Hard)
- Player vs AI
- Uses the Minimax algorithm for optimal AI moves
- Supports Java 21 for modern JVM features
- Clean UI using JavaFX
- Comprehensive input validation for player names and moves

---

## Tech Stack

Here's how you can enhance your README with real icons for the technologies used:

-Java 21
-JavaFX for the User Interface
-Minimax Algorithm for AI logic
-Maven for project management
-Lombok for reducing boilerplate code

<center>
<img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/java/java-original-wordmark.svg" height="50" alt="Java" /><img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTvH0Z3P1LvoEGIc6XMgKB-o1Dg2_X7hZtXd7rrgtWMoy559R3fn_YWOdydsvydI61iLWE&usqp=CAU" height="50" alt="JavaFX" /><img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/apache/apache-original-wordmark.svg" height="50" alt="Maven" /><img src="https://github.com/user-attachments/assets/1b01a8c0-c845-4e99-a1fa-c5a187f11f95" height="50" alt="Lombok" />
</center>


---

## AI Algorithm

The AI uses the **Minimax algorithm**, which is a decision-making algorithm for two-player games. The AI maximizes its score and minimizes the player's score based on possible future moves. Here's a simple explanation of how it works:

### Algorithm Overview:

1. **Maximizing Player (AI):** The AI tries to maximize its score. It considers all possible moves, evaluates each one, and chooses the move that provides the best possible outcome.
2. **Minimizing Player (Human):** The human player minimizes the AI's score. The AI assumes the player will always play optimally, meaning it will choose the moves that make it harder for the AI to win.
3. **Base Case:** If there is a winner or a tie, the algorithm returns a score (e.g., +10 for AI win, -10 for AI loss, 0 for a tie).
4. **Recursion:** The algorithm evaluates every possible future state of the board.

---

## Screenshots

### 1. **Main Menu:**
![image](https://github.com/user-attachments/assets/05f5b717-2983-4f80-ac8e-eeffbe3e4d26)


### 2. **Gameplay (Player vs AI):**
![image](https://github.com/user-attachments/assets/dde6e251-8fdd-4a86-9235-08efa5b84bf2)



### 3. **Victory Screen:**
![image](https://github.com/user-attachments/assets/5c608fe6-5e22-4e35-9ea1-15b5fa5beca8)


---

## Installation

Follow the steps below to set up the project on your local machine.

### Prerequisites

Ensure that you have the following installed:
- **Java 21**: You can download the JDK from [here](https://www.oracle.com/java/technologies/javase-jdk21-downloads.html).
- **Maven**: For managing project dependencies. You can install Maven from [here](https://maven.apache.org/install.html).

### Clone the Repository

```bash
git clone https://github.com/Dinidu21/tictactoe.git
cd tictactoe
```

### Install Dependencies

Run the following Maven command to download and install all required dependencies:

```bash
mvn clean install
```

---

## Running the Project Locally

After cloning the repository and installing dependencies, you can run the project on your local machine.

### Step 1: Compile the Project

Run the following Maven command to compile the project:

```bash
mvn compile
```

### Step 2: Run the Application

You can run the application by executing the following command:

```bash
mvn javafx:run
```

This will launch the Tic-Tac-Toe game UI. From there, you can select your piece (X or O), choose difficulty, and start playing.

---

## How to Play

1. **Select a Piece**: Choose whether you want to play as `X` or `O`.
2. **Choose Difficulty**: Select either `Easy` or `Hard`. The AI's difficulty will adjust based on this selection.
3. **Start the Game**: After selecting your piece and difficulty, click the **Start Game** button to begin playing.
4. **Make Your Move**: Click on an empty cell in the 3x3 grid to place your piece.
5. **AI’s Turn**: After you place your move, the AI will make its move.
6. **Win or Tie**: The game ends when either player wins or the board is full with no winner.

---

## Folder Structure

```
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── assignment
│   │   │           └── tictactoe
│   │   │               ├── controller
│   │   │               ├── model
│   │   │               └── service
│   │   └── resources
│   │       └── icons
│   │           └── pAgain.png
│   └── test
│       └── java
│           └── com
│               └── assignment
│                   └── tictactoe
├── pom.xml
└── README.md
```

- `controller`: Contains the JavaFX controllers.
- `model`: Contains game models like `Board`, `Piece`, etc.
- `service`: Contains core game logic, including the AI (`AIPlayer` class) and the `minimax` algorithm.
- `resources`: Stores game assets like images.

---

## Contributing

Contributions are welcome! If you find a bug or have a feature request, please create an issue or fork the repository and submit a pull request.

### Steps to Contribute:
1. Fork the repository.
2. Create a new feature branch.
3. Commit your changes.
4. Push the branch.
5. Submit a pull request.

