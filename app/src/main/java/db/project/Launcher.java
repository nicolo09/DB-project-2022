package db.project;

import java.nio.file.Paths;

import db.project.utils.PersonsLoader;
import javafx.application.Application;

public class Launcher {
    
    public static void main(String[] args) {
        if (args.length > 0) {
            switch (args[0]) {
                case "--help":
                    System.out.println("Usage: java -jar <jar-file> [--help]");
                    System.out.println("");
                    System.out.println("--help: Prints this help message.");
                    System.out.println("");
                    System.out.println("Example: java -jar <jar-file>");
                    System.out.println("");
                    System.out.println("Example: java -jar <jar-file> --help");
                    System.out.println("");
                    System.exit(0);
                    break;
                case "--load-persons":
                    if (Paths.get(args[1]).toFile().exists()) {
                        System.out.println("Loading persons...");
                        PersonsLoader loader = new PersonsLoader(args[1], App.DBUSERNAME, App.DBPASSWORD);
                        loader.loadInDB();
                    } else {
                        System.out.println("File not found.");
                    }
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
