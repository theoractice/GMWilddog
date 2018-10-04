using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Runtime.InteropServices;
using org.gamemaker.wilddog;

namespace GMWilddog
{
    public class Wrapper
    {
        [DllExport("wilddog_init", CallingConvention = CallingConvention.Cdecl)]
        public static string Init(string appId) => App.wilddog_init(appId);

        [DllExport("wilddog_set", CallingConvention = CallingConvention.Cdecl)]
        public static string Set(string path, string msg) => App.wilddog_set(path, msg);

        [DllExport("wilddog_subscribe", CallingConvention = CallingConvention.Cdecl)]
        public static string Subscribe(string path, string type) => App.wilddog_subscribe(path, type);

        [DllExport("wilddog_pull", CallingConvention = CallingConvention.Cdecl)]
        public static string Pull(string format) => App.wilddog_pull(format);

        [DllExport("wilddog_remove", CallingConvention = CallingConvention.Cdecl)]
        public static string Remove(string path) => App.wilddog_remove(path);
    }
}
