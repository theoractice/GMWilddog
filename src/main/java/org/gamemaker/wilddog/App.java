package org.gamemaker.wilddog;

import com.wilddog.client.*;
import com.wilddog.wilddogcore.WilddogApp;
import com.wilddog.wilddogcore.WilddogOptions;

import ikvm.lang.DllExport;

import java.util.LinkedList;
import java.util.Queue;

public class App {

/*    @DllExport(name = "wilddog_init", ordinal = 1)
    public static String wilddog_init(String appId) {
        return appId + "true";
    }

    public static void main(String[] args) {
        System.out.println(wilddog_init("dragonus"));
    }*/

    static Queue<Msg> msgQueue;
    static App thisApp;

//    @DllExport(name = "wilddog_init", ordinal = 1)
    public static String wilddog_init(String appId) {
        if (thisApp == null) {
            thisApp = new App();
        }
        msgQueue = new LinkedList<Msg>();
        WilddogOptions options = new WilddogOptions.Builder().setSyncUrl(String.format("https://%s.wilddogio.com", appId)).build();
        WilddogApp.initializeApp(options);
        return "true";
    }

//    @DllExport(name = "wilddog_set", ordinal = 2)
    public static String wilddog_set(String path, String data) {
        SyncReference ref = WilddogSync.getInstance().getReference();
        ref.child(path).setValue(data);
        return "true";
    }

//    @DllExport(name = "wilddog_subscribe", ordinal = 3)
    public static String wilddog_subscribe(String path, String type) {
        SyncReference ref = WilddogSync.getInstance().getReference();
        ref.child(path).addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    Msg msg = new Msg();
                    msg.Key = dataSnapshot.getKey();
                    msg.Value = dataSnapshot.getValue().toString();
                    msgQueue.add(msg);
                }
            }

            public void onCancelled(SyncError syncError) {
                if (syncError != null) {
                    System.out.println("onCancelled" + syncError.toString());
                }
            }
        });
        return "true";
    }

//    @DllExport(name = "wilddog_pull", ordinal = 4)
    public static String wilddog_pull(String format) {
        String ret;
        if (format.length() == 0) format = "%s,%s";
        if (msgQueue.isEmpty()) {
            ret = "empty";
        } else {
            Msg e = msgQueue.poll();
            ret = String.format(format, e.Key, e.Value);
        }
        return ret;
    }

//    @DllExport(name = "wilddog_remove", ordinal = 5)
    public static String wilddog_remove(String path) {
        SyncReference ref = WilddogSync.getInstance().getReference();
        ref.child(path).removeEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
            }

            public void onCancelled(SyncError syncError) {
            }
        });
        return "true";
    }

    public static void main(String[] args) {
        System.out.println("Hello GameMaker");
        thisApp = new App();
        thisApp.wilddog_init("dragonus");
        thisApp.wilddog_subscribe("players", "value");
        thisApp.wilddog_set("players", "cool");
        String ret;
        do {
            ret = thisApp.wilddog_pull("");
            System.out.println(ret);
        }
        while (ret == "empty");
    }
}
