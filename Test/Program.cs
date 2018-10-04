using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Runtime.InteropServices;

namespace Test
{
    class Program
    {
        [DllImport("GMWilddogWrapper.dll", EntryPoint = "wilddog_init", CallingConvention = CallingConvention.Cdecl)]
        internal static extern string Init(string appId);
        [DllImport("GMWilddogWrapper.dll", EntryPoint = "wilddog_set", CallingConvention = CallingConvention.Cdecl)]
        internal static extern string Set(string path, string msg);
        [DllImport("GMWilddogWrapper.dll", EntryPoint = "wilddog_subscribe", CallingConvention = CallingConvention.Cdecl)]
        internal static extern string Subscribe(string path, string type);
        [DllImport("GMWilddogWrapper.dll", EntryPoint = "wilddog_pull", CallingConvention = CallingConvention.Cdecl)]
        internal static extern string Pull(string format);
        [DllImport("GMWilddogWrapper.dll", EntryPoint = "wilddog_remove", CallingConvention = CallingConvention.Cdecl)]
        internal static extern string Remove(string path);

        static void Main(string[] args)
        {
            Console.WriteLine(Init("dragonus"));
            Subscribe("players", "value");
            Set("players", "cool");
            for (; ; )
                Console.WriteLine(Pull(""));
        }
    }
}
