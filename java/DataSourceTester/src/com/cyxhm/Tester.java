package com.cyxhm;

import java.io.IOException;
import java.util.Scanner;

public class Tester
{

    public static void main(String[] args)
    {

    }

    private static void mainMenu()
    {
        System.out.println("Data generator for AA Assignment 1 \nBy Ming Hu (s3554025) & Yuxuan Cheng (s3516930)");
        System.out.println("Select a test method:\n" +
                "1. Test by CJK/CJKV (East Asian) chars\n" +
                "2. Test by repeating English sentences\n" +
                "3. Test by English words\n\n");

        Scanner scanner = new Scanner(System.in);
        int userInput = scanner.nextInt();
        scanner.nextLine(); // Consumes new line char

        // TODO: 1. Shorten this switch-case; 2. Implements method #2's random deletion
        switch(userInput)
        {
            case 1:
            {
                // Gather some information...
                System.out.print("[NOTICE] Enter article text file input path: ");
                String inputPath = scanner.nextLine();
                System.out.print("\n[NOTICE] Enter output text file input path: ");
                String outputPath = scanner.nextLine();
                System.out.print("[NOTICE] Enter \"yes\" to generate random deletion command,\n" +
                        "[NOTICE] or press enter to continue: ");
                boolean generateRandomDelCmd = scanner.nextLine().toLowerCase().equals("yes");

                // Run generator
                try
                {
                    StdInputFileGenerator.SeparatedEastAsianCharsFromText(inputPath, outputPath, generateRandomDelCmd);
                }
                catch (IOException ioError)
                {
                    // If error occurs, exit by status code 1 with error message shown
                    System.err.println("[ERROR] IO Exception thrown! Message shows below: \n\n");
                    ioError.printStackTrace();
                    System.exit(1);
                }

                break;
            }
            case 2:
            {
                // Gather some information...
                System.out.print("[NOTICE] Enter repeat times: ");
                long repeatTimes = Long.valueOf(scanner.nextLine());
                System.out.print("\n[NOTICE] Enter output text file input path: ");
                String outputPath = scanner.nextLine();
                System.out.print("[NOTICE] Enter \"yes\" to generate random deletion command,\n" +
                        "[NOTICE] or press enter to continue: ");
                boolean generateRandomDelCmd = scanner.nextLine().toLowerCase().equals("yes");

                // Run generator
                try
                {
                    StdInputFileGenerator.RepeatEnglishSentence(outputPath, repeatTimes);
                }
                catch (IOException ioError)
                {
                    // If error occurs, exit by status code 1 with error message shown
                    System.err.println("[ERROR] IO Exception thrown! Message shows below: \n\n");
                    ioError.printStackTrace();
                    System.exit(1);
                }

                break;
            }
            case 3:
            {
                // Gather some information...
                System.out.print("[NOTICE] Enter article text file input path: ");
                String inputPath = scanner.nextLine();
                System.out.print("\n[NOTICE] Enter output text file input path: ");
                String outputPath = scanner.nextLine();
                System.out.print("[NOTICE] Enter \"yes\" to generate random deletion command,\n" +
                        "[NOTICE] or press enter to continue: ");
                boolean generateRandomDelCmd = scanner.nextLine().toLowerCase().equals("yes");

                // Run generator
                try
                {
                    StdInputFileGenerator.SeparatedEnglishWord(inputPath, outputPath, generateRandomDelCmd);
                }
                catch (IOException ioError)
                {
                    // If error occurs, exit by status code 1 with error message shown
                    System.err.println("[ERROR] IO Exception thrown! Message shows below: \n\n");
                    ioError.printStackTrace();
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
    }
}
