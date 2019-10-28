import java.util.*;

public class SHA1Tree<E> {
    private List<SHA1Tree<E>> _children;
    private SHA1Tree<E> _parent;
    private E _label;
    
    public SHA1Tree(E label) {
        this._children = new ArrayList<SHA1Tree<E>>();
        this._label = label;
    }
    public SHA1Tree<E> addChild(E label) {
        SHA1Tree<E> child = new SHA1Tree<E>(label);
        child._parent = this;
        this._children.add(child);
        return child;
    }
    public SHA1Tree<E> addCommit(E match, E value) {
        if (this._label.equals(match)) {
            return this.addChild(value); 
        } else {
            for (int i = 0; i < this._children.size(); i++) {
                return this._children.get(i).addCommit(match, value);    
            }
            return null;
        }    
    }
    public static String printTree(SHA1Tree<String> tree) {
        if (tree == null) {
            return "";
        } else {
            String ret = "[" + tree.getSHA1();
            for (SHA1Tree<String> child: tree._children) {
                ret += " " + printTree(child);
            }
            ret += "]";
            return ret;
        }
    }

    public List<SHA1Tree<E>> getChildren() { return this._children; } 
    public SHA1Tree<E> getParent() { return this._parent; }
    public E getSHA1() { return this._label; }
}
