using System.Collections.Generic;
using System.IO;
using System.Linq;

namespace DataSourceTester.Generator
{
    public class Generator
    {
        public static List<string> GenerateSameStringList(int itemCount)
        {
            return Enumerable.Repeat("I'm feeling very happy to stay with you", itemCount).ToList();
        }

        public static List<string> GenerateEnglishWordList(string textFile)
        {
            return File.ReadAllText(textFile).Split(new[] {' ', ',', ';', '.'}).ToList();
        }

        public static List<string> GenerateChineseCharList(string textFile)
        {
            // Convert to char array and then convert to list of string...
            return File.ReadAllText(textFile).ToCharArray().Select(c => c.ToString()).ToList();
        }
        
    }
}