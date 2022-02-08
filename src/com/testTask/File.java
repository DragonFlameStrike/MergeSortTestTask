package com.testTask;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class File {
    private final String name;
    private long filePointer;
    private int localPointer;
    private boolean canLoad;
    private final ArrayList<String> strings = new ArrayList<>();

    public File(String name) throws IOException {
        this.filePointer = 0;
        this.localPointer = 0;
        this.name = name;
        this.canLoad = true;
        loadNewStrings();
    }

    public void loadNewStrings() throws IOException {
        try {
            strings.clear();
            FileReader input = new FileReader("files/"+name);
            BufferedReader reader = new BufferedReader(input);
            for(int i = 0; i< filePointer; i++){
                String line = reader.readLine();
            }
            for (int i = 0; i < 512; i++) {
                String line = reader.readLine();
                if(line != null){
                    strings.add(line);
                }
                else {
                    canLoad = false;
                    input.close();
                    reader.close();
                    break;
                }
            }
            filePointer +=512;
            reader.close();
            input.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String getCurrentElement() {

        return strings.get(localPointer);
    }

    public int loadNextElement() throws IOException {
        localPointer++;
        if(localPointer>=strings.size() && canLoad){
            loadNewStrings();
            localPointer=0;
        }
        else if(localPointer>=strings.size()){
            return -1;
        }
        return 0;
    }
}
