package enigma;
 import java.util.*;

public class Rotor {
    private String _name;
    private List<Character> _notches;
    private ArrayList<LinkedList<Character>> _permutations;
    private boolean _isMoving;    

    public Rotor(String name, String description, String permutations) {
        this._name = name;
        this._isMoving = isMoving(description);
        this._permutations = processPermutations(permutations);
        verifyPermutations(this._permutations);
        this._notches = processNotches(description.substring(1));        
    }
    private boolean isMoving(String desc) {
        return desc.charAt(0) == 'M' ? true: false; 
    }
    private List<Character> processNotches(String desc) {
        List<Character> temp = new ArrayList<Character>();
        if (this._isMoving) {
            for (char i: desc.toCharArray())
                temp.add(i);
            return temp;
        }
        return null;
    }
    public static ArrayList<LinkedList<Character>> processPermutations(String perms) {
        ArrayList<LinkedList<Character>> temp = new ArrayList<LinkedList<Character>>();
        for (int i = perms.indexOf("("); i != -1; i = perms.indexOf("(", i + 1)) {
            LinkedList<Character> loopIntermediate = new LinkedList<Character>();
            for (char j: perms.substring(i + 1, perms.indexOf(")", i)).toCharArray()) 
                loopIntermediate.add(j); 
            temp.add(loopIntermediate);
        }
        return temp;
    }
    public static List<Rotor> discardFromConfig(List<Rotor> allRotors, List<String> reqRotors) {
        List<Rotor> retList = new ArrayList<Rotor>();
        List<String> totalNames = new ArrayList<String>();
        for (Rotor r: allRotors)
            totalNames.add(r.getName());
        if (!totalNames.containsAll(reqRotors))
            throw EnigmaException.error("Some rotors in setting line not found");
        for (String i: reqRotors) 
            for (Rotor r: allRotors) {
                if (r._name.equals(i))
                    if (!retList.contains(r)) 
                        retList.add(r);
                    else
                        throw EnigmaException.error("Rotor %s is repeated in configuration.", r.getName());
            }
        return retList;
    } 
    public char permute(char key, boolean goingUp) {
        return permute(this._permutations, key, goingUp);
    }
    public static Character permute(ArrayList<LinkedList<Character>> permutations, char key, boolean goingUp) {
        for (LinkedList<Character> i: permutations) {
            for (int j = 0; j < i.size(); j++) 
                if (i.get(j) == key && goingUp)
                    return i.get(Math.floorMod(j + 1, i.size()));
                else if (i.get(j) == key && !goingUp)
                    return i.get(Math.floorMod(j - 1, i.size()));
        }
        return null;
    }
    private void verifyPermutations(ArrayList<LinkedList<Character>> permutations) {
        if (permutations.size() == 0)
            throw EnigmaException.error("Rotor %s has no configurable permutations", this._name);
        for (LinkedList<Character> i: permutations) {
            if (i.size() == 0) throw EnigmaException.error("Rotor %s cannot have an empty permutation", this._name);
            for (Character j: i)
               if (!Main._alphabet.contains(j))
                   throw EnigmaException.error("%s is not in the alphabet (in permutation for rotor %s)", "" + j, this._name); 
        }
    }
    public List<Character> getNotches() { return this._notches; }
    public boolean getMoving() { return this._isMoving; }
    public ArrayList<LinkedList<Character>> getPermutations() { return this._permutations; }
    public String getName() { return this._name; }
}
