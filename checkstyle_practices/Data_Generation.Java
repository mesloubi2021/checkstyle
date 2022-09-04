package anotherPackage;

import java.io.BufferedInputStream; 
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**use of:
buffered input stream:
Adds functionality to another input stream: the ability to buffer the input and to support the mark 
reset methods
file:
representation of file or directory pathname **/
public class Test {

    public static int countLines(File filename) throws IOException 
    {
        InputStream is = new BufferedInputStream(new FileInputStream(filename));
        try {
            byte[] c = new byte[1024]; //byte array of size 1024 created 

            int readChars = is.read(c); // for reading characters 
            if (readChars == -1) 
            {
                // bail out if nothing to read
                return 0;
            }

            // make it easy for the optimizer to tune this loop
            int count = 0; //for couting thr characters 
            while (readChars == 1024) 
            { // count until the character count reaches to 1024
                for (int i = 0; i < 1024; ) 
                {// for loop for iterating till 1024
                    if (c[i++] == '\n') 
                    { // if c[1]=='\n' then increment count  
                        ++count;
                    }
                }
                readChars = is.read(c);
            }

            // count remaining characters
            while (readChars != -1) {
                for (int i = 0; i < readChars; ++i) {
                    if (c[i] == '\n') {
                        ++count;
                    }
                }
                readChars = is.read(c);
            }

            return count == 0 ? 1 : count;
        }
        finally {
            is.close();
        }
    }
   public static void main(String[] args) throws IOException 
   {
        List<Path> resources = Files.walk(
                        Paths.get("/home/vyom/IdeaProjects/checkstyle/src/test/resources"))
                .filter(Files::isRegularFile)
                .filter(path -> path.toFile().getName().matches("Input.*\\.java"))
                .collect(Collectors.toList());
        List<Path> resourcesNonCompilable = Files.walk(
                        Paths.get("/home/vyom/IdeaProjects/checkstyle/src/test/resources-noncompilable"))
                .filter(Files::isRegularFile)
                .filter(path -> path.toFile().getName().matches("Input.*\\.java"))
                .collect(Collectors.toList());
        resources.addAll(resourcesNonCompilable);
        resources.forEach(path -> System.out.println(path.toFile().getName()));
        int numOfLines = 0;
        List<Integer> sizeOfFile = new ArrayList<>();
        for (Path path : resources) 
        {
            int numOfLinesInFile = countLines(path.toFile());
            numOfLines += numOfLinesInFile;
            sizeOfFile.add(numOfLinesInFile);
        }
        Collections.sort(sizeOfFile);
        System.out.println("Total Num Of Lines: " + numOfLines);
        System.out.println("Total Num Of Files: " + sizeOfFile.size());
        double median = ((double) sizeOfFile.get(sizeOfFile.size() / 2) + (double) sizeOfFile.get(
                (sizeOfFile.size() / 2) - 1)) / 2;

        System.out.println("Median of number of lines: " + median);
        System.out.println("The average number of lines: " + (double) numOfLines / sizeOfFile.size());
    }

}

  
  
