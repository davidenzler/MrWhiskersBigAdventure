package Main;

import FileIO.EZFileRead;
import FileIO.EZFileWrite;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.Vector;

public class ArtFileUpdater {
    public static void update() throws IOException {
        File artDir = new File("./Art");
        String dirName = "Art/";
        String artTxtFilename = "./Art.txt";

        Vector<String> listFilesArtDir = new Vector<String>();
        HashMap<String, String> artTxtContents = new HashMap<>();
        int numLinesArtTxt;
        EZFileRead fileReader = new EZFileRead(artTxtFilename);

        numLinesArtTxt = fileReader.getNumLines();
        gatherArtFiles(dirName, artDir.listFiles(), listFilesArtDir);

        if(numLinesArtTxt != listFilesArtDir.size()) {
            getArtTxtContents(artTxtContents, fileReader, numLinesArtTxt);
            EZFileWrite artTxtWriter = new EZFileWrite(artTxtFilename, true); // EZFileWrite now has an overloaded constructor which allows for append mode
            for (String artDirFilename: listFilesArtDir) {
                if(!artTxtContents.containsKey(artDirFilename)) {
                    String line = artDirFilename + "*";

                    // this section will parse out the "/" and file type from the filename
                    // example: "Art/Animation/tree.png" => "tree"
                    StringTokenizer fileNameParser = new StringTokenizer(artDirFilename, "/");
                    String sprite_tag = "";
                    while(fileNameParser.hasMoreTokens()) {
                        sprite_tag = fileNameParser.nextToken();
                    }
                    StringTokenizer filetypeParser = new StringTokenizer(sprite_tag, ".");
                    sprite_tag = filetypeParser.nextToken();

                    line += sprite_tag;
                    artTxtWriter.writeLine(line);
                }
            }
            artTxtWriter.saveFile();
        }
    }

    // recursive function call => extracts all path names from art directory and subdirectories
    private static void gatherArtFiles(String fileName, File[] files, Vector<String> listFilesArtDir) {
        if (files == null) return;
        for (File file : files) {
            if(file.isDirectory()) {
                gatherArtFiles( (fileName + file.getName() + "/") ,file.listFiles(), listFilesArtDir);
            } else {
                listFilesArtDir.add(fileName + file.getName());
            }
        }
    }

    private static void getArtTxtContents(HashMap<String, String> artTxtContent, EZFileRead fileReader, int numLines) {
        for(int i=0; i<numLines; i++ ) {
            String rawOutput = fileReader.getLine(i);
            StringTokenizer output = new StringTokenizer(rawOutput, "*");
            String key = output.nextToken();
            String value = output.nextToken();
            artTxtContent.put(key, value);
        }
    }
}
