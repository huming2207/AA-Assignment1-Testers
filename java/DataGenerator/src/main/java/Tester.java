import java.io.File;
import java.nio.file.*;
import java.io.IOException;
import java.util.Scanner;

public class Tester
{

    public static void main(String[] args)
    {
        mainMenu();
    }



    private static void mainMenu()
    {
        System.out.println("Data generator for AA Assignment 1 \nBy Ming Hu (s3554025) & Yuxuan Cheng (s3516930)");
        System.out.println("Select a test method:\n" +
                "1. Test by CJK/CJKV (East Asian) chars\n" +
                "2. Test by phrases\n" +
                "3. Run test and get result\n\n");

        Scanner scanner = new Scanner(System.in);
        int userInput = scanner.nextInt();
        scanner.nextLine(); // Consumes new line char

        switch(userInput)
        {
            case 1:
            {
                // Gather some information...
                System.out.print("[NOTICE] Enter article text file path: ");
                String inputPath = scanner.nextLine();
                System.out.print("\n[NOTICE] Enter output text file path: ");
                String outputPath = scanner.nextLine();


                // Run generator
                try
                {
                    StdInputFileGenerator.EastAsianCharGenerator(inputPath, outputPath, commandTypeSelector());
                }
                catch (IOException ioError)
                {
                    // If error occurs, exit by status code 1 with error message shown
                    System.err.println("[ERROR] IO Exception thrown! Message shows below: \n\n");
                    ioError.printStackTrace();
                    System.exit(1);
                }

                mainMenu();
                break;
            }
            case 2:
            {
                // Gather some information...
                System.out.print("[NOTICE] Enter article text file path: ");
                String inputPath = scanner.nextLine();
                System.out.print("\n[NOTICE] Enter output text file path: ");
                String outputPath = scanner.nextLine();


                // Run generator
                try
                {
                    StdInputFileGenerator.PhraseGenerator(inputPath, outputPath, commandTypeSelector());
                }
                catch (IOException ioError)
                {
                    // If error occurs, exit by status code 1 with error message shown
                    System.err.println("[ERROR] IO Exception thrown! Message shows below: \n\n");
                    ioError.printStackTrace();
                    System.exit(1);
                }

                mainMenu();
                break;
            }
            case 3:
            {
                try
                {
                    System.out.print("[INFO] Your assignment source path please: ");
                    String assignmentPath = scanner.nextLine();

                    // Test if assignment path can be found
                    if(!(Files.exists(Paths.get(assignmentPath))))
                    {
                        System.out.println("[INFO] Invalid path, please try again.");
                        mainMenu(); return;
                    }

                    // Test if assignment source can be found
                    if(!(Files.exists(Paths.get(assignmentPath + "/MultisetTester.class"))))
                    {
                        System.out.println("[INFO] Main class not found, please compile first.");
                        System.exit(2);
                    }

                    // Grab the test part
                    System.out.print(
                            "[INFO] Which multiset would you like to test?\n " +
                            "[INFO] It can be: bst, linkedlist, sortedlinkedlist, baltree, hash.\n" +
                            "[INFO] Enter your choice: ");
                    String testPart = scanner.nextLine();

                    // Grab the test file and see if it exists
                    System.out.print("[INFO] Enter your test file path: ");
                    String testFilePath = scanner.nextLine();

                    // Iterate all files in one path
                    if (!(Files.exists(Paths.get(testFilePath))))
                    {
                        System.out.println("[INFO] Test file not found, please try again.");
                        mainMenu();
                    }

                    AssignmentRunner.Run(assignmentPath, testPart, testFilePath, commandTypeSelector());



                }
                catch (IOException | InterruptedException exceptions)
                {
                    System.err.println("[ERROR] Exception occur, application will exit.");
                    exceptions.printStackTrace();
                    System.exit(1);
                }

                break;
            }
            default:
            {
                System.out.println("[ERROR] Wrong selection, please retry or press Ctrl+C to exit.\n");
                mainMenu();
                break;
            }
        }

        mainMenu();

    }

    private static CommandType commandTypeSelector()
    {
        System.out.print("[NOTICE] What do you want to generate?\n" +
                "[NOTICE] 1. Insertions\n" +
                "[NOTICE] 2. Deletions\n" +
                "[NOTICE] 3. Search\n" +
                "[NOTICE] Your choice: ");

        Scanner scanner = new Scanner(System.in);
        int result = scanner.nextInt();
        scanner.nextLine(); // Consumes new line char

        switch(result)
        {
            case 1: return CommandType.INSERTION;
            case 2: return CommandType.DELETION;
            case 3: return CommandType.SEARCH;
            default:
            {
                System.err.println("[ERROR] Invalid input, please try again.");
                commandTypeSelector();
                return CommandType.INSERTION; // For shutting up Intellij IDEA's warning only, useless.
            }
        }


    }
}
