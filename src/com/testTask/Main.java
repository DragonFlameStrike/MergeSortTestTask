package com.testTask;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException {
        String output;
        ArrayList<File> inputs = new ArrayList<>();
        boolean flagInputFiles = false;
        for (String arg : args) {
            char check_postfix = arg.charAt(0);
            if (flagInputFiles) {
                inputs.add(new File(arg));
            }
            if (check_postfix != '-' && !flagInputFiles) {
                output = arg;
                flagInputFiles = true;
            }
        }

    }
}
