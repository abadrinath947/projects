import java.util.*;
import java.io.*;

public class HeadPointer {
    private Map<String, String> _branchesPointers;
    private String _activeBranch;

    public HeadPointer(File config) {
        this._branchesPointers = new HashMap<String, String>();
        processConfig(config);
    }
    public HeadPointer(String activeBranch) {
        this._activeBranch = activeBranch;
        this._branchesPointers = new HashMap<String, String>();
    }
    private void processConfig(File config) {
        this._activeBranch = config.getName();
        String[] branches = Utils.readContentsAsString(config).split("_");
        String[][] branchesCommits = new String[branches.length][2];

        for (int i = 0; i < branches.length; i++) {
            branchesCommits[i] = branches[i].split("-");
        }
        processArrayToMap(branchesCommits);   
    }
    private void processArrayToMap(String[][] branchesCommits) {
        for (int i = 0; i < branchesCommits.length; i++)
            this._branchesPointers.put(branchesCommits[i][0], branchesCommits[i][1]); 
    }
    public boolean isHeadPointer(String commit) {
        return this._branchesPointers.values().contains(commit);
    }
    public String getBranch() {
        return this._activeBranch;
    }
    public void addBranch(String branch) {
        this._branchesPointers.put(branch, getCurrentCommit());
    }
    public boolean removeBranch(String branch) {
        if (!branch.equals(this._activeBranch)) {
            this._branchesPointers.remove(branch);
            return true;
        }
        return false;
    }
    public void updateCommit(String branch, String commit) {
        this._activeBranch = branch;
        this._branchesPointers.remove(branch);
        this._branchesPointers.put(branch, commit);
    }
    public boolean containsBranch(String branch) {
        return this._branchesPointers.keySet().contains(branch);
    }
    public String getCurrentCommit(String branch) {
        return this._branchesPointers.get(branch);
    }
    public String getCurrentCommit() {
        return getCurrentCommit(this._activeBranch);
    }
    public void makeHeadBranch(String branch) {
        this._activeBranch = branch;
    }
    public void makeHeadBranch(String branch, String identifier) {
        makeHeadBranch(branch);
        this._branchesPointers.remove(branch);
        this._branchesPointers.put(branch, identifier);
    }
    public List<String> getBranches() {
        return new ArrayList<String>(this._branchesPointers.keySet());
    }
    public String toString() {
        String ret = "";
        for (Map.Entry<String, String> branchPointer: this._branchesPointers.entrySet()) {
            ret += branchPointer.getKey() + "-" + branchPointer.getValue() + "_";
        }
        return ret.substring(0, ret.length() - 1); 
    }
}
