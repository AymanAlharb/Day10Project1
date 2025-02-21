//Ayman Alharbi

import java.util.*;
import java.util.concurrent.TimeUnit;

public class TicTacToe {
    static Scanner input = new Scanner(System.in);
    static int n = 3;
    static int m = 3;
    static long wait = 1000;
    //The game board.
    static String[][] gameBoard = new String[n][m];
    static Random random = new Random();//X and Y to convert the moves to 2d arrays indexing.
    static int x = 0;
    static int y = 0;
    static String move = " ";
    //To determine who started the game and who is X and who us O.
    static boolean xMove = false;
    //To determine the winner.
    static String theWinner = " ";
    //To determine the game mode.
    static boolean nRounder = false;
    static int numOfwinsNeeded = 1;
    //For the three rounds game mode.
    static int userWins = 0;
    static int botWins = 0;
    static String userXO = "o";
    static String botXO = "x";
    static String botName = "bot";
    static String userName = "user";
    //To count the number of moves made by the players to detect draws.
    static int numOfMoves = 0;
    static int numberOfRoundsDrawn = 0;

    public static void main(String[] args) throws InterruptedException {
        //Print the welcome statements.
        print();
        //Take the user input.
        userInput();
        //Start the game.
        playingMethod();
        System.out.println("Thank you for playing the game.");
    }

    public static void print() throws InterruptedException {
        System.out.println("                                       ---Tic-Tac-Toe--- ");
        System.out.println("                      Welcome to X O\n" +
                "                      this game has two playing modes\n" +
                "                      a one rounder and a game where you can play as many rounds as you want");
        System.out.println("--------------------------------------------------------------------------------------------------------------------\n\n");
        TimeUnit.MILLISECONDS.sleep(wait);
    }

    //Method to declare the winner using the theWinner variable
    //and handle the case of draw using the number of moves made by the user and the bot.
    public static void winnerDeclare() {
        if (numOfMoves == 10) System.out.println("The game ends in a draw");
        else System.out.println(theWinner + " have won the game.");
    }

    public static void userInput() {
        System.out.println("Please enter your User Name");
        userName = input.nextLine();
        System.out.println("Enter a name for the computer: ");
        botName = input.nextLine();
        System.out.println("Enter 1 to enter the advance settings");
        String choise = input.next();
        if (choise.equals("1")) advanceSettings();
    }

    //Method to determine who start first.
    public static boolean whoPlaysFirst() {
        int turn = random.nextInt(1, 3);
        if (turn == 1) {
            xMove = true;
            System.out.println("You will play first: ");
            return false;
        } else {
            //If the bot starts first make it's first move than continue.
            return true;
        }
    }

    //The main playing method
    public static void playingMethod() throws InterruptedException {
        System.out.println("Welcome " + userName + " Please choose the playing format: \n1- One round\n2- n rounds");
        String mode = input.next();
        //Determine if the user wants to play the normal mode or the n rounder mode.
        if (mode.equals("2")) {
            nRounder = true;
            System.out.println("Please enter the number of wins needed to win the game");
            numOfwinsNeeded = input.nextInt();
        }
        //Reset the number of rounds drawn for next games if the player wants to play more than one game.
        numberOfRoundsDrawn = 0;
        //Initialize the board game.
        gameBoardBuilder();
        //If the bot play first generate the bot move than continue normally.
        if (whoPlaysFirst()) {
            System.out.println("You will play second: ");
            gameBoardDisplay();
            //make the program hold for one second to enhance the user experience.
            TimeUnit.MILLISECONDS.sleep(wait);
            System.out.println("\n\n___________________________\nComputer move");
            //generate the bot move.
            botMove();
        }
        //Display the board.
        gameBoardDisplay();
        //Playing loop.
        while (true) {
            //Make the player play his move.
            playerMove();
            //Display the board after the player move.
            gameBoardDisplay();
            //Check if the user won with his last move.
            if (gameOrRoundWon(userXO, userName)) break;
            //Make the bot move.
            botMove();
            System.out.println("\n\n___________________________\nComputer move");
            gameBoardDisplay();
            //Check if the bot won with his last move.
            if (gameOrRoundWon(botXO, botName)) break;
        }
        //Announce the winner of the game.
        winnerDeclare();
        //Ask the user if he wants to play another game.
        System.out.println("Enter Yes if you want to play another game");
        String choise = input.next();
        //Check if the user wants to play another game if yes call the playing method again, otherwise exit the method.
        if (choise.equalsIgnoreCase("yes")) playingMethod();
    }

    //Method to display the board.
    public static void gameBoardDisplay() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                System.out.print(" " + gameBoard[i][j]);
                if (j == m - 1) continue;
                System.out.print(" | ");
            }
            if (i == n - 1) continue;
            System.out.println("\n--------------");
        }
        System.out.println();
    }

    //Method to build the board, this method is a must in the three rounder mode.
    public static void gameBoardBuilder() {
        //Reset the number of moves for the next round.
        numOfMoves = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                gameBoard[i][j] = ("" + (((i * n) + j) + 1));
            }
        }
    }

    public static void playerMove() {
        System.out.println("Please choose your move");
        //A while loop to make sure the user choose a valid move.
        while (true) {
            move = input.next();
            if (checkMoveValidity(Integer.parseInt(move))) break;
            System.out.println("The " + move + " move is an invalid move please choose a valid move");
            gameBoardDisplay();
        }
        numOfMoves++;
        gameBoardChanger(move, userXO);
    }

    //Bot move maker.
    public static void botMove() throws InterruptedException {
        Integer temp = 0;
        while (true) {
            //generate a random number between 1 and 9.
            temp = random.nextInt(1, 10);
            //If the move is invalid keep generating random numbers.
            if (checkMoveValidity(temp)) break;
        }
        move = temp.toString();
        TimeUnit.MILLISECONDS.sleep(wait);
        numOfMoves++;
        gameBoardChanger(move, botXO);
    }


    public static boolean checkMoveValidity(int move) {
        //Convert the move to x and y to access the gameBoard array.
        moveConverter(move);
        //Make sure the user input a move within the range
        if (move > 9 || move < 1) return false;
        return (!(gameBoard[x][y].equals(userXO) || gameBoard[x][y].equals(botXO)));
    }

    //Convert the move and update x and y. * x and y acts like i and j.
    public static void moveConverter(int move) {
        if (move < 4) {
            x = 0;
            y = move - 1;
        } else if (move < 7) {
            x = 1;
            y = move - 4;
        } else {
            x = 2;
            y = move - 7;
        }
    }

    public static void gameBoardChanger(String move, String XO) {
        //Convert the move to be able to update the board.
        moveConverter(Integer.parseInt(move));
        //Change the board.
        gameBoard[x][y] = XO;
    }

    //This is a helper method for the gameOrRoundWon method to checks if the last move made by a player ended the game.
    //The String parameter is to know which player (X or O) called the method.
    public static boolean gameContinue(String XO) {
        //Check if the game drawn.
        if (drawDetector()) {
            return true;
        }
        for (int i = 0; i < n; i++) {
            if ((gameBoard[i][0].equals(gameBoard[i][1]) && gameBoard[i][1].equals(gameBoard[i][2]) && gameBoard[i][2].equals(XO)) ||
                    ((gameBoard[0][i].equals(gameBoard[1][i]) && gameBoard[1][i].equals(gameBoard[2][i]) && gameBoard[2][i].equals(XO)))) {
                return true;
            }
        }
        if ((gameBoard[0][0].equals(gameBoard[1][1]) && gameBoard[1][1].equals(gameBoard[2][2]) && gameBoard[2][2].equals(XO)) ||
                (gameBoard[0][2].equals(gameBoard[1][1]) && gameBoard[1][1].equals(gameBoard[2][0]) && gameBoard[2][0].equals(XO))) {
            return true;
        }
        //No one won yet so continue the game.
        return false;
    }

    public static boolean drawDetector() {
        //if the number of moves made by the user and the bot equal 9
        //than no more moves can be made and the game drawn.
        if (numOfMoves == 9) {
            //Increment the number of rounds drawn.
            numberOfRoundsDrawn++;
            return true;
        }
        return false;
    }

    //Method to check if the game or round won.
    public static boolean gameOrRoundWon(String XO, String player) {
        //The gameContinue is true than no one won.
        if (gameContinue(XO)) {
            //Check the mode of the game.
            if (nRounder) {
                if (player.equals(userName)) userWins++;
                else botWins++;
                //If the player reached the number of wins needed than end the game
                //otherwise declare it as the winner of the round.
                if (botWins == numOfwinsNeeded || userWins == numOfwinsNeeded) {
                    System.out.println("\n\nYou have " + (player.equals(userName) ? "won " : "lost ") + "round: " + (botWins + userWins + numberOfRoundsDrawn));
                    theWinner = player;
                    return true;
                } else {
                    //The sum of rounds won by the user, the bot and the rounds drawn equal to the overall number of rounds.
                    System.out.println("\n\nYou have " + (player.equals(userName) ? "won " : "lost ") + "round: " + (botWins + userWins + numberOfRoundsDrawn));
                    System.out.print((player.equals(userName) ? "You need " : (botName + " needs ")) + "to win ");
                    System.out.println((player.equals(userName) ? (numOfwinsNeeded - userWins) : (numOfwinsNeeded - botWins)) + " more round/s to win the game");
                    gameBoardBuilder();
                    System.out.println("\n___________________________\n");
                    gameBoardDisplay();
                }
            } else {
                theWinner = player;
                return true;
            }
        }
        return false;
    }

    //Advance settings
    public static void advanceSettings() {
        System.out.println("This is the advance settings menu");
        while (true) {
            System.out.println("1- Change the waiting time" +
                    "\n2- Customizable X and O" +
                    "\n0- Exit");
            String choice = input.next();
            if(choice.equals("0")) break;
            switch (choice) {
                case "1":
                    System.out.println("Enter the time in milliseconds");
                    wait = input.nextInt();
                    break;
                case "2":
                    System.out.println("Character length should be one");
                    System.out.println("Enter the character for the user: ");
                    userXO = input.next();
                    System.out.println("Enter the character for the bot: ");
                    botXO = input.next();
                    break;
            }
        }
    }
}
