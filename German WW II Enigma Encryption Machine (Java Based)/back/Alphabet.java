import java.util.TreeSet;
public class Alphabet {
    TreeSet<Character> _alphabetRange;

    public Alphabet(TreeSet<Character> range) {
        this._alphabetRange = range;
    }
    public Character first() { return this._alphabetRange.first(); }
    public boolean contains(Character i) { return this._alphabetRange.contains(i); }
    public int size() { return this._alphabetRange.size(); }
}
