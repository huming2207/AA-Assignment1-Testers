import org.apache.commons.lang.time.StopWatch;

import java.io.*;
import java.util.Scanner;

public class AssignmentRunner
{
    public static void Run(String sourcePath, String multisetType, String testFile)
                                            throws IOException, InterruptedException
    {
        // Declare process builder
        ProcessBuilder processBuilder =
                new ProcessBuilder("java", "MultisetTester", multisetType);
        processBuilder.directory(new File(sourcePath));

        // Get the length of test file
        LineNumberReader lineNumberReader = new LineNumberReader(new FileReader(testFile));
        lineNumberReader.skip(Long.MAX_VALUE);
        double lengthOfTestFile = lineNumberReader.getLineNumber() + 1;
        lineNumberReader.close();

        // Declare a stopwatch
        StopWatch stopWatch = new StopWatch();

        // Run the process
        Process process = processBuilder.start();

        // Set stdin
        // Here OutputStream is actually standard input!!
        BufferedWriter stdinWriter = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
        BufferedReader testFileReader = new BufferedReader(new FileReader(testFile));

        // Declare test file reader's line pointer
        String testFileLineRead;
        double currentLine = 1;

        stopWatch.start();


        // Iterate the test file and input it to stdin of the assignment program
        while((testFileLineRead = testFileReader.readLine()) != null)
        {
            // Only count the input time...
            stdinWriter.write(testFileLineRead);
            stdinWriter.flush();

            // "Reduce" some lines of output...
            System.out.print(String.format("\r[INFO] Current progress: %f %%",
                    (++currentLine/lengthOfTestFile) * 100));
        }

        System.out.println(" ...done!");
        stopWatch.stop();

        // Flush and close the buffered writer and readers
        stdinWriter.flush();
        testFileReader.close();
        stdinWriter.close();

        // Stop the stopwatch and print time result
        System.out.println(String.format("[INFO] Process exit with code %d.", process.waitFor()));
        System.out.println(String.format("[INFO] Spent time %d ms", stopWatch.getTime()));
    }

}
