package com.testTask;


import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class Main {

    public static void main(String[] args) {
        String output = "log.txt";
        ArrayList<File> inputs = new ArrayList<>();
        boolean flagInputFiles = false; // for args input...
        boolean flagSortAscending = true; // for args -a -d
        boolean flagSortStrings = true; // for args -s -i
        boolean flagControlSetType = false; // for only one SortAscending arg
        boolean flagControlSetDirection = false; // for only one ControlSetType arg
        boolean flagCorrectInput = true; // for catch error input
        for (String arg : args) {
            char checkPrefix = arg.charAt(0);
            if (flagInputFiles) {
                inputs.add(new File(arg));
                if (inputs.get(inputs.size() - 1).isEmpty()) {
                    inputs.remove(inputs.size() - 1);
                }
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
            } else {
                flagCorrectInput = false;
            }

        }
        if (flagCorrectInput && flagControlSetType && flagInputFiles) {
            try (FileWriter out = new FileWriter(output)) {
                mergeSort(inputs, out, flagSortAscending, flagSortStrings);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            FileWriter out = null;
            try {
                out = new FileWriter("log.txt");

                out.write(" Wrong input, try agains\n" +
                            "-a or -d to set SortDirection(Optional)s\n" +
                            "-s or -i to set SortType(Obligatory)s\n" +
                            "After keys must be  name output file and input files(>=1)");
                out.close();

            } catch (IOException e){
                e.printStackTrace();
            }
            System.out.println(" Wrong input, try agains\n" +
                    "-a or -d to set SortDirection(Optional)s\n" +
                    "-s or -i to set SortType(Obligatory)s\n" +
                    "After keys must be  name output file and input files(>=1)");
        }
    }

    public static void mergeSort(ArrayList<File> inputs, FileWriter out, boolean flagSortAscending, boolean flagSortString) throws IOException {
        while (!inputs.isEmpty()) {
            int currentInput = 0;
            // skip empty strings for set nextPushElement
            while (Objects.equals(inputs.get(currentInput).getCurrentElement(flagSortString), "")) {
                if (inputs.get(currentInput).loadNextElement(flagSortString) == -1) {
                    inputs.remove(currentInput);
                    if (currentInput >= inputs.size()) {
                        out.close();
                        return;
                    }
                }
            }
            String nextPushElement = inputs.get(currentInput).getCurrentElement(flagSortString);
            for (int i = 0; i < inputs.size(); i++) {
                boolean flagWasRemovedInput = false;
                while (Objects.equals(inputs.get(i).getCurrentElement(flagSortString), "")) {
                    if (inputs.get(i).loadNextElement(flagSortString) == -1) {
                        inputs.remove(i);
                        flagWasRemovedInput = true;
                        break;
                    }
                }
                if(flagWasRemovedInput) {
                    i--;
                    continue;
                }
                String currentCandidateElement = inputs.get(i).getCurrentElement(flagSortString);
                if (flagSortString) {
                    if (flagSortAscending) {
                        if (biggerThan(nextPushElement, currentCandidateElement)) {
                            nextPushElement = currentCandidateElement;
                            currentInput = i;
                        }
                    } else {
                        if (!biggerThan(nextPushElement, currentCandidateElement)) {
                            nextPushElement = currentCandidateElement;
                            currentInput = i;
                        }
                    }
                } else {
                    if (flagSortAscending) {
                        if (Integer.parseInt(nextPushElement) > Integer.parseInt(currentCandidateElement)) {
                            nextPushElement = currentCandidateElement;
                            currentInput = i;
                        }
                    } else {
                        if (Integer.parseInt(nextPushElement) <= Integer.parseInt(currentCandidateElement)) {
                            nextPushElement = currentCandidateElement;
                            currentInput = i;
                        }
                    }
                }
            }
            out.write(nextPushElement);
            out.write('\n');
            // loadNextElement in currentInput or remove currentInput
            do {
                if (inputs.get(currentInput).loadNextElement(flagSortString) == -1) {
                    inputs.remove(currentInput);
                    break;
                }
                //remove input if not sorted
                if (!Objects.equals(inputs.get(currentInput).getCurrentElement(flagSortString), "")) {
                    if (flagSortString) {
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
                    } else {
                        if (flagSortAscending) {
                            if (Integer.parseInt(inputs.get(currentInput).getLastElement()) > Integer.parseInt(inputs.get(currentInput).getCurrentElement(true))) {
                                inputs.remove(currentInput);
                                break;
                            }
                        } else {
                            if (Integer.parseInt(inputs.get(currentInput).getLastElement()) < Integer.parseInt(inputs.get(currentInput).getCurrentElement(true))) {
                                inputs.remove(currentInput);
                                break;
                            }
                        }
                    }
                }
            } while (Objects.equals(inputs.get(currentInput).getCurrentElement(flagSortString), ""));
        }
        out.close();
    }

    public static boolean biggerThan(String string1, String string2) {
        if (string1.length() > string2.length()) {
            return true;
        } else if (string1.length() < string2.length()) {
            return false;
        } else {
            for (int i = 0; i < string1.length(); i++) {
                if (string1.charAt(i) > string2.charAt(i)) {
                    return true;
                } else if (string1.charAt(i) < string2.charAt(i)) {
                    return false;
                }
            }
            return false;
        }
    }
}

