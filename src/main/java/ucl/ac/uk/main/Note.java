package ucl.ac.uk.main;

import java.util.ArrayList;

public class Note {
    private String title;
    private String date_created;
    private String date_modified;
    private String contents;
    private String type;


    public Note(ArrayList<String> contents){
        this.title = contents.get(0);
        this.date_created = contents.get(1);
        this.date_modified = contents.get(2);
        this.contents = contents.get(3);
        this.type = contents.get(4);
    }

    //To prevent errors in reading the file. ALl commas in the title and contents is changed to ~~~
    public String getTitle(){
        return this.title.replace("~~~", ",");
    }
    public String getDate_modified(){
        return this.date_modified;
    }
    public String getDate_created(){
        return this.date_created;
    }
    public String getContents(){
        return this.contents.replace("~~~", ",");
    }
    public String getType(){
        return this.type;
    }

    public void setTitle(String title){this.title = title;}
    public void setContents(String contents){this.contents = contents;}
    public void setDate_modified(String date_modified){this.date_modified = date_modified;}
    public void setType(String type){this.type = type;}

}
