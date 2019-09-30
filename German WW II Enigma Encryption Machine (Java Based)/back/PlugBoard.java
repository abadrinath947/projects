import java.util.*;

public class PlugBoard {
    private ArrayList<LinkedList<Character>> _switches;
    
    public PlugBoard(String permutations) {
        this._switches = Rotor.processPermutations(permutations);
        verifyPlugBoard(this._switches);
    }
    public PlugBoard() {
        this._switches = null;
    }
    public char permute(char key, boolean upBoard) {
        return this._switches == null || Rotor.permute(this._switches, key, upBoard) == null? key: Rotor.permute(this._switches, key, upBoard);
    }
    private void verifyPlugBoard(ArrayList<LinkedList<Character>> switches){
        for (LinkedList i: switches)
            if (i.size() != 2)
                throw EnigmaException.error("Plugboard configuration %s is of an incorrect size (must be 2).", i.toString());
    }
}
