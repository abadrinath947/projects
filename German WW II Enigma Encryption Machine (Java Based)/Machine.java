package enigma;
import java.util.*;

public class Machine {
    private List<Rotor> _rotors;
    private List<Setting> _settings;
    private final PlugBoard _plugBoard;

    public Machine(List<Rotor> rotors, String settings, PlugBoard plugBoard) {
        this._rotors = rotors;
        this._settings = new ArrayList<Setting>(); 
        this._settings.add(new Setting());
        for (int i = 0; i < settings.length(); i++) 
            this._settings.add(new Setting(settings.toCharArray()[i]));
        this._plugBoard = plugBoard;
    }
    public char keyPressed(char key) {
        rotateRotors();
        return encryptKey(key);
    }
    public void rotateRotors() {
        boolean[] rotated = new boolean[this._settings.size()];
        int i = 0;

        while (i < this._settings.size() - 1) {
            boolean isReflector = this._rotors.get(i) instanceof Reflector,
                    hasNotches = this._rotors.get(i + 1).getNotches() != null,
                    notchesAlign = hasNotches && this._rotors.get(i + 1).getNotches().contains(this._settings.get(i + 1).getSetting());
            if (!isReflector && hasNotches && notchesAlign) {          
                if (!rotated[i]) { rotate(this._settings.get(i)); rotated[i] = true; }
                if (!rotated[i + 1])     { rotate(this._settings.get(i + 1)); rotated[i + 1] = true; }
            }
            i += 1;
        }       
        if (!rotated[this._settings.size() - 1]) 
            rotate(this._settings.get(this._settings.size() - 1));  
    }
    private static void rotate(Setting toRot) {
        toRot.rotateSetting();
    }
    private char encryptKey(char key) { 
        int i = 0;
        key = this._plugBoard.permute(key, true);
        for (i = this._rotors.size() - 1; i >= 0 && !(this._rotors.get(i) instanceof Reflector); i--) {
            key = Main.shiftCharUp(key, this._settings.get(i).getSetting());
            key = this._rotors.get(i).permute(key, true);
            key = Main.shiftCharDown(key, this._settings.get(i).getSetting());
        }
        key = this._rotors.get(i).permute(key, false);
        for (i += 1; i < this._rotors.size() && !(this._rotors.get(i) instanceof Reflector); i++) {
            boolean isReflector = this._rotors.get(i) instanceof Reflector;
            key = Main.shiftCharUp(key, this._settings.get(i).getSetting());
            key = this._rotors.get(i).permute(key, false);
            key = Main.shiftCharDown(key, this._settings.get(i).getSetting());
        }
        key = this._plugBoard.permute(key, false);
        return key;
    }
    public List<Rotor> getRotors() { return this._rotors; }
    public List<Setting> getSettings() { return this._settings; }

    public String toString() {
        return  "Rotors: " + this._rotors + "\n" +
                "Settings: " + this._settings + "\n";
    }
}
