public class Reflector extends Rotor {
    public Reflector(String name, String description, String permutations) {
        super(name, description, permutations);
    }
    public String toString() {
        return "-- REFLECTOR --\n" + super.toString();
    } 
}
