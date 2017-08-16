import org.apache.commons.lang.time.StopWatch;

import java.io.*;
import java.util.Scanner;

public class AssignmentRunner
{
    public static void Run(String sourcePath, String multisetType, String testFile) throws IOException
    {
        // Declare process builder
        ProcessBuilder processBuilder =
                new ProcessBuilder("java", "MultisetTester", multisetType);
        processBuilder.directory(new File(sourcePath));

        // Declare a stopwatch
        StopWatch stopWatch = new StopWatch();

        // Run the process
        Process process = processBuilder.start();

        // Set stdin
        // Here OutputStream is actually standard input!!!
        OutputStream stdinStream = process.getOutputStream();
        InputStream stdoutStream = process.getInputStream();

        // Write to file with buffered reader/writer
        BufferedWriter stdinWriter = new BufferedWriter(new OutputStreamWriter(stdinStream));
        BufferedReader testFileReader = new BufferedReader(new FileReader(testFile));
        BufferedReader stdoutReader = new BufferedReader(new InputStreamReader(stdoutStream));

        // Declare test file reader's line pointer
        String lineRead;

        // Declare output
        Scanner scanner = new Scanner(stdoutReader);

        // Start stopwatch here
        stopWatch.start();

        // Iterate the test file and input it to stdin of the assignment program
        while((lineRead = testFileReader.readLine()) != null)
        {
            stdinWriter.write(lineRead + "\n");
            stdinWriter.flush();

            while(scanner.hasNext())
            {
                System.out.println(String.format("[ASSIGNMENT OUTPUT] %s", scanner.next()));
            }
        }

        // Flush and close the buffered writer and readers
        stdinWriter.flush();
        stdoutReader.close();
        testFileReader.close();
        stdinWriter.close();

        // Stop the stopwatch and print time result
        stopWatch.stop();
        process.destroy();
    }
}
