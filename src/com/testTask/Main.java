package com.testTask;


import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class Main {

    public static void main(String[] args){
        String output = "out.txt";
        ArrayList<File> inputs = new ArrayList<>();
        boolean flagInputFiles = false;
        boolean flagSortAscending = true ;
        boolean flagSortStrings = true;
        boolean flagControlSetType = false;
        boolean flagControlSetDirection = false;
        boolean flagCorrectInput = true;
        for (String arg : args) {
            char checkPrefix = arg.charAt(0);
            if (flagInputFiles) {
                inputs.add(new File(arg));
            }
            //first file without prefix must be output.txt
            else if (checkPrefix != '-') {
                output = arg;
                flagInputFiles = true;
            }
            // single set direction
            else if ((arg.equals("-a") || arg.equals("-d")) && !flagControlSetDirection) {
                flagControlSetDirection = true;
                flagSortAscending = arg.equals("-a");
            }
            // single set sortType
            else if ((arg.equals("-s") || arg.equals("-i")) && !flagControlSetType) {
                flagControlSetType = true;
                flagSortStrings = arg.equals("-s");
            }
            else {
                flagCorrectInput = false;
            }

        }
        if(flagCorrectInput && flagControlSetType && flagInputFiles) {
            try (FileWriter out = new FileWriter(output)){
                mergeSort(inputs, out, flagSortAscending, flagSortStrings);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            System.out.println(" Wrong input, try agains\n" +
                    "-a or -d to set SortDirection(Optional)s\n" +
                    "-s or -i to set SortType(Obligatory)s\n" +
                    "After keys must be  name output file and input files(>=1)");
        }
    }
    public static void mergeSort(ArrayList<File> inputs, FileWriter out,boolean flagSortAscending, boolean flagSortString) throws IOException {

        while(!inputs.isEmpty()){
            int currentInput=0;
            while(Objects.equals(inputs.get(currentInput).getCurrentElement(flagSortString), "")) {
                if (inputs.get(currentInput).loadNextElement() == -1) {
                    inputs.remove(currentInput);
                    currentInput++;
                    if(currentInput>inputs.size()){
                        out.close();
                        return;
                    }
                }
            }
            String nextPushElement = inputs.get(currentInput).getCurrentElement(flagSortString);
            for(int i=0;i<inputs.size();i++) {
                String currentMinElement = inputs.get(i).getCurrentElement(flagSortString);
                if (flagSortAscending) {
                    if (biggerThan(nextPushElement, currentMinElement)) {
                        nextPushElement = currentMinElement;
                        currentInput = i;
                    }
                } else {
                    if (!biggerThan(nextPushElement, currentMinElement)) {
                        nextPushElement = currentMinElement;
                        currentInput = i;
                    }
                }
            }
            do {
                if (inputs.get(currentInput).loadNextElement() == -1) {
                    inputs.remove(currentInput);
                    break;
                }
                //remove input if not sorted
                if(!Objects.equals(inputs.get(currentInput).getCurrentElement(flagSortString), "")) {
                    if (flagSortAscending) {
                        if (biggerThan(inputs.get(currentInput).getLastElement(), inputs.get(currentInput).getCurrentElement(true))) {
                            inputs.remove(currentInput);
                            break;
                        }
                    } else {
                        if (biggerThan(inputs.get(currentInput).getCurrentElement(true), inputs.get(currentInput).getLastElement())) {
                            inputs.remove(currentInput);
                            break;
                        }
                    }
                }
            } while(Objects.equals(inputs.get(currentInput).getCurrentElement(flagSortString), ""));
            out.write(nextPushElement);
            out.write('\n');
        }
        out.close();
    }
    public static boolean biggerThan(String string1, String string2){
        if(string1.length() > string2.length()){
            return true;
        }
        else if(string1.length() < string2.length()){
            return false;
        }
        else {
            for(int i=0;i<string1.length();i++){
                if(string1.charAt(i) > string2.charAt(i)){
                    return true;
                }
                else if(string1.charAt(i) < string2.charAt(i)){
                    return false;
                }
            }
            return false;
        }
    }

}

