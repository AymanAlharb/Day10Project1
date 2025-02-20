//Ayman Alharbi
import java.util.*;

public class Calculator {
    static ArrayList<String> operationsRecord = new ArrayList<>();

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int choice = 0;
        int num1 = 0;
        int num2 = 0;
        while (true) {
            System.out.println(
                    "Enter 1 to addition the numbers" +
                    "\n" +
                    "Enter 2 to subtraction the numbers" +
                    "\n" +
                    "Enter 3 to multiplication the numbers" +
                    "\n" +
                    "Enter 4 to division the numbers" +
                    "\n" +
                    "Enter 5 to modulus the numbers" +
                    "\n" +
                    "Enter 6 to find minimum number" +
                    "\n" +
                    "Enter 7 to find maximum number" +
                    "\n" +
                    "Enter 8 to find the average of numbers" +
                    "\n" +
                    "Enter 9 to print the last result in calculator" +
                    "\n" +
                    "Enter 10 to print the list of all results in calculator\n");
            choice = input.nextInt();
            while (choice < 0 || choice > 10){
                System.out.println("Please enter a valid choice");
                choice = input.nextInt();
            }
            if (choice == 0) break;
            if(choice != 10 && choice != 9){
                System.out.println("Enter the first number: ");
                num1 = input.nextInt();
                System.out.println("Enter the second number: ");
                num2 = input.nextInt();
            }
            switch (choice) {
                case 9:
                    print();
                    break;
                case 10:
                    printAll();
                    break;
                case 1:
                    addtion(num1, num2);
                    break;
                case 2:
                    subtraction(num1, num2);
                    break;
                case 3:
                    multiplication(num1, num2);
                    break;
                case 4:
                    division(num1, num2);
                    break;
                case 5:
                    mod(num1, num2);
                    break;
                case 6:
                    min(num1, num2);
                    break;
                case 7:
                    max(num1, num2);
                    break;
                case 8:
                    avg(num1, num2);
                    break;
            }
        }
    }

    public static int addtion(int num1, int num2) {
        int sum = num1 + num2;
        operationsRecord.add(num1 + " + " + num2 + " = " + sum);
        return sum;

    }

    public static int subtraction(int num1, int num2) {
        int subtraction = num1 - num2;
        operationsRecord.add(num1 + " - " + num2 + " = " + subtraction);
        return subtraction;
    }

    public static int multiplication(int num1, int num2) {
        int multiplication = num1 * num2;
        operationsRecord.add(num1 + " * " + num2 + " = " + multiplication);
        return multiplication;
    }

    public static int division(int num1, int num2) {
        int division = num1 / num2;
        operationsRecord.add(num1 + " / " + num2 + " = " + division);
        return division;
    }

    public static int min(int num1, int num2) {
        int min = Math.min(num1, num2);
        operationsRecord.add("The minimum of " + num1 + " and " + num2 + " is " + min);
        return min;
    }

    public static int max(int num1, int num2) {
        int max = Math.max(num1, num2);
        operationsRecord.add("The maximum of " + num1 + " and " + num2 + " is " + max);
        return max;
    }

    public static int avg(int num1, int num2) {
        int avg = (num1 + num2) / 2;
        operationsRecord.add("The avg of " + num1 + " and " + num2 + " is " + avg);
        return avg;
    }

    public static int mod(int num1, int num2) {
        int mod = num1 / num2;
        operationsRecord.add(num1 + " / " + num2 + " = " + mod);
        return mod;
    }

    public static void print() {
        System.out.println(operationsRecord.get(operationsRecord.size() - 1));

    }

    public static void printAll() {
        for (String op : operationsRecord) {
            System.out.println(op);
        }
    }
}