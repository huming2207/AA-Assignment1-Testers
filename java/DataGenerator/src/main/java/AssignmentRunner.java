import org.apache.commons.lang.time.StopWatch;

import java.io.*;

public class AssignmentRunner
{
    public static void Run(String sourcePath, String multisetType, String testFilePath, CommandType commandType)
                                            throws IOException, InterruptedException
    {
        // Declare process builder
        ProcessBuilder processBuilder =
                new ProcessBuilder("java", "MultisetTester", multisetType);
        processBuilder.directory(new File(sourcePath));


        // Run the process
        Process process = processBuilder.start();

        // Run insertion test first on every time (otherwise there will be nothing for deletion/search lol...)
        for (File file : new File(testFilePath).listFiles())

        {
            System.out.println(String.format("\n\n[INFO] Now testing %s , please wait...",
                    file.getCanonicalPath()));

            if (file.getCanonicalPath().endsWith(".insertion.txt"))
            {
                runTest(file.getCanonicalPath(), process);
            }
        }

        // If user need to test deletion, then do it...
        if (commandType == CommandType.DELETION)
        {
            for (File file : new File(testFilePath).listFiles())

            {
                System.out.println(String.format("\n\n[INFO] Now testing %s for DELETION, please wait...",
                        file.getCanonicalPath()));

                if (file.getCanonicalPath().endsWith(".deletion.txt"))
                {
                    runTest(file.getCanonicalPath(), process);
                }
            }
        }

        // If user needs to do search test, then do it...
        else if (commandType == CommandType.SEARCH)
        {
            for (File file : new File(testFilePath).listFiles())

            {
                System.out.println(String.format("\n\n[INFO] Now testing %s for SEARCH, please wait...",
                        file.getCanonicalPath()));

                if (file.getCanonicalPath().endsWith(".search.txt"))
                {
                    runTest(file.getCanonicalPath(), process);
                }
            }
        }


        // Print exit code for debugging
        System.out.println(String.format("[INFO] Process exit with code %d.", process.waitFor()));
    }

    private static void runTest(String testFile, Process process) throws IOException
    {
        // Get the length of test file
        LineNumberReader lineNumberReader = new LineNumberReader(new FileReader(testFile));
        lineNumberReader.skip(Long.MAX_VALUE);
        double lengthOfTestFile = lineNumberReader.getLineNumber() + 1;
        lineNumberReader.close();

        // Declare a stopwatch
        StopWatch stopWatch = new StopWatch();

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

        // Print result for insertions
        System.out.println(String.format("[INFO] Spent time %d ms", stopWatch.getTime()));

        // Reset stopwatch
        stopWatch.reset();
    }

}
