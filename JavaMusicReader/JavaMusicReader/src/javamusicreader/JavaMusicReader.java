/*
 ༼ つ ◕_◕ ༽つ
 Project:Music Reader In Java
 Name:Weston Wingo
 Date:Feburary/9/17
 Description:Play music from a text file
 ༼ つ ◕_◕ ༽つ
 */
package javamusicreader;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javamusicreader.JavaMusicReader.BeepNote;
import javamusicreader.JavaMusicReader.FormalNote;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import org.jfugue.player.ManagedPlayer;
import org.jfugue.player.Player;

public class JavaMusicReader extends Application {                 
    //Rows are the octaves, Colums are the notes                                       
    //C       C#       D        D#       E        F        F#       G        G#       A       A#       B
    static final double[][] noteFrequencyArray =
    {{16.35,  17.32,   18.35,   19.45,   20.6,    21.83,   23.12,   24.5,    25.96,   27.5,   29.14,   30.87},  //0                                            
    {32.7,    34.65,   36.71,   38.89,   41.2,    43.65,   46.25,   49.0,    51.91,   55.0,   58.27,   61.74},  //1
    {65.41,   69.3,    73.42,   77.78,   82.41,   87.31,   92.5,    98.0,    103.83,  110.0,  116.54,  123.47}, //2
    {130.81,  138.59,  146.83,  155.56,  164.81,  174.61,  185.0,   196.0,   207.65,  220.0,  233.08,  246.94}, //3
    {261.63,  277.18,  293.66,  311.13,  329.63,  349.23,  369.99,  392.0,   415.3,   440.0,  466.16,  493.88}, //4
    {523.25,  554.37,  587.33,  622.25,  659.25,  698.46,  739.99,  783.99,  830.61,  880.0,  932.33,  987.77}, //5
    {1046.5,  1108.73, 1174.66, 1244.51, 1318.51, 1396.91, 1479.98, 1567.98, 1661.22, 1760.0, 1864.66, 1975.53},//6
    {2093.0,  2217.46, 2349.32, 2489.02, 2637.02, 2793.83, 2959.96, 3135.96, 3322.44, 3520.0, 3729.31, 3951.07},//7
    {4186.01, 4434.92, 4698.63, 4978.03, 5274.04, 5587.65, 5919.91, 6271.93, 6644.88, 7040.0, 7458.62, 7902.13} //8
    };
    //this is the array of the possible instruments 
    static final String[] instrumentList = {
        "PIANO",
        "ACOUSTIC_GRAND",
        "BRIGHT_ACOUSTIC",
        "ELECTRIC_GRAND",
        "HONKEY_TONK",
        "ELECTRIC_PIANO",
        "ELECTRIC_PIANO1",
        "ELECTRIC_PIANO2",
        "HARPISCHORD",
        "CLAVINET",
        "CELESTA",
        "GLOCKENSPIEL",
        "MUSIC_BOX",
        "VIBRAPHONE",
        "MARIMBA",
        "XYLOPHONE",
        "TUBULAR_BELLS",
        "DULCIMER",
        "DRAWBAR_ORGAN",
        "PERCUSSIVE_ORGAN",
        "ROCK_ORGAN",
        "CHURCH_ORGAN",
        "REED_ORGAN",
        "ACCORDIAN",
        "HARMONICA",
        "TANGO_ACCORDIAN",
        "GUITAR",
        "NYLON_STRING_GUITAR",
        "STEEL_STRING_GUITAR",
        "ELECTRIC_JAZZ_GUITAR",
        "ELECTRIC_CLEAN_GUITAR",
        "ELECTRIC_MUTED_GUITAR",
        "OVERDRIVEN_GUITAR",
        "DISTORTION_GUITAR",
        "GUITAR_HARMONICS",
        "ACOUSTIC_BASS",
        "ELECTRIC_BASS_FINGER",
        "ELECTRIC_BASS_PICK",
        "FRETLESS_BASS",
        "SLAP_BASS_1",
        "SLAP_BASS_2",
        "SYNTH_BASS_1",
        "SYNTH_BASS_2",
        "VIOLIN",
        "VIOLA",
        "CELLO",
        "CONTRABASS",
        "TREMOLO_STRINGS",
        "PIZZICATO_STRINGS",
        "ORCHESTRAL_STRINGS",
        "TIMPANI",
        "STRING_ENSEMBLE_1",
        "STRING_ENSEMBLE_2",
        "SYNTH_STRINGS_1",
        "SYNTH_STRINGS_2",
        "CHOIR_AAHS",
        "VOICE_OOHS",
        "SYNTH_VOICE",
        "ORCHESTRA_HIT",
        "TRUMPET",
        "TROMBONE",
        "TUBA",
        "MUTED_TRUMPET",
        "FRENCH_HORN",
        "BRASS_SECTION",
        "SYNTHBRASS_1",
        "SYNTHBRASS_2",
        "SOPRANO_SAX",
        "ALTO_SAX",
        "TENOR_SAX",
        "BARITONE_SAX",
        "OBOE",
        "ENGLISH_HORN",
        "BASSOON",
        "CLARINET",
        "PICCOLO",
        "FLUTE",
        "RECORDER",
        "PAN_FLUTE",
        "BLOWN_BOTTLE",
        "SKAKUHACHI",
        "WHISTLE",
        "OCARINA",
        "LEAD_SQUARE",
        "LEAD_SAWTOOTH",
        "LEAD_CALLIOPE",
        "LEAD_CHIFF",
        "CHIFF",
        "LEAD_CHARANG",
        "LEAD_VOICE",
        "VOICE",
        "LEAD_FIFTHS",
        "FIFTHS",
        "LEAD_BASSLEAD",
        "PAD_NEW_AGE",
        "PAD_WARM",
        "WARM",
        "PAD_POLYSYNTH",
        "PAD_CHOIR",
        "CHOIR",
        "PAD_BOWED",
        "BOWED",
        "PAD_METALLIC",
        "PAD_HALO",
        "HALO",
        "PAD_SWEEP",
        "SWEEP",
        "FX_RAIN",
        "RAIN",
        "FX_SOUNDTRACK",
        "FX_CRYSTAL",
        "FX_ATMOSPHERE",
        "FX_BRIGHTNESS",
        "FX_GOBLINS",
        "FX_ECHOES",
        "ECHOES",
        "FX_SCI-FI",
        "SCI-FI",
        "SITAR",
        "BANJO",
        "SHAMISEN",
        "KOTO",
        "KALIMBA",
        "BAGPIPE",
        "FIDDLE",
        "SHANAI",
        "TINKLE_BELL",
        "AGOGO",
        "STEEL_DRUMS",
        "WOODBLOCK",
        "TAIKO_DRUM",
        "MELODIC_TOM",
        "SYNTH_DRUM",
        "REVERSE_CYMBAL",
        "GUITAR_FRET_NOISE",
        "BREATH_NOISE",
        "SEASHORE",
        "BIRD_TWEET",
        "TELEPHONE_RING",
        "HELICOPTER",
        "APPLAUSE",
        "GUNSHOT"};
    static ArrayList<Thread> threadList = new ArrayList<>();
    static ArrayList<FormalNote> noteList;
    Player player;
    File fileToPlay = null;
    //set the defaults if there is no specified BPM or INSTRUMENT
    int bpm = 140;
    String instrument = "GUITAR";
    public static void main(String[] args) {
        Application.launch(args);
    }    
    
    @Override
    public void start(Stage stage) {
        HBox pane = new HBox();
        VBox pane2 = new VBox();
        FileChooser fileSelector = new FileChooser();
        Button openFile = new Button("Choose a file to play music from");
        Button stopMusic = new Button("Stop");
        Button playMusic = new Button("Play");
        //This is the text field that the user can enter music into and play from
        //I just put this in here to show the format that the text files need to be in
        TextArea noteField = new TextArea("This is one of Teris' themes:\nAccordian\n" +
        "140\n" +
        "A	4	1/4\n" +
        "E	4	1/8\n" +
        "F	4	1/8\n" +
        "G	4	1/8\n" +
        "A	4	1/16\n" +
        "G	4	1/16\n" +
        "F	4	1/8\n" +
        "E	4	1/8\n" +
        "D	4	1/4\n" +
        "D	4	1/8\n" +
        "F	4	1/8\n" +
        "A	4	1/4\n" +
        "G	4	1/8\n" +
        "F	4	1/8\n" +
        "E	4	3/8\n" +
        "F	4	1/8\n" +
        "G	4	1/4\n" +
        "A	4	1/4\n" +
        "F	4	1/4\n" +
        "D	4	1/4\n" +
        "D	4	1/2\n" +
        "Rest		1/8\n" +
        "G	4	1/4\n" +
        "A	4	1/8	#\n" +
        "D	5	1/4\n" +
        "C	5	1/8\n" +
        "B	4	1/8\n" +
        "A	4	3/8\n" +
        "F	4	1/8\n" +
        "A	4	1/4\n" +
        "G	4	1/8\n" +
        "F	4	1/8\n" +
        "E	4	1/4\n" +
        "E	4	1/8\n" +
        "F	4	1/8\n" +
        "G	4	1/4\n" +
        "A	4	1/4\n" +
        "F	4	1/4\n" +
        "D	4	1/4\n" +
        "D	4	1/4\n" +
        "Rest		1/4\n" +
        "A	4	1/4\n" +
        "E	4	1/8\n" +
        "F	4	1/8\n" +
        "G	4	1/8\n" +
        "A	4	1/16\n" +
        "G	4	1/16\n" +
        "F	4	1/8\n" +
        "E	4	1/8\n" +
        "D	4	1/4\n" +
        "D	4	1/8\n" +
        "F	4	1/8\n" +
        "A	4	1/4\n" +
        "G	4	1/8\n" +
        "F	4	1/8\n" +
        "E	4	3/8\n" +
        "F	4	1/8\n" +
        "G	4	1/4\n" +
        "A	4	1/4\n" +
        "F	4	1/4\n" +
        "D	4	1/4\n" +
        "D	4	1/2\n" +
        "Rest		1/8\n" +
        "G	4	1/4\n" +
        "A	4	1/8	#\n" +
        "D	5	1/4\n" +
        "C	5	1/8\n" +
        "B	4	1/8\n" +
        "A	4	3/8\n" +
        "F	4	1/8\n" +
        "A	4	1/4\n" +
        "G	4	1/8\n" +
        "F	4	1/8\n" +
        "E	4	1/4\n" +
        "E	4	1/8\n" +
        "F	4	1/8\n" +
        "G	4	1/4\n" +
        "A	4	1/4\n" +
        "F	4	1/4\n" +
        "D	4	1/4\n" +
        "D	4	1/4\n" +
        "Rest		1/4\n" +
        "A	4	1/2\n" +
        "F	4	1/2\n" +
        "G	4	1/2\n" +
        "E	4	1/2\n" +
        "F	4	1/2\n" +
        "D	4	1/2\n" +
        "C	4	1/2	#\n" +
        "E	4	1/4\n" +
        "Rest		1/2\n" +
        "A	4	1/2\n" +
        "F	4	1/2\n" +
        "G	4	1/2\n" +
        "E	4	1/2\n" +
        "F	4	1/4\n" +
        "A	4	1/4\n" +
        "D	5	1/2\n" +
        "C	5	1/2	#\n" +
        "Rest		1\n" +
        "A	4	1/4\n" +
        "E	4	1/8\n" +
        "F	4	1/8\n" +
        "G	4	1/8\n" +
        "A	4	1/16\n" +
        "G	4	1/16\n" +
        "F	4	1/8\n" +
        "E	4	1/8\n" +
        "D	4	1/4\n" +
        "D	4	1/8\n" +
        "F	4	1/8\n" +
        "A	4	1/4\n" +
        "G	4	1/8\n" +
        "F	4	1/8\n" +
        "E	4	3/8\n" +
        "F	4	1/8\n" +
        "G	4	1/4\n" +
        "A	4	1/4\n" +
        "F	4	1/4\n" +
        "D	4	1/4\n" +
        "D	4	1/2\n" +
        "Rest		1/4\n" +
        "G	4	1/4\n" +
        "A	4	1/8	#\n" +
        "D	5	1/4\n" +
        "C	5	1/8\n" +
        "B	4	1/8	\n" +
        "A	4	3/8\n" +
        "F	4	1/8\n" +
        "A	4	1/4\n" +
        "G	4	1/8\n" +
        "F	4	1/8\n" +
        "E	4	1/4\n" +
        "E	4	1/8\n" +
        "F	4	1/8\n" +
        "G	4	1/4\n" +
        "A	4	1/4\n" +
        "F	4	1/4\n" +
        "D	4	1/4\n" +
        "D	4	1/4\n" +
        "Rest		1/4\n" +
        "A	4	1/4\n" +
        "E	4	1/8\n" +
        "F	4	1/8\n" +
        "G	4	1/8\n" +
        "A	4	1/16\n" +
        "G	4	1/16\n" +
        "F	4	1/8\n" +
        "E	4	1/8\n" +
        "D	4	1/4\n" +
        "D	4	1/8\n" +
        "F	4	1/8\n" +
        "A	4	1/4\n" +
        "G	4	1/8\n" +
        "F	4	1/8\n" +
        "E	4	3/8\n" +
        "F	4	1/8\n" +
        "G	4	1/4\n" +
        "A	4	1/4\n" +
        "F	4	1/4\n" +
        "D	4	1/4\n" +
        "D	4	1/2\n" +
        "Rest		1/4\n" +
        "G	4	1/4\n" +
        "A	4	1/8	#\n" +
        "D	5	1/4\n" +
        "C	5	1/8\n" +
        "B	4	1/8	\n" +
        "A	4	3/8\n" +
        "F	4	1/8\n" +
        "A	4	1/4\n" +
        "G	4	1/8\n" +
        "F	4	1/8\n" +
        "E	4	1/4\n" +
        "E	4	1/8\n" +
        "F	4	1/8\n" +
        "G	4	1/4\n" +
        "A	4	1/4\n" +
        "F	4	1/4\n" +
        "D	4	1/4\n" +
        "D	4	1/4\n" +
        "Rest		1/4");
        //declare the toggle groups for each set of radio buttons
        ToggleGroup noteType = new ToggleGroup();
        ToggleGroup songType = new ToggleGroup();
        
        RadioButton beepButton = new RadioButton("Beep");
        RadioButton textFieldForMusic = new RadioButton("Instrument");
        RadioButton fileButton = new RadioButton("File");
        RadioButton textButton = new RadioButton("TextField");
        noteField.setMaxSize(1000, 1000);
        //set the toggle groups for each set of buttons
        beepButton.setToggleGroup(noteType);
        textFieldForMusic.setToggleGroup(noteType);
        fileButton.setToggleGroup(songType);
        textButton.setToggleGroup(songType);
        //make the default settings
        beepButton.setSelected(true);
        textButton.setSelected(true);
        //make the boxes to hold all of the radio buttons
        VBox songTypeBox = new VBox();
        songTypeBox.getChildren().addAll(new Label("Select were to play from:"), textButton, fileButton);
        VBox noteTypeBox = new VBox();
        noteTypeBox.getChildren().addAll(new Label("Select how to play the music:"), beepButton, textFieldForMusic);      
        VBox radioButtonBox = new VBox();
        radioButtonBox.getChildren().addAll(noteTypeBox, songTypeBox);
        
        fileSelector.setTitle("Open file to play music from");
        //set the default directory to the music folder 
        fileSelector.setInitialDirectory(new File("Music"));
        pane2.getChildren().addAll(noteField, pane);
        stage.setTitle("Music Player Weston Wingo");
        pane.getChildren().addAll(openFile, stopMusic, playMusic, radioButtonBox);
        Scene scene = new Scene(pane2);
        stage.setScene(scene);
        stage.show();
        scene.heightProperty().addListener(e -> {
            noteField.setPrefHeight(scene.getHeight() / 1.2);
        });
        stopMusic.setOnAction(e -> {
            for(Thread thread : threadList) {
                thread.stop();
            }
            //this is to prevent errors if the user tries to stop the song without the song playing
            try {
                ManagedPlayer mPlayer = player.getManagedPlayer();
                //if the song is playing stop it
                if(mPlayer.isPlaying()) 
                    mPlayer.finish();
                }
            catch(Exception ex) {}
        });
        //button to play the music depending on the radio buttons selected
        playMusic.setOnAction(e -> {
            //if the music is to be played from file
            if(fileButton.isSelected()) {
            //try catch is used in case the user closes out of the dialog window without making a file choice
            try {
                if (fileToPlay.exists()) {
                    noteList = readFile(fileToPlay);
                }
            }
            catch(Exception ex){}
            }
            //if the play from text button is pressed
            else if (textButton.isSelected()) {
                noteList = readTextField(noteField.getText());
            }
            if(beepButton.isSelected()) {
                playBeep();
            }
            else if(textFieldForMusic.isSelected()) {
                playInstrument();
            }
        });
        /*when the button is clicked, open the file selector and pass the file to the 
        method the parse the info into the FormalNote class*/
        openFile.setOnAction(e -> {
            fileToPlay = fileSelector.showOpenDialog(stage);
            //try catch is used in case the user closes out of the dialog window without making a file choice

        });    
    }
    //these methods are the methods used to play the songs
    private void playInstrument() {
        Thread thread = new Thread(() -> {
                //check to see if the array has notes to play
                if (noteList.size() > 0) {
                    FormalNote note1 = noteList.get(0);
                    String song = "T" + (int)(note1.bpm) + " V0 I[" + note1.getInstrument() + "] ";
                    //add a note to the song for every note in the array list
                    for (FormalNote note: noteList) {
                        song += convertToString(note.instrument, note.noteType, note.octave, note.duration, note.isSharp);
                    }
                    player = new Player();
                    System.out.println(song);
                    //play the song
                    player.play(song);
                }
            });
            //add the thread to the list so I can stop the music if need be
            threadList.add(thread);
            thread.start();
    }
    private void playBeep() {
        //make a thread
            Thread thread = new Thread (() -> { 
                //check to see if the array has notes to play
                if (noteList != null) {
                    //this is where I will put the condition for playing with different song types
                    try {
                        ArrayList<BeepNote> noteListB = new ArrayList<>();
                        for(FormalNote note: noteList) {
                            noteListB.add(new BeepNote(note));
                        }
                        IHopeThisWorks.tone(noteListB);
                    } catch (LineUnavailableException ex) {
                        Logger.getLogger(JavaMusicReader.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });   
            //add the thread to the list so I can stop the music if need be
            threadList.add(thread);
            thread.start();
    }
    
    //Method used to read the file 
    public ArrayList<FormalNote> readFile(File file) {
        ArrayList<FormalNote> noteList = new ArrayList<>();
        try {
            //load the file into the scanner
            Scanner fileReader = new Scanner(file);
            //while the file still has information
            while(fileReader.hasNext()) {
                //read a line from the file
                String currentLine = fileReader.nextLine().trim();
                FormalNote note = parseString(currentLine);
                if (note.getOctave() != -1 && note.getNoteType() != ' ' && note.getDuration() != -1) {
                    noteList.add(note);
                }   
            } 
        } catch (FileNotFoundException ex) {
        }  
        return noteList;
    }
    
    public FormalNote parseString(String currentLine) {
        FormalNote note = new FormalNote();
        try { 
            //split the line into tokens
            String[] lineArray = currentLine.split("\t");
            //convert the note type to upper case
            lineArray[0] = lineArray[0].toUpperCase();
            //check the instrument
            if (checkInstrumentValidity(lineArray[0].trim())) {
                instrument = lineArray[0].trim().toUpperCase();
            }
            //gets the BPM
            else if (lineArray[0].trim().matches("[0-9][0-9]?[0-9]?")) {
                bpm = Integer.parseInt(lineArray[0].trim());
            }
            //handels rests in the song
            else if(lineArray[0].trim().matches("REST")) {
                note = (transposeRest(lineArray));
            }
            else {
                note.setInstrument(instrument);
                if(lineArray[0].trim().matches("[A-G a-g]"))
                    note.setNoteType(lineArray[0].charAt(0));
                //if the second token is a number 0-9 set that as the note objects octave
                if(lineArray[1].trim().matches("[0-9]")) 
                    note.setOctave(Integer.parseInt(lineArray[1].trim()));
                //This will figure out the duration's validity
                if(lineArray[2].trim().matches("[1-9][/]?[1-9]?[0-9]?[0-9]?")) {
                    //this will convert the String duration to a concreate number
                    //split the fraction at the divider
                    String[] durationString = lineArray[2].split("/");
                    double duration = 1;
                    double numerator = Integer.parseInt(durationString[0]);
                    try {
                        double denominator = Integer.parseInt(durationString[1]);
                        duration = numerator / denominator;
                    } catch(Exception e) {
                        duration = Integer.parseInt(durationString[0]);
                    }
                    note.setDuration(duration);
                } 
                //see if the note is sharp
                if (lineArray[3].trim().equals("#")) {
                    note.setIsSharp(true);
                }
            }
        }
        catch(Exception e) { } 
        note.setBpm(bpm);
        //finally add the note to the array list of notes 
        return note;
    } 
    
    public boolean checkInstrumentValidity(String inst) {
        //loop through the whole list to confirm if the instrument is in the list
        for(String s : instrumentList) {
            if (inst.toUpperCase().equals(s))
                return true;
        }
        return false;
    }
    
    public FormalNote transposeRest(String[] lineArray) {
        FormalNote note = new FormalNote();
        note.setNoteType('R');
        if(lineArray[2].trim().matches("[1-9][/]?[1-9]?[0-9]?[0-9]?")) {
            //this will convert the String duration to a concreate number
            //split the fraction at the divider
            String[] durationString = lineArray[2].split("/");
            double duration = 1;
            double numerator = Integer.parseInt(durationString[0]);
            try {
                double denominator = Integer.parseInt(durationString[1]);
                duration = numerator / denominator;
            } catch(Exception e) {
            }
            note.setDuration(duration);
        } 
        note.setOctave(0);
        return note;
    }

    private ArrayList<FormalNote> readTextField(String txt) {
        ArrayList<FormalNote> noteList = new ArrayList<>();
        //split the textfield by newline char
        String[] lineList = txt.split("\n");
        for(String line: lineList) {
            FormalNote note = parseString(line);
            if (note.getOctave() != -1 && note.getNoteType() != ' ' && note.getDuration() != -1) {
                noteList.add(note);
            } 
        }
        return noteList;
    }
    //Class used to help create notes 
    public class FormalNote {
        private double bpm;
        private String instrument;
        private char noteType;
        private int octave;
        private double duration;
        private boolean isSharp = false;
        //convert the note type to upper case
        protected FormalNote(char noteType) {
            this.noteType = noteType;
        }

        protected FormalNote() {
            this.instrument = "";
            this.octave = -1;
            this.duration = -1;
            this.noteType = ' ';
        }
        //getter and setters
        public char getNoteType() {
            return noteType;
        }

        public void setNoteType(char noteType) {
            this.noteType = noteType;
        }

        public int getOctave() {
            return octave;
        }

        public void setOctave(int octave) {
            this.octave = octave;
        }

        public double getDuration() {
            return duration;
        }

        public void setDuration(double duration) {
            this.duration = duration;
        }

        public boolean isIsSharp() {
            return isSharp;
        }

        public void setIsSharp(boolean isSharp) {
            this.isSharp = isSharp;
        }
        
        @Override
        public String toString() {
            return ("NoteType: " + this.noteType + " Octave: " + this.octave + "\nDuration: " + this.duration + " IsSharp: " + this.isSharp + "\n");
        }

        public String getInstrument() {
            return instrument;
        }

        public void setInstrument(String instrument) {
            this.instrument = instrument;
        }

        public double getBpm() {
            return bpm;
        }

        public void setBpm(int bpm) {
            this.bpm = bpm;
        }
    }
    //class for making the notes as beeps 
    public class BeepNote {
        double frequency;
        double duration;
        double bpm;
        public BeepNote (FormalNote note) {   
            //determine the frequency the note needs to be
            switch(note.getNoteType()) {
                case 'A': {
                    if(note.isSharp) 
                        this.frequency = noteFrequencyArray[note.octave][10];
                    else 
                        this.frequency = noteFrequencyArray[note.octave][9];
                    break;
                }
                case 'B': {
                    this.frequency = noteFrequencyArray[note.octave][11];
                    break;
                }
                case 'C': {
                    if(note.isSharp)
                        this.frequency = noteFrequencyArray[note.octave][1];
                    else 
                        this.frequency = noteFrequencyArray[note.octave][0];
                    break;
                }
                case 'D': {
                    if(note.isSharp)
                        this.frequency = noteFrequencyArray[note.octave][3];
                    else 
                        this.frequency = noteFrequencyArray[note.octave][2];
                    break;
                }
                case 'E': {
                    this.frequency = noteFrequencyArray[note.octave][4];
                    break;
                }
                case 'F': {
                    if(note.isSharp) 
                        this.frequency = noteFrequencyArray[note.octave][5];
                    else 
                        this.frequency = noteFrequencyArray[note.octave][6];
                    break;
                }
                case 'G': {
                    if(note.isSharp)
                        this.frequency = noteFrequencyArray[note.octave][8];
                    else 
                        this.frequency = noteFrequencyArray[note.octave][7];
                    break;
                }
                case 'Z': {
                    this.frequency = 0;
                    break;
                }
                default: {
                    break;
                }
            } 
            //set the duration of the note in milliseconds also adding BPM to the equation
            //figure out how to get the beats per duration            
            this.duration = ((240 * note.duration) / note.bpm * 1000);
            System.out.println(this.frequency + "\t" + this.duration);
        }
    }
    //convert the note into the syntax that jfugue uses
    public String convertToString(String instrument, char noteType, int octave, double duration, boolean isSharp) {
        //if the note is sharp add the sharp notation
        String sharp = "";
        if (isSharp) 
            sharp = "#";
        return noteType + sharp + octave + "/" + duration + " "; 
    }
}

//Class that produces the beeps 
class IHopeThisWorks {                  
    public static float SAMPLE_RATE = 8000;
    public static void tone(ArrayList<BeepNote> formalNoteList) throws LineUnavailableException {
        //for every note in the list
        byte[] buf = new byte[1];
            AudioFormat af
                = new AudioFormat(
                        SAMPLE_RATE, // sampleRate
                        8, // sampleSizeInBits
                        1, // channels
                        true, // signed
                        false);      // bigEndian
            //get the audio source line
        try (SourceDataLine sdl = AudioSystem.getSourceDataLine(af)) {
            sdl.open(af);
            sdl.start();
            //play the notes
            for(BeepNote note: formalNoteList) {
                tone(note.frequency, note.duration, .5, buf, sdl);
            }
            sdl.drain();
            sdl.stop();
        }
    }
    //write the byte array to the audio line 
    public static void tone(double hz, double msecs, double vol, byte[] buf, SourceDataLine sdl) throws LineUnavailableException {
        for (int i = 0; i < msecs * (SAMPLE_RATE / 1000); i++) {
            //equation for the sine wave of the note
            double angle =  i / (SAMPLE_RATE / hz) * 2.0 * Math.PI;
            //convert it to a btye array
            buf[0] = (byte)(Math.sin(angle) * 127 * vol);
            sdl.write(buf, 0, 1);
        }
    }
}
