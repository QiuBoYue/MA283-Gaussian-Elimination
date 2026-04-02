import java.util.Scanner;

public class GaussianElimination {

    public static void main(String[] args) {

        // The Scanner is used to read user input from the keyboard
        Scanner sc = new Scanner(System.in);

       // running is used to control whether the program continues to run; true means continue, false means stop
        boolean running = true;

        // The `while` loop means that as long as `running` is true, you can keep entering new examples; this way, the program doesn't have to restart every time.
        while (running) {

            
            // Read the number of equations
            // n represents the number of unknowns
            // For example: 2 unknowns -> a system of two linear equations;
            // 3 unknowns -> a system of three linear equations
            System.out.print("Enter number of variables: ");
            int n = sc.nextInt();

            
            // Create an augmented matrix
            // Here, we create a two-dimensional array with n rows and n+1 columns
            // Why n+1 columns? Because in a system of linear equations:
            // ax + by + cz = d, where a, b, and c on the left are coefficients, and d on the right is the constant term. Therefore, we write the entire system of equations as an “augmented matrix”:
            // [ a  b  c | d ]
            // If there are n unknowns, there are n columns of coefficients + 1 column of the constant term
            double[][] matrix = new double[n][n + 1];


            //  Input the augmentation matrix
            System.out.println("Enter the augmented matrix:");

            // The outer loop controls the “rows”
            // Each row represents an equation
            for (int i = 0; i < n; i++) {

                // The inner loop controls the “columns”
                // A total of n+1 numbers must be entered in this row
                // The first n are coefficients, and the last one is the constant term
                for (int j = 0; j <= n; j++) {
                    matrix[i][j] = sc.nextDouble();
                }
            }


            //Print the initial matrix
            System.out.println("\nInitial Matrix:");
            printMatrix(matrix);

            
            // Gaussian Elimination
            //    Forward Elimination
            // The goal of Gaussian elimination:
            // To transform the matrix step by step into “upper triangular form”
            // For example:
            // [ 1  *  * | * ]
            // [ 0  1  * | * ]
            // [ 0  0  1 | * ]
            for (int i = 0; i < n; i++) {

                
                //  Find the current pivot
                // The pivot is the number located on the diagonal of the current row
                // For example:
                // For row 1, look at matrix[0][0]
                // For row 2, look at matrix[1][1]
                // For row 3, look at matrix[2][2]
                double pivot = matrix[i][i];

                
                // Check if the pivot is 0
                // If pivot = 0, the “divide by pivot” step cannot be performed
                // Immediately display an error message and terminate the calculation
                if (pivot == 0) {
                    System.out.println("Error: Pivot is zero. Cannot continue this system.");
                    break;
                }

                
                //  Normalizing the Pivot Row
                // Mathematical Objective:
                // Set the pivot element to 1
                System.out.println("\nStep " + (i + 1) + ": Normalize pivot");
                System.out.println("R" + (i + 1) + " = R" + (i + 1) + " / " + pivot);

               // Here, we use a loop to divide each element in the current row by the pivot, continuing until we reach the last column
                for (int j = 0; j <= n; j++) {
                    matrix[i][j] = matrix[i][j] / pivot;
                }

                printMatrix(matrix);

                
                //  Clear the elements below the leading element
                // Clear all numbers below the leading element in the current row to 0
                for (int k = i + 1; k < n; k++) {

                    // The factor represents “the number in the row below this one”
                    // It tells us: how many times the leading row must be subtracted to reduce it to 0
                    double factor = matrix[k][i];

                    System.out.println("\nEliminate element in Row " + (k + 1));
                    System.out.println("R" + (k + 1) + " = R" + (k + 1) + " - "
                            + factor + " * R" + (i + 1));

                    
                    // New row k = original row k - factor × row i
                    for (int j = 0; j <= n; j++) {
                        matrix[k][j] = matrix[k][j] - factor * matrix[i][j];
                    }

                    printMatrix(matrix);
                }
            }

            
            //  Back Substitution
            //    Solving by back substitution
            // After completing forward substitution, the matrix is usually in upper triangular form
            // At this point, the solution in the bottom row is easiest to read directly; first solve for the last unknown, then substitute it back into the equation to solve for the preceding unknowns
            double[] solution = new double[n];

            for (int i = n - 1; i >= 0; i--) {

                // First, assign the value in the last column of this row (the constant term) to `solution[i]`
                // For example:
                // x2 = 1
                // Here, we first take the value from the right side
                solution[i] = matrix[i][n];

                // Then subtract the terms in this row for which the solution is already known
                // For example:
                // x1 + 0.5x2 = 2.5
                // If we already know that x2 = 1
                // Then:
                // x1 = 2.5 - 0.5(1)
                for (int j = i + 1; j < n; j++) {
                    solution[i] = solution[i] - matrix[i][j] * solution[j];
                }

            }


            //  Output the final solution
            System.out.println("\nFinal Solution:");
            for (int i = 0; i < n; i++) {
                System.out.println("x" + (i + 1) + " = " + solution[i]);
            }

            //  Ask if you want to continue
            System.out.print("\nDo you want to solve another system? (y/n): ");
            char choice = sc.next().charAt(0);

            // If the user enters n or N, terminate the program
            if (choice == 'n' || choice == 'N') {
                running = false;
            }
        }

        sc.close();
    }


    // printMatrix method: Used to print the matrix. This method displays the current matrix in a neat format, making it easier to observe the changes at each step.
    public static void printMatrix(double[][] matrix) {

        for (double[] row : matrix) {

            for (double value : row) {

                System.out.printf("%8.2f ", value);

            }

            System.out.println();
        }
    }
}