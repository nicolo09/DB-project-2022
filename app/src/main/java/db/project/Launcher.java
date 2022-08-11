package db.project;

import java.nio.file.Paths;

import db.project.utils.PersonsLoader;
import javafx.application.Application;

public class Launcher {
    
    public static void main(String[] args) {
        if (args.length > 0) {
            switch (args[0]) {
                case "--help":
                    System.out.println("Usage: java -jar <jar-file> [--help] [--load-persons <file>]");
                    System.out.println("");
                    System.out.println("Example: java -jar <jar-file>");
                    System.out.println("");
                    System.out.println("--help: Prints this help message.");
                    System.out.println("");
                    System.out.println("Example: java -jar <jar-file> --help");
                    System.out.println("");
                    System.out.println("--load-persons <file-path> <dbuser> <dbpass>: Loads persons from the given CSV file (name|surname|CF) into the db.");
                    System.out.println("");
                    System.out.println("Example: java -jar <jar-file> --load-persons <file-path> <dbuser> <dbpass>");
                    System.out.println("");
                    System.out.println("--credentials <dbuser> <dbpass>: Login immediately into the db with the given credentials.");
                    System.out.println("");
                    System.out.println("Example: java -jar <jar-file> --credentials <dbuser> <dbpass>");
                    System.exit(0);
                    break;
                case "--load-persons":
                    if (Paths.get(args[1]).toFile().exists()) {
                        System.out.println("Loading persons..."); 
                        new PersonsLoader(args[1], args[2], args[3]).loadInDB();
                    } else {
                        System.out.println("File not found.");
                    }
                    break;
                case "--credentials":
                    Application.launch(App.class, args[1], args[2]);
                    break;
                default:
                    System.out.println("Unknown argument: " + args[0]);
                    System.out.println("");
                    System.out.println("Usage: java -jar <jar-file> [--help]");
                    System.out.println("");
                    System.out.println("--help: Prints this help message.");
                    System.out.println("");
                    System.out.println("Example: java -jar <jar-file>");
                    System.out.println("");
                    System.out.println("Example: java -jar <jar-file> --help");
                    System.out.println("");
                    System.exit(1);
                    break;
            }
        }
        else {
            Application.launch(App.class);
        }
    }
    
}
