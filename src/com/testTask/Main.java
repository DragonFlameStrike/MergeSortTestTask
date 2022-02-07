package com.testTask;


import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException {
        String output = "";
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
        mergeSort(inputs, output);

    }
    public static void mergeSort(ArrayList<File> inputs, String output) throws IOException {
        FileWriter out = new FileWriter(output);
        while(!inputs.isEmpty()){
            int currentInput=0;
            String minElement = inputs.get(currentInput).getCurrentElement();
            for(int i=0;i<inputs.size();i++){
                String currentMinElement = inputs.get(i).getCurrentElement();
                if(Integer.parseInt(minElement)>Integer.parseInt(currentMinElement)){
                    minElement=currentMinElement;
                    currentInput=i;
                }
            }
            if(inputs.get(currentInput).loadNextElement() == -1){
                inputs.remove(currentInput);
            }

            out.write(minElement);
            out.write('\n');
        }
        out.close();
    }
}

