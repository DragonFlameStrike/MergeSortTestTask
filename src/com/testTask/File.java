package com.testTask;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class File {
    private final String name;
    private String lastElement;
    private long filePointer;
    private int localPointer;
    private boolean canLoad;
    private boolean flagStringsIsEmpty;
    private final ArrayList<String> strings = new ArrayList<>();

    public File(String name) {
        this.filePointer = 0;
        this.localPointer = 0;
        this.name = name;
        this.canLoad = true;
        this.flagStringsIsEmpty = loadNewStrings();
    }

    public boolean loadNewStrings() {

        strings.clear();
        FileReader input = null;
        try {
            input = new FileReader(name);
        } catch (IOException e) {
            FileWriter out = null;
            try {
                out = new FileWriter("log.txt");
                out.write("Cant open " + name);
                out.close();

            } catch (IOException e1){
                e1.printStackTrace();
            }
            e.printStackTrace();
            return true; // flag - strings is Empty
        }
        BufferedReader reader = new BufferedReader(input);
        // skip previous lines
        try {
            for (int i = 0; i < filePointer; i++) {
                String line = reader.readLine();
            }
            // set new 512 lines
            for (int i = 0; i < 512; i++) {
                String line = reader.readLine();
                if (line != null) {
                    if (!line.contains(" "))
                        strings.add(line);
                } else {
                    canLoad = false;
                    input.close();
                    reader.close();
                    break;
                }
            }
            filePointer += 512;
            reader.close();
            input.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        return strings.isEmpty();
    }

    public String getCurrentElement(boolean flagStringType) {
        if (!isNumber(strings.get(localPointer)) && !flagStringType) {
            return "";
        }
        return strings.get(localPointer);
    }

    public String getLastElement() {
        return lastElement;
    }

    public int loadNextElement(boolean flagStrings) {
        if (flagStrings) {
            if (!strings.get(localPointer).isEmpty()) {
                lastElement = strings.get(localPointer);
            }
        } else {
            if (isNumber(strings.get(localPointer)) && !strings.get(localPointer).isEmpty()) {
                lastElement = strings.get(localPointer);
            }
        }
        localPointer++;
        if (localPointer >= strings.size() && canLoad) {
            flagStringsIsEmpty = loadNewStrings();
            localPointer = 0;
        }
        if (localPointer >= strings.size()) {
            return -1;
        }

        return 0;
    }

    public boolean isNumber(String string) {
        int i = 0;
        if (string.length() > 0) {
            if (string.charAt(0) == '-' && string.length() > 1) {
                if (string.charAt(1) != '0') {
                    i++;
                }
            }
            for (; i < string.length(); i++) {
                int chr = string.charAt(i);

                if (chr < '0' || chr > '9') {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public boolean isEmpty() {
        return flagStringsIsEmpty;
    }
}
