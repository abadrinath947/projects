import java.util.*;
import java.io.*;

public class Commit {
    private Date _timestamp;
    private String _commitMessage, _identifier, _parent;
    private Set<String> _blobs;
    private Map<String, String> _trackedFiles;

    public Commit(String commitMessage, String parent, Date timestamp, Set<String> blobs) {
        this._commitMessage = commitMessage;
        this._timestamp = timestamp;
        this._blobs = new LinkedHashSet<String>();
        this._blobs.addAll(blobs);
        this._parent = parent;
        this._trackedFiles = processTrackedFiles(this._blobs);
        this._identifier = Utils.sha1(this.toString());
    }
    public void updateBlobs(Set<String> parent) {
        this._blobs.addAll(parent);
        this._identifier = Utils.sha1(this.toString());
    }
    private Map<String, String> processTrackedFiles(Set<String> blobs) {
        Map<String, String> temp = new LinkedHashMap<String, String>();
        for (String i: blobs) {
            if (Gitlet.searchBlobSHA1(i) != null) {
                temp.put(Gitlet.searchBlobSHA1(i).getIdentity(), i);
            }
        }
        return temp;
    }
    public String toString() {
        return this._commitMessage + "-" + getParent() + "-" + this._timestamp.getTime() + "-" + getConcatBlobs();
    }
    public String getSHA1() { return this._identifier; }
    public String getConcatBlobs() {
        if (this._blobs == null || this._blobs.isEmpty()) {
            return " ";
        } 
        String concat = "";
        for (String blob: this._blobs) {
            concat += blob + "_";
        }
        return concat.substring(0, concat.length() - 1);
    }  
    public String getCommitMessage() { return this._commitMessage; } 
    public String getParent() { 
        if (this._parent == null)
            return "null";
        return this._parent; 
    }
    public Map<String, String> getTrackedFiles() { return this._trackedFiles; }
    public Date getTimeStamp() { return this._timestamp; }
    public boolean isTracked(String identifier) { 
        return this._blobs.contains(identifier); }
}
