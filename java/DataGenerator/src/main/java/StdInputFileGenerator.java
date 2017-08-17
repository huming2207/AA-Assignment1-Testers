import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.NlpAnalysis;
import org.ansj.splitWord.analysis.ToAnalysis;
import org.apache.commons.lang.time.StopWatch;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class StdInputFileGenerator
{
    public static void SeparatedEastAsianCharsFromText(String inputPath, String outputPath,
                                                       CommandToGenerate commandToGenerate) throws IOException
    {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        System.out.println("[INFO] Running test: SeparatedChineseCharsFromText()");

        // Declare writer and reader
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outputPath));
        BufferedReader bufferedReader = new BufferedReader(new FileReader(inputPath));
        long lineCount = 0;

        // Current line of the string
        String lineRead;


        while ((lineRead = bufferedReader.readLine()) != null)
        {
            if(!(lineRead.isEmpty() || lineRead.equals("\n") || lineRead.equals("\r\n")) ) {
                // Increase the line counter
                lineCount++;
                System.out.println(String.format("[INFO] Reading/writing line %d...", lineCount));

                // Convert a line of string to char array
                char[] charArray = easternAsianStrFilter(lineRead).toCharArray();

                // ...then write every char as command (append "A" before the char) one by one
                for (char selectedChar : charArray) {
                    bufferedWriter.write(String.format("A %c\n", selectedChar));
                }

                // Flush cache when a line finishes
                bufferedWriter.flush();

                // Generate the commands
                commandGenerator(commandToGenerate, charArray, bufferedWriter);
            }
        }

        // Close the writer and reader
        bufferedWriter.flush();
        bufferedWriter.close();
        bufferedReader.close();

        // Count the time
        stopWatch.stop();
        System.out.println(String.format("[INFO] Time spent %d ms.", stopWatch.getTime()));

    }

    public static void SeparatedChinesePhrase(String inputPath, String outputPath,
                                              CommandToGenerate commandToGenerate) throws IOException
    {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        System.out.println("[INFO] Running test: SeparatedChinesePhrase");

        // Declare writer and reader
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outputPath));
        BufferedReader bufferedReader = new BufferedReader(new FileReader(inputPath));
        long lineCount = 0;

        // Current line of the string
        String lineRead;

        while ((lineRead = bufferedReader.readLine()) != null)
        {
            if(!(lineRead.isEmpty() || lineRead.equals("\n") || lineRead.equals("\r\n")) )
            {

                // Increase the line counter
                lineCount++;
                System.out.println(String.format("[INFO] Reading/writing line %d...", lineCount));

                // Creates a phrase list
                ArrayList<String> phraseList = new ArrayList<>();

                // Parse the line string and parse it to phrases
                for (Term term : ToAnalysis.parse(lineRead).getTerms())
                {
                    phraseList.add(term.getName());
                    bufferedWriter.write(String.format("A %s\n", term.getName()));
                }

                // If user need to generate some extra commands, then do it...
                commandGenerator(commandToGenerate, phraseList, bufferedWriter);

            }
        }

        // Close the writer and reader
        bufferedWriter.flush();
        bufferedWriter.close();
        bufferedReader.close();

        // Count the time
        stopWatch.stop();
        System.out.println(String.format("[INFO] Time spent %d ms.", stopWatch.getTime()));
    }


    // ONLY EAST ASIAN chars  will reserved after this filter method is applied...
    // This includes:
    //      Chinese (simplified & traditional stantards used in Mainland/Hong Kong/Macau/Taiwan)
    //      Korean
    //      Japanese (including Han chars of course)
    //      Vietnamese (Han-Nom chars)
    private static String easternAsianStrFilter(String inputStr)
    {

        StringBuilder stringBuilder = new StringBuilder();

        // Iterate the string by each char inside
        for(char chineseChar : inputStr.toCharArray())
        {
            // Put char into UTF-16 block
            Character.UnicodeBlock unicodeBlock = Character.UnicodeBlock.of(chineseChar);

            // Append if this char is an Eastern-Asian char
            if(
                // For the details abouut these stuff below, please refer to this article:
                // https://en.wikipedia.org/wiki/CJK_Unified_Ideographs
                    unicodeBlock == Character.UnicodeBlock.CJK_COMPATIBILITY ||
                            unicodeBlock == Character.UnicodeBlock.CJK_COMPATIBILITY_FORMS ||
                            unicodeBlock == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS ||
                            unicodeBlock == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS_SUPPLEMENT ||
                            unicodeBlock == Character.UnicodeBlock.CJK_RADICALS_SUPPLEMENT ||
                            unicodeBlock == Character.UnicodeBlock.CJK_STROKES ||
                            unicodeBlock == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION ||
                            unicodeBlock == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS ||
                            unicodeBlock == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A ||
                            unicodeBlock == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B ||
                            unicodeBlock == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_C ||
                            unicodeBlock == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_D)
            {
                stringBuilder.append(chineseChar);
            }
        }

        return stringBuilder.toString();
    }

    private static void commandGenerator(CommandToGenerate commandToGenerate,
                                         ArrayList<String> itemList, BufferedWriter writer) throws IOException
    {
        if(itemList.size() <= 1) return;

        switch (commandToGenerate)
        {
            case NONE:
            {
                break;
            }
            case ALL_REMOVE:
            {
                for(String item : itemList)
                {
                    writer.write(String.format("RO %s\n", item));
                }

                writer.flush();
                break;
            }
            case ALL_SEARCH:
            {
                for(String item : itemList)
                {
                    writer.write(String.format("S %s\n", item));
                }

                writer.flush();
                break;
            }
            case RANDOM_REMOVE:
            {
                Random random = new Random();
                for(String item : itemList)
                {
                    // Get a random boolean
                    if (random.nextBoolean())
                    {
                        // Randomly pick a char from the char array and write with a delete command "RA"
                        writer.write(String.format("RO %s\n", item));

                    }
                }

                writer.flush();
                break;
            }
            case RANDOM_SEARCH:
            {
                Random random = new Random();
                for(String item : itemList)
                {
                    // Get a random boolean
                    if (random.nextBoolean())
                    {
                        // Randomly pick a char from the char array and write with a delete command "RA"
                        writer.write(
                                String.format("S %s\n", item));
                    }
                }

                writer.flush();
                break;
            }
        }
    }

    private static void commandGenerator(CommandToGenerate commandToGenerate,
                                         char[] charList, BufferedWriter writer) throws IOException
    {
        ArrayList<String> strList = new ArrayList<>();

        for(Character character : charList)
        {
            strList.add(character.toString());
        }

        commandGenerator(commandToGenerate, strList, writer);
    }


}
