import java.util.ArrayList;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.FileAlreadyExistsException;

public class Parser {
    private String fileLoc;
    public Parser(String fileLoc) {
        this.fileLoc = fileLoc;
    }

    public ArrayList<Task> getDatabase(String fileLoc) {
        ArrayList<Task> listing = new ArrayList<>();
        Path path = Paths.get(fileLoc);
        try {
            Files.createFile(path);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        File file = new File(fileLoc);
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));

            if (!file.createNewFile()) {
                String st;
                while ((st = br.readLine()) != null) {
                    listing.add(parseList(st));
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return listing;
    }

    public ArrayList<Task> getDatabase() {
        return this.getDatabase(fileLoc);
    }

    /**
     * Returns Task object from string.
     * String example: D|true|read book|by|2019-02-10
     *
     * @param s string for object parameters.
     * @return Task of string.
     */

    private Task parseList(String s) {
        String[] params = s.split("\\|");

        if (params[0].equals("D")) {
            return new Deadline(params[2], params[3], params[4], !params[1].equals("false"));
        } else if (params[0].equals("E")) {
            return new Event(params[2], params[3], params[4], !params[1].equals("false"));
        } else {
            return new Task(params[2], !params[1].equals("false"));
        }
    }
}