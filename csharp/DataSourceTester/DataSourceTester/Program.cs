using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Net.Http;

namespace DataSourceTester
{
    internal class Program
    {
        private static void Main(string[] args)
        {
            string textFilePath = string.Empty, processFilePath = string.Empty;
            Mode mode;
            var repeatTimes = 1;
            
            if (args.Length < 1)
            {
                PrintUsage();
            }
            
            // Iterate the arguments
            foreach (var arg in args)
            {
                // Interpret mode argument
                if(arg.Contains("--file="))
                {
                    textFilePath = arg.Split('=')[1];
                }
                else if (arg.Contains("--repeat="))
                {
                    repeatTimes = Convert.ToInt32(arg.Split('=')[1]);
                }
                else if(arg.Contains("--process="))
                {
                    processFilePath = arg.Split('=')[1];
                }
                else if (arg.Contains("--mode="))
                {
                    switch (arg.Split('=')[1])
                    {
                        case "onesentence":
                        {
                            Console.WriteLine("[INFO] Selected mode: fixed sentence");
                            HookAndTest(Generator.Generator.GenerateSameStringList(repeatTimes), processFilePath);
                            break;
                        }
                        case "chinese":
                        {
                            Console.WriteLine("[INFO] Selected mode: Chinese chars (UTF-8 or GBK???)");
                            HookAndTest(Generator.Generator.GenerateChineseCharList(textFilePath), processFilePath);
                            break;
                        }
                        case "english":
                        {
                            Console.WriteLine("[INFO] Selected mode: English word strings");
                            HookAndTest(Generator.Generator.GenerateChineseCharList(textFilePath), processFilePath);
                            break;
                        }
                        default:
                        {
                            PrintUsage();
                            break;
                        }
                    }
                }
                else
                {
                    PrintUsage();
                }
            }
        }

        private static void PrintUsage()
        {
            Console.WriteLine("Usage: --process=PATH --mode=[chinese|english|onesentence] [--file=PATH] [--repeat=N]");
            Environment.Exit(1);
        }

        private static void HookAndTest(List<string> stringList, string processFilePath)
        {
            // Declare stop watch
            var stopWatch = new Stopwatch();
            
            // Declare Java process
            var javaProcess = new Process()
            {
                StartInfo = new ProcessStartInfo(processFilePath)
                {
                    CreateNoWindow = true,
                    RedirectStandardError = true,
                    RedirectStandardInput = true,
                    RedirectStandardOutput = true,
                    UseShellExecute = false
                },
            };


            if (javaProcess.Start())
            {
                Console.WriteLine("[SUCCESS] Java process started, test will continue.");
                stopWatch.Start();
            }
            else
            {
                Console.WriteLine("[ERROR] Failed to start Java process!");
                
                // In case something goes wrong, dispose the process hooker before exit
                javaProcess.Dispose();
                Environment.Exit(1);
            }
                
            
            // Iterate the string with "A" add operator (based on assignment spec)
            stringList.ForEach(stringContent => javaProcess.StandardInput.WriteLine($"A {stringContent}"));
            
            // When iteration finishes, stop the stopwatch, and print the time
            stopWatch.Stop();
            Console.WriteLine($"[INFO] Result: addition {stopWatch.ElapsedMilliseconds}ms");
            
            Environment.Exit(0);
        }
    }
}