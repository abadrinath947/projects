import java.io.*;
import java.util.*;
import java.nio.charset.StandardCharsets;

public class Blob {
    private String _identifier, _fileName, _identity;
    private byte[] _fileContents;
   
    public Blob(String fileName, String identity) {
        this._fileName = fileName;
        this._identity = identity;
        this._fileContents = Utils.readContents(new File(this._fileName));
        this._identifier = Utils.sha1(this._fileContents, this._identity);
    }
    public String getSHA1() { return this._identifier; }
    public String getFileName() { return this._fileName; }
    public String getIdentity() { return this._identity; }
    public String toString() {
        return new String(this._fileContents, StandardCharsets.UTF_8);
    }
}
