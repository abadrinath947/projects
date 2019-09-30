public class Setting {
    private Character _setting;

    public Setting(char setting) {
        verifySetting(setting);
        this._setting = setting; 
    }
    public Setting() { this._setting = null; }
    private void verifySetting(char setting) {
        if (!Main._alphabet.contains(setting))
            throw EnigmaException.error("Initial configuration with letter %s is not in the alphabet", "" + setting);
    }
    public char getSetting() { return this._setting; }
    public void rotateSetting() { this._setting = (char)((this._setting - Main._alphabet.first() + 1) % Main._alphabet.size() + Main._alphabet.first()) ; }
    public String toString() { return "\'" + this._setting + "\'"; }
}
