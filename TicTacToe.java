//Ayman Alharbi
import java.util.*;
public class TicTacToe {
    static Scanner input = new Scanner(System.in);
    static int n = 3;
    static int m = 3;
    //The game board.
    static String[][] gameBoard = new String[n][m];
    static Random random = new Random();
    //X and Y to convert the moves to 2d arrays indexing.
    static int x = 0;
    static int y = 0;
    static int move = 0;
    //To determine who started the game and who is X and who us O.
    static boolean xMove = false;
    //To determine the winner.
    static boolean userIsTheWinner = false;
    //To determine the game mode.
    static boolean threeRounder = false;
    //For the three rounds game mode.
    static int userWins = 0;
    static int botWins = 0;

    public static void main(String[] args) {
        System.out.println("    ---Tic-Tac-Toe--- ");
        System.out.println("Please enter your User Name");
        String userName = input.nextLine();
        System.out.println("Welcome " + userName + " Please choose the playing format: \n1- One round\n2- Three rounds");
        int mode = input.nextInt();
        if (mode == 2) threeRounder = true;

        gameBoardBuilder();
        //Determine who start first.
        int turn = random.nextInt(1, 3);
        if (turn == 1) {
            xMove = true;
            System.out.println("You will play first: ");
        } else {
            //If the bot starts first make it's first move than continue.
            System.out.println("You will play second: ");
            gameBoardDisplay();
            System.out.println("\n\n___________________________\nComputer move");
            botMove();
        }
        //Display the game board.
        gameBoardDisplay();
        //Playing loop.
        while (true) {
            //This if condition is for the bot.
            if (xMove) {
                if (gameContinue(false)) {
                    //If we are playing three round increment the counter of wins than check the condition.
                    if (threeRounder) {
                        botWins++;
                        if (botWins == 2) {
                            break;
                        } else {
                            System.out.println("\n\nYou have lost round " + (botWins + userWins));
                            gameBoardBuilder();
                            System.out.println("\n___________________________\n");
                            gameBoardDisplay();
                        }
                    } else {
                        //We are playing a one rounder so exit of the playing loop.
                        break;
                    }
                }
                //Two parts to handle the two cases correctly if the user started first than the bot played second so handle the operation with this in mind * if xMove = true than the user started first
            } else {
                if (gameContinue(true)) {
                    if (threeRounder) {
                        botWins++;
                        if (botWins == 2) {
                            break;
                        } else {
                            System.out.println("\n\nYou have lost round " + (botWins + userWins));
                            gameBoardBuilder();
                            System.out.println("\n___________________________\n");
                            gameBoardDisplay();
                        }
                    } else {
                        break;
                    }
                }
            }
            //This part is for the user
            System.out.println("Please choose your move");
            //A while loop to make sure the user choose a valid move.
            while (true) {
                move = input.nextInt();
                if (checkMoveValidity(move)) break;
                System.out.println("The " + move + " move is an invalid move please choose a valid move");
                gameBoardDisplay();
            }
            //This if statements to know who started first and update the board accordingly.
            if (xMove) gameBoardChanger(move, true);
            else gameBoardChanger(move, false);
            //Display the board after the player move.
            gameBoardDisplay();
            //This if is the same as the above but this one is for the user.
            if (xMove) {
                if (gameContinue(true)) {
                    if (threeRounder) {
                        userWins++;
                        if (userWins == 2) {
                            userIsTheWinner = true;
                            break;
                        } else {
                            System.out.println("\n\nYou have won round " + (botWins + userWins));
                            gameBoardBuilder();
                            System.out.println("\n___________________________\n");
                            gameBoardDisplay();
                        }
                    } else {
                        userIsTheWinner = true;
                        break;
                    }
                }
            } else {
                if (gameContinue(false)) {
                    if (threeRounder) {
                        userWins++;
                        if (userWins == 2) {
                            break;
                        } else {
                            System.out.println("\n\nYou have won round " + (botWins + userWins));
                            gameBoardBuilder();
                            System.out.println("\n___________________________\n");
                            gameBoardDisplay();
                        }
                    } else {
                        break;
                    }
                }
            }
            botMove();
            System.out.println("\n\n___________________________\nComputer move");
            gameBoardDisplay();
        }
        if (userIsTheWinner) System.out.println(userName + " have won the game");
        else System.out.println("You have lost");
        System.out.println("Thank you");
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
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                gameBoard[i][j] = ("" + (((i * n) + j) + 1));
            }
        }
    }

    //Bot move maker.
    public static void botMove() {
        while (true) {
            //generate a random number between 1 and 9.
            move = random.nextInt(1, 10);
            //If the move is invalid keep generating random numbers.
            if (checkMoveValidity(move)) break;
        }
        //Check who started first.
        if (xMove) gameBoardChanger(move, false);
        else gameBoardChanger(move, true);

    }


    public static boolean checkMoveValidity(int move) {
        //Convert the move to x and y to access the gameBoard array.
        moveConverter(move);
        //Make sure the user input a move within the range
        if(move > 9 || move < 1) return false;
        return (!(gameBoard[x][y].equals("X") || gameBoard[x][y].equals("O")));
    }

    //Convert the move and update x and y.
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

    public static void gameBoardChanger(int move, boolean xMove) {
        //Convert the move to be able to update the board.
        moveConverter(move);
        //Check whose move is calling the method.
        if (xMove) gameBoard[x][y] = "X";
        else gameBoard[x][y] = "O";
    }

    //This method checks if with the last move made a player won.
    //The Boolean parameter is to know which player (X or O) called the method.
    public static boolean gameContinue(boolean xMove) {
        String str = "";
        if (xMove) str = "X";
        else str = "O";
        for (int i = 0; i < n; i++) {
            if ((gameBoard[i][0].equals(gameBoard[i][1]) && gameBoard[i][1].equals(gameBoard[i][2]) && gameBoard[i][2].equals(str)) ||
                    ((gameBoard[0][i].equals(gameBoard[1][i]) && gameBoard[1][i].equals(gameBoard[2][i]) && gameBoard[2][i].equals(str)))) {
                return true;
            }
        }
        if ((gameBoard[0][0].equals(gameBoard[1][1]) && gameBoard[1][1].equals(gameBoard[2][2]) && gameBoard[2][2].equals(str)) ||
                (gameBoard[0][2].equals(gameBoard[1][1]) && gameBoard[1][1].equals(gameBoard[2][0]) && gameBoard[2][0].equals(str))) {
            return true;
        }
        //No one won yet so continue the game.
        return false;
    }
}