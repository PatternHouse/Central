package org.openaod.patternhouse.util;

import java.io.File;
import java.util.Vector;

public class FireExtensions {
	
	protected static int read(String filename) {
        return VCache.storeCache(DirectStreamReader.read(filename));
    }

    protected static void write(String filename, int vcacheindex) {
        DirectStreamWriter.write(VCache.getCache(vcacheindex), filename);
    }

    protected static void write(String filename, Vector<String> contents) {
        DirectStreamWriter.write(contents, filename);
    }

    protected static int replace(int vcacheindex, String target, String replacement) {
        Vector<String> reference = VCache.getCache(vcacheindex);
        Vector<String> replaced = new Vector<>(1,1);
        for(String str:reference) {
            replaced.addElement(str.replace(target, replacement));
        }
        return VCache.storeCache(replaced);
    }

    protected static int replace(int vcacheindex1, String target, int vcacheindex2) {
        Vector<String> reference = VCache.getCache(vcacheindex1);
        Vector<String> replacement = VCache.getCache(vcacheindex2);
        Vector<String> replaced = new Vector<>(1,1);
        for(String str:reference) {
            if(str.equals(target)) {
                for(String repl:replacement) {
                    replaced.addElement(repl);
                }
            } else replaced.addElement(str);
        }
        return VCache.storeCache(replaced);
    }

    protected static int listFiles(String directory) {
        File f = new File(directory);
        File[] listing = f.listFiles();
        Vector<String> vec = new Vector<>(1,1);
        for(File file:listing) vec.addElement(file.getPath());
        return VCache.storeCache(vec);
    }

    protected static boolean exists(String filename) {
        return (new File(filename)).exists();
    }

    protected static void mkdir(String dir) {
        File f = new File(dir);
        if(f.exists()) if(f.isDirectory()) return;
        (new File(dir)).mkdir();
    }

    protected static String getProperName(String st) {
        File f = new File(st);
        return f.getName();
    }

    protected static Vector<String> get(int vcacheindex) {
        return VCache.getCache(vcacheindex);
    }
	
}
