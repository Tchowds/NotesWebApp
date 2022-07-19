package ucl.ac.uk.main;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Model {
    //Multiple note objects stored in an arrayList. Can be empty. Note objects are private.
    private ArrayList<Note> Notes;


    public Model(File file) throws Exception {
        //Constructor reads file into arrayList to be constructed into a note object
        this.Notes = new ArrayList<>();
        int counter = 0;
        ArrayList<String> entry = new ArrayList<>();
        Scanner scanner = new Scanner(file);
        //delimiter is , due to notes stored in csv file
        scanner.useDelimiter(",");
        //Information sent to made into note and added to notes after five fields reset.
        //Entry reset after five fields
        while (scanner.hasNext()) {
            String field = scanner.next();
            entry.add(field);
            counter += 1;
            if (counter > 4) {
                this.Notes.add(new Note(entry));
                counter = 0;
                entry.clear();
            }
        }
    }

    //returns array list in format [title, date created, date modified, contents, type]
    public ArrayList<String> getNoteContents(int index) {
        Note n = this.Notes.get(index);
        ArrayList<String> entry = new ArrayList<>();
        entry.add(n.getTitle());
        entry.add(n.getDate_created());
        entry.add(n.getDate_modified());
        entry.add(n.getContents());
        entry.add(n.getType());
        return entry;
    }

    //Using the type parameter from the index page
    //"Sorted" = sorts the titles alphabetically
    //"date" or "mod"  or else = sorts by dateCreated/modified
    public ArrayList<String> getNoteNames(String type) {
        ArrayList<String> titles = new ArrayList<>();
        for (Note n : this.Notes) {
            titles.add(n.getTitle());
        }

        if (type != null && type.equals("sorted")) {
            Collections.sort(titles, String.CASE_INSENSITIVE_ORDER);
        } else if (type != null) {
            titles = this.sortByDates(titles, type);
        }

        return titles;
    }

    //Returns the first sentence or line of text of the contents of the note
    public String getNoteSummary(int index) {
        Note n = this.Notes.get(index);
        int iend = n.getContents().indexOf(".");
        if (n.getContents().indexOf("\n") < iend) {
            iend = n.getContents().indexOf("\n");
        }

        if (iend != -1) {
            return n.getContents().substring(0, iend);
        } else {
            return n.getContents();
        }
    }

    public int getLength() {
        return this.Notes.size();
    }


    public ArrayList<String> sortByDates(ArrayList<String> titles, String type) {
        ArrayList<String> dates = new ArrayList<>();
        ArrayList<Integer> indexes = new ArrayList<>();
        for (Note n : this.Notes) {
            if (type.equals("date")) {
                dates.add(n.getDate_created());
            } else {
                dates.add(n.getDate_modified());
            }
        }
        ArrayList<String> oldDates = new ArrayList<>(dates);
        Collections.sort(dates);
        //Sorts the dates (date created or modified depending on type parameter

        for (String date : dates) {
            indexes.add(oldDates.indexOf(date));
        }
        //Retrieves the original indexes of those dates before sorting

        ArrayList<String> newTitles = new ArrayList<>();
        for (int i : indexes) {
            newTitles.add(titles.get(i));
        }
        //Makes an array of the titles sorted according to the new indexes of the sorted dates
        Collections.reverse(newTitles);
        //Titles list needs to be reversed otherwise would give titles in order of old to new
        return newTitles;
    }

    public ArrayList<String> search(String searchstring){
        ArrayList<String> names = this.getNoteNames(null);
        ArrayList<String> lowerNames = new ArrayList<>();

        for (int i = 0; i < names.size(); i++) {
            String lowerName = names.get(i).toLowerCase();
            lowerNames.add(lowerName);
        }

        ArrayList<String> matches = new ArrayList<>();
        //Lower case of search term compare to lowercase note titles (case insensitive)
        //Checks to see if search term is a substring of any of the note titles

        for (String name : lowerNames) {
            if (name.contains(searchstring)) {
                matches.add(names.get(lowerNames.indexOf(name)));
            }
        }

        return matches;
    }

    //Every field of a note could be updates except date created which stays constant
    public void updateNote(int index, String title, String contents) {
        this.Notes.get(index).setTitle(title.replaceAll("[^ A-Za-z0-9-._~:/?#@!$&'()*+,;=]", "").replaceAll(",", "~~~"));
        this.Notes.get(index).setContents(contents.replace(",", "~~~"));
        //To avoid confusion with commas in the csv, replace , in both title and contents when updated in the csv

        LocalDateTime unformattedDate = LocalDateTime.now();
        DateTimeFormatter formattedDate = DateTimeFormatter.ofPattern(("yyyy-MM-dd HH:MM:ss"));
        String dateFinal = unformattedDate.format(formattedDate);
        //Current date in format given assigned to date modified
        this.Notes.get(index).setDate_modified(dateFinal);
        this.Notes.get(index).setType(this.checkIfUrl(contents));
        //type depends on whether contents is an url or not
        this.updateFile();
    }


    public void updateFile() {
        try (PrintWriter writer = new PrintWriter("./data/notes.csv")) {
            //Goes through all the current notes and builds a string of every single field from every note

            StringBuilder builder = new StringBuilder();
            for (Note n : this.Notes) {
                builder.append(n.getTitle().replace(",", "~~~") + ",");
                builder.append(n.getDate_created() + ",");
                builder.append(n.getDate_modified() + ",");
                builder.append(n.getContents().replace(",", "~~~") + ",");
                builder.append(n.getType() + ",");
            }
            writer.write(builder.toString());
            //Writes it to the specified file name

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    //Checks to see if contents is a url and chooses type depending on this
    public String checkIfUrl(String possibleUrl) {
        try {
            new URL(possibleUrl).toURI();
            return "URL";
        } catch (Exception e) {
            return "text";
        }
    }

    public void addNote(String title, String contents) {
        ArrayList<String> entry = new ArrayList<>();
        entry.add(title.replaceAll("[^ A-Za-z0-9-._~:/?#@!$&'()*+,;=]", "").replaceAll(",", "~~~"));
        //entry titles and contents replace , with ~~~ for csv formatting.
        //Title has extra replace function to remove all URL invalid symbols from the title to prevent errors when viewing it

        LocalDateTime unformattedDate = LocalDateTime.now();
        DateTimeFormatter formattedDate = DateTimeFormatter.ofPattern(("yyyy-MM-dd HH:MM:ss"));
        String dateFinal = unformattedDate.format(formattedDate);

        entry.add(dateFinal);

        entry.add(dateFinal);
        entry.add(contents.replace(",", "~~~"));

        entry.add(this.checkIfUrl(contents));
        this.Notes.add(new Note(entry));
        this.updateFile();
    }


    public void deleteNote(int index) {
        this.Notes.remove(index);
        this.updateFile();
    }

    //Every time a note is added, updates, or deleted the file needs to be updated as the notes arrayList has been changed
    //the updateFile function is called at the end of those functions.


}
