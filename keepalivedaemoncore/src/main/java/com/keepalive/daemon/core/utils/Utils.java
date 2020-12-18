package com.keepalive.daemon.core.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.keepalive.daemon.core.utils.Logger.TAG;

public class Utils {

    private volatile static String sProcessName = null;

    public static String getProcessName() {
        if (sProcessName != null) {
            return sProcessName;
        } else {
            BufferedReader br = null;
            try {
                File file = new File("/proc/self/cmdline");
                br = new BufferedReader(new FileReader(file));
                sProcessName = br.readLine().trim();
                Logger.v(TAG, "!! execute [/proc/self/cmdline] >>> " + sProcessName);
                return sProcessName;
            } catch (Throwable th) {
                th.printStackTrace();
                return null;
            } finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static void getCurrSOLoaded() {
        List<String> allSOLists = null;
        // 当前应用的进程ID
        int pid = android.os.Process.myPid();
        String path = "/proc/" + pid + "/maps";
        Logger.v(TAG, "maps path: " + path);
        File file = new File(path);
        if (file.exists() && file.isFile()) {
            allSOLists = readFileByLines(file.getAbsolutePath());
        } else {
            Logger.w(TAG, "不存在[" + path + "]文件.");
        }

        if (allSOLists == null || allSOLists.size() == 0) {
            return;
        }
    }

    private static List<String> readFileByLines(String fileName) {
        List<String> allSOLists = new ArrayList<>();
        File file = new File(fileName);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                if (tempString.endsWith(".so")) {
                    int index = tempString.indexOf(File.separator);
                    if (index != -1) {
                        String str = tempString.substring(index);
                        if (!allSOLists.contains(str)) {
                            Logger.v(TAG, "str: " + str);
                            // 所有so库（包括系统的，即包含/system/目录下的）
                            allSOLists.add(str);
                        }
                    }
                }
            }
//            Logger.v(Logger.TAG, "allSOLists: " + allSOLists);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return allSOLists;
    }
}
