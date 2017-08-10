package com.cyxhm;

import java.io.*;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class StdInputFileGenerator
{
    public static void SeparatedChineseCharsFromText(String inputPath, String outputPath,
                                              boolean randomDelete) throws IOException
    {
        System.out.println("[INFO] Running test: SeparatedChineseCharsFromText()");

        // Declare writer and reader
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(inputPath));
        BufferedReader bufferedReader = new BufferedReader(new FileReader(outputPath));
        long lineCount = 0;

        // Current line of the string
        String lineRead;

        while ((lineRead = bufferedReader.readLine()) != null)
        {
            // Increase the line counter
            lineCount++;
            System.out.println(String.format("[INFO] Reading/writing line %d...", lineCount));

            // Convert a line of string to char array
            char[] charArray = lineRead.toCharArray();

            // ...then write every char as command (append "A" before the char) one by one
            for (char selectedChar : charArray)
            {
                bufferedWriter.write(String.format("A %c\n", selectedChar));
            }

            // Flush cache when a line finishes
            bufferedWriter.flush();

            // If user requires to do a random delete, then
            if(randomDelete)
            {
                // Declare random
                Random random = new Random();

                // Get a random boolean
                if(random.nextBoolean())
                {
                    // Randomly pick a char from the char array and write with a delete command "RO"
                    bufferedWriter.write(
                            String.format("RO %c",
                            charArray[ThreadLocalRandom.current().nextInt(0, charArray.length - 1)]));
                }
            }

        }

        // Close the writer and reader
        bufferedWriter.close();
        bufferedReader.close();
    }

    public static void RepeatEnglishSentence(String outputPath, long repeatTimes) throws IOException
    {
        System.out.println("[INFO] Running test: RepeatEnglishSentence()");

        String repeatString = "I'm feeling very happy to be with you.";

        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outputPath));

        for(long currentRepeat = 0; currentRepeat < repeatTimes; currentRepeat++)
        {
            bufferedWriter.write(repeatString + "\n");
            bufferedWriter.flush();
        }

        bufferedWriter.close();

    }


    public static void SeparatedEnglishWord(String inputPath, String outputPath,
                                     boolean randomDelete) throws IOException
    {
        System.out.println("[INFO] Running test: SeparatedEnglishWord()");

        // Declare writer and reader
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(inputPath));
        BufferedReader bufferedReader = new BufferedReader(new FileReader(outputPath));
        long lineCount = 0;
        long wordCount = 0;

        // Current line of the string
        String lineRead;

        while ((lineRead = bufferedReader.readLine()) != null)
        {
            // Increase the line counter
            lineCount++;
            System.out.println(String.format("[INFO] Reading/writing line %d, word count...", lineCount));

            // Split to word, delimiters are: space, comma, slash, and full stop (".")
            String[] wordArray = lineRead.split("[ .,/]+");

            // Write to file
            for(String word : wordArray)
            {
                wordCount++;
                bufferedWriter.write(String.format("A %s\n", word));
            }

            // Flush the writer after every line
            bufferedWriter.flush();
        }

        // ...and close it later when finishes
        bufferedWriter.close();
    }


}
