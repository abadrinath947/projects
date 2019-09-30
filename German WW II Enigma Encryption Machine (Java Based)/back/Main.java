import java.io.*;
import java.util.*;

public class Main {
    private Machine _machine;
    private List<Rotor> _totalRotors;
    public static Alphabet _alphabet;
    private List<String> _encryptedMessage;
    private int _rotorSlots, _numPawls;
    private Scanner _configReader, _inputReader;
    private FileWriter _outputWriter;

    public Main(String[] args) {
        String configFile = args[0], inputFile = args[1], outputFile = args[2];
        
        this._configReader = configureReaders(configFile);
        this._inputReader = configureReaders(inputFile);
        this._alphabet = new Alphabet(processAlphabet(this._configReader));
        processRotorSlots(this._configReader);
        this._totalRotors = processInputRotors(this._configReader);
        this._outputWriter = configureWriter(outputFile);
    }
    private void process() {
        this._encryptedMessage = format(processInputFile(this._inputReader));
        writeEncrypted(this._outputWriter, this._encryptedMessage);
    }
    private Scanner configureReaders(String file) {
        Scanner temp = null;
        try {
            temp = new Scanner(new File(file));
        } catch (FileNotFoundException e) {
            throw EnigmaException.error("\'%s\' file not found", file);
        }
        return temp;
    }
    private FileWriter configureWriter(String file) {
        FileWriter temp = null;
        try {
            temp = new FileWriter(file);
        } catch (IOException e) {
            System.out.println("File not found!");
        }
        return temp;
    }
    private TreeSet<Character> processAlphabet(Scanner configReader) {
        String[] line = verifyAlphabet(configReader.nextLine());
        TreeSet<Character> temp = new TreeSet<Character>();
        for (int i = line[0].charAt(0); i <= line[1].charAt(0); i++)
             temp.add((char) i);
        return temp;
    }
    private void processRotorSlots(Scanner configReader) {
        String line = configReader.nextLine().trim();
        try {
            this._rotorSlots = Integer.parseInt(line.split("\\s+")[0]);
            this._numPawls = Integer.parseInt(line.split("\\s+")[1]);
        } catch (NumberFormatException e) {
            throw EnigmaException.error("Config line 2 unable to parse rotor slots and number of pawls: %s", line);
        }
    }
    private List<Rotor> processInputRotors(Scanner configReader) {
        List<String> remLines = new ArrayList<String>();
        List<Rotor> temp = new ArrayList<Rotor>();
        while (configReader.hasNextLine())
            remLines.add(configReader.nextLine().trim().toUpperCase());
        for (int i = 0; i < remLines.size(); i++) {
            if (remLines.get(i) == "") {
                remLines.remove(i); 
                i--;
            }
            if (remLines.get(i).charAt(0) == '(') {
                remLines.set(i - 1, remLines.get(i - 1) + " " + remLines.remove(i));
                i--;
            }
            remLines.set(i, remLines.get(i).trim());
        }
        for (String i: remLines) {
            String[] rotorConfig = verifyInputRotors(i);
            String name = rotorConfig[0], description = rotorConfig[1];
            String permutations = i.substring(i.indexOf("("));
            if (description.equals("R"))
                temp.add(new Reflector(name.toUpperCase(), description, permutations));
            else if (description.trim().charAt(0) == 'M' || description.trim().charAt(0) == 'N')
                temp.add(new Rotor(name.toUpperCase(), description, permutations));
        }
        return temp;    
    }
    private List<String> processInputFile(Scanner inputConfig) {
        List<String> remLines = new ArrayList<String>();
        List<String> processed = new ArrayList<String>();
        while (inputConfig.hasNextLine()) 
            remLines.add(inputConfig.nextLine().trim().toUpperCase());
        for (String i: remLines)
            if (i.equals(""))
               processed.add(i); 
            else if (i.charAt(0) == '*')
                processSettingLine(i.substring(1).trim());
            else
                processed.add(processEncryptionLine(i));
        return processed;
    }
    private void processSettingLine(String setting) {
        String[] tokenized = setting.trim().split("\\s+");
        boolean firstTime = true;
        String initialConfig = "";
        List<String> toRem = new ArrayList<String>();
        PlugBoard def = new PlugBoard();
        
        for (int i = tokenized.length - 1; i >= 0; i--) 
            if (tokenized[i].charAt(0) != '(' && firstTime) { 
                initialConfig = tokenized[i].toUpperCase();
                firstTime = false;
            } else if (tokenized[i].charAt(0) != '(') {
                toRem.add(tokenized[i]);
            }
        Collections.reverse(toRem);
        if (setting.indexOf("(") != -1)
            def = new PlugBoard(setting.substring(setting.indexOf("(")).trim());
        this._machine = new Machine(Rotor.discardFromConfig(this._totalRotors, toRem), initialConfig, def);
        verifyMachine(this._machine, this._rotorSlots, this._numPawls); 
    }
    private String processEncryptionLine(String toEncrypt) {
        if (this._machine == null)
            throw EnigmaException.error("No encryption machine initted");
        String ret = "";
        String[] splitted = toEncrypt.trim().split("\\s+");
        for (String i: splitted)
            for (int j = 0; j < i.length(); j++)
                ret += this._machine.keyPressed(i.charAt(j));     
        return ret;
    }
    private void writeEncrypted(FileWriter writer, List<String> message) {
        try {
            for (String i: message)
                writer.write(i.trim() + "\n");
            writer.close();
        } catch (IOException e) {
            throw EnigmaException.error("Error: %s", e.getMessage());
        }
    }
    private void verifyMachine(Machine m, int slots, int pawls) {
        int count = 0;
        if (m.getRotors().size() != slots)
            throw EnigmaException.error("Error: There need to be %s slots", slots);
        if (!(m.getRotors().get(0) instanceof Reflector))
            throw EnigmaException.error("First Rotor %s must be a reflector", m.getRotors().get(0).getName());
        if (m.getSettings().size() != m.getRotors().size())
            throw EnigmaException.error("Initial configuration of rotors has an incorrect num of letters (%s instead of %s)", m.getSettings().size() - 1, m.getRotors().size() - 1);
        for (Rotor i: m.getRotors()) 
            if (i.getMoving())
                count++; 
        if (count != pawls)
            throw EnigmaException.error("Error: There need to be %s pawls", pawls);
    } 
    private String[] verifyInputRotors(String rotorConfig) {
        String[] temp = rotorConfig.split("\\s+");
        if (temp.length < 3)
            throw EnigmaException.error("Permutations and a proper name with description must be specified for rotors and reflectors: %s", rotorConfig);
        if (temp[1].charAt(0) != 'M' && temp[1].charAt(0) != 'N' && temp[1].charAt(0) != 'R')
            throw EnigmaException.error("%s must be a reflector or rotor", temp[0]);
        if ((rotorConfig.length() - rotorConfig.replace("(", "").length()) !=
            (rotorConfig.length() - rotorConfig.replace(")", "").length()) ||
            rotorConfig.indexOf("(") == -1)
            throw EnigmaException.error("Permutations are formatted incorrectly", "");
        return temp;
    }
    private String[] verifyAlphabet(String input) {
        String[] alphabetLine = null;
        try {
            alphabetLine = input.trim().replace(" ", "").split("-");
        } catch (IndexOutOfBoundsException e) {
            throw EnigmaException.error("Alphabet line not specified in the correct format: %s", input);
        }
        if (alphabetLine.length != 2 ||
            alphabetLine[1].charAt(0) <= alphabetLine[0].charAt(0))
            throw EnigmaException.error("Alphabet line incorrectly formatted: %s", Arrays.toString(alphabetLine));

        return alphabetLine;
    }
    public static char shiftCharUp(char a, char b) {
        return (char)(Math.floorMod(((a + b) - 2 * Main._alphabet.first()), Main._alphabet.size()) + 
                        Main._alphabet.first());
    }
    public static char shiftCharDown(char a, char b) {
        return (char)(Math.floorMod(((a - b) - 2 * Main._alphabet.first()), Main._alphabet.size()) + 
                        Main._alphabet.first());
    }
    private List<String> format(List<String> messages) {
        for (int i = 0; i < messages.size(); i++) 
            messages.set(i, formatString(messages.get(i)));
        return messages;
    }
    private String formatString(String message) {
        String ret = "";
        for (int i = 0; i < message.length(); i++) {
            ret += message.charAt(i);
            if (i != 0 && (i + 1) % 5 == 0)
                ret += " ";
        }
        return ret;
    }  
    public static void main(String[] args) {
        try {
            new Main(args).process();
            return;
        } catch (EnigmaException excp) {
            System.err.printf("Error: %s%n", excp.getMessage());
        }
        System.exit(1);
    }
}
