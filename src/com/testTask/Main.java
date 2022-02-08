package com.testTask;


import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException {
        String output = "";
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
        if(flagCorrectInput) {
            try {
                mergeSort(inputs, output, flagSortAscending, flagSortStrings);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            System.out.println("Wrong input, try again \n " +
                    " -a or -d to set SortDirection(Optional) \n" +
                    " -s or -i to set SortType(Obligatory) \n" +
                    "After keys must be  name output file and input files(>=1)"
            );
        }
    }
    public static void mergeSort(ArrayList<File> inputs, String output,boolean flagSortAscending, boolean flagSortString) throws IOException {
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

