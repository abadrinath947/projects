import java.util.*;
import java.text.*;
import java.io.*;

public class Gitlet {
    private static Map<String, Blob> _blobBindings;
    private static Map<String, Commit> _commitBindings;
    private SHA1Tree<String> _commitSHA1Tree;
    private Map<String, String> _stagedBlobs, _trackedBlobs, _untrackedBlobs;
    private Map<SHA1Tree<String>, SHA1Tree<String>> _currentCommitPointers;
    private HeadPointer _currentHeadPointer;

    public static void main(String[] args) {
        Gitlet test = new Gitlet(args);
    }

    public Gitlet(String[] args) {
        if (args[0].equals("init")) {
            this._trackedBlobs = new LinkedHashMap<String, String>();
            this._stagedBlobs = new LinkedHashMap<String, String>();
            this._commitBindings = new LinkedHashMap<String, Commit>();
            init(); 
        }
        else if (args[0].equals("add")) { 
            generateCurrentPointers();     
            generateBlobs();
            for (int i = 1; i < args.length; i++)
                add(args[i]);
            writeCurrentPointers();
            writeBlobs();
        }
        else if (args[0].equals("commit")) {
            generateCurrentPointers();
            generateBlobs();
            generateCommits();
            commit(args[1], new Date());
            writeCommits();
            writeCurrentPointers();
            writeBlobs();
        }
        else if (args[0].equals("log")) {
            generateCurrentPointers();
            generateCommits();
            log();
        }
        else if (args[0].equals("global-log")) {
            generateCurrentPointers();
            generateCommits();
            globalLog();
        }
        else if (args[0].equals("find")) {
            generateCurrentPointers();
            generateCommits();
            find(args[1]);            
        }
        else if (args[0].equals("rm")) {
            generateCurrentPointers();
            generateBlobs(); 
            generateCommits();
            for (int i = 1; i < args.length; i++)
                rm(args[i]);
        }
        else if (args[0].equals("branch")) {
            generateCurrentPointers();
            branch(args[1]);
            writeCurrentPointers();
        }
        else if (args[0].equals("rm-branch")) {
            generateCurrentPointers();
            rmBranch(args[1]);
            writeCurrentPointers();
        }
        else if (args[0].equals("checkout")) {
            generateCurrentPointers();
            generateBlobs();
            generateCommits();
            if (args.length == 2 && this._currentHeadPointer.containsBranch(args[1])) {
                checkoutBranch(args[1]);
            } else if (args.length == 2) {
                checkoutFile(this._currentHeadPointer.getCurrentCommit(), args[1]);
            } else {
                checkoutFile(autocomplete(args[1]), args[2]);
            }
            writeCurrentPointers();
        }
        else if (args[0].equals("reset")) {
            generateCurrentPointers();
            generateBlobs();
            generateCommits();
            reset(autocomplete(args[1]));
            writeCurrentPointers();
        }
        else if (args[0].equals("status")) {
            generateCurrentPointers();
            generateBlobs();
            status(); 
        }
        else if (args[0].equals("merge")) {
            generateCurrentPointers();
            generateBlobs();
            generateCommits();
            merge(args[1]);
        }
        else if (args[0].equals("print")) {
            generateCurrentPointers();
            generateBlobs();
            generateCommits();
            print();
        }
    }
    public void init() {
        File stageDir = new File(".gitlet/staged"), 
             trackedDir = new File(".gitlet/tracked"),
             untrackedDir = new File(".gitlet/untracked"),
             infoDir = new File(".gitlet/info");
        if (!stageDir.isDirectory() && !trackedDir.isDirectory() && !infoDir.isDirectory() && !untrackedDir.isDirectory()) {
            stageDir.mkdirs(); trackedDir.mkdirs(); infoDir.mkdirs(); untrackedDir.mkdirs();
            this._currentHeadPointer = new HeadPointer("master");
            makeFirstCommit();
            writeCurrentPointers();
            writeCommits(); 
        }
    }
    public void commit(String commitMessage, Date timestamp) {
        Commit temp = new Commit(commitMessage, this._currentHeadPointer.getCurrentCommit(), timestamp, this._stagedBlobs.keySet());
        mapCommitPointers(temp); 
        for (String blobSHA1: this._stagedBlobs.keySet()) {
            this._trackedBlobs.put(blobSHA1, temp.getSHA1());
        }
        removeUntrackedBlobs();
        this._currentHeadPointer.updateCommit(this._currentHeadPointer.getBranch(), temp.getSHA1());
        this._commitBindings.put(temp.getSHA1(), temp);
        this._stagedBlobs = new LinkedHashMap<String, String>();
        removeStagedBlobs();
    }
    public void log() {
        for (SHA1Tree<String> curr = getHeadPointer(); curr != null; curr = curr.getParent()) {
            printLogMessages(searchCommitSHA1(curr.getSHA1()));
        }
    }
    public void globalLog() {
        printGlobalLogMessages(this._commitSHA1Tree);
    }
    public void add(String fileName) {
        Blob stageBlob = new Blob(fileName, fileName);
        this._blobBindings.put(stageBlob.getSHA1(), stageBlob);
        this._stagedBlobs.put(stageBlob.getSHA1(), this._currentHeadPointer.getBranch());
    }
    public void find(String match) {
        findSHA1Print(this._commitSHA1Tree, match);
    }
    public void rm(String fileName) {
        if (!unStage(fileName) && !setUntracked(fileName))
            System.out.println("error");
    } 
    public void branch(String branch) {
        this._currentHeadPointer.addBranch(branch);
    }
    public void rmBranch(String branch) {
        if (!this._currentHeadPointer.removeBranch(branch)) {
            System.out.println("error");
        }
    }
    public void checkoutFile(String SHA1, String file) {
        Utils.writeContents(new File(file), searchCommitSHA1(SHA1).getBlob(file).getContents());
    }
    public void checkoutBranch(String branch) {
        resetCommit(this._currentHeadPointer.getCurrentCommit(branch));
        this._currentHeadPointer.makeHeadBranch(branch);
    }
    public void reset(String identifier) {
        resetCommit(identifier);
        this._currentHeadPointer.makeHeadBranch(this._currentHeadPointer.getBranch(), identifier);
    }
    public void status() {
        System.out.println("=== Branches ===");
        printBranches();
        System.out.println("\n=== Staged Files ===");
        printFiles(this._stagedBlobs);
        System.out.println("\n=== Removed Files ===");
        printFiles(this._untrackedBlobs);
    }
    public void merge(String branch) {
        String commonIdentifier = findCommonIdentifier(getHeadPointer(), getHeadPointer(branch)).getSHA1();
        if (this._currentHeadPointer.getCurrentCommit(branch).equals(commonIdentifier)) {
            System.out.println("Given branch is an ancestor of the current branch.");
            return;
        } else if (this._currentHeadPointer.getCurrentCommit().equals(commonIdentifier)) {
            reset(this._currentHeadPointer.getCurrentCommit(branch));
            System.out.println("Current branch fast-forwarded.");
            return;
        }
        // ifs
    }
    private SHA1Tree<String> findCommonIdentifier(SHA1Tree<String> left, SHA1Tree<String> right) {
        if (left == null || right == null) {
            return null;
        } else if (left == right) {
            return left;
        } else {
            SHA1Tree<String> left_parent = findCommonIdentifier(left.getParent(), right), right_parent = findCommonIdentifier(left, right.getParent());
            return left_parent == null? right_parent : left_parent;
        }
    } 
    private void printBranches() {
        List<String> branches = this._currentHeadPointer.getBranches();
        Collections.sort(branches);
        for (String branch: branches) {
            if (branch.equals(this._currentHeadPointer.getBranch())) {
                System.out.print("*");
            }
            System.out.println(branch);
        }
    }
    private void printFiles(Map<String, String> blobs) {
        List<String> toPrint = new ArrayList<String>(); 
        for (String blob: blobs.keySet()) {
            toPrint.add(searchBlobSHA1(blob).getIdentity());
        }
        Collections.sort(toPrint);
        for (String filename: toPrint) {
            System.out.println(filename);
        }
    }
    private void resetCommit(String identifier) {
        for (String filename: searchCommitSHA1(this._currentHeadPointer.getCurrentCommit()).getTrackedFiles().keySet()) {
            new File(filename).delete();
        }
        for (Map.Entry<String, String> fileHash: searchCommitSHA1(identifier).getTrackedFiles().entrySet()) {
            Utils.writeContents(new File(fileHash.getKey()), searchBlobSHA1(fileHash.getValue()).getContents()); 
        }
        removeStagedBlobs();
    }
    private void removeUntrackedBlobs() {
        for (String loc: Utils.plainFilenamesIn(new File(".gitlet/untracked/"))) {
            new File(".gitlet/untracked/" + loc).delete();
        }
    }
    private boolean unStage(String fileName) {
        for (String blob: this._stagedBlobs.keySet()) {
            if (searchBlobSHA1(blob).getIdentity().equals(fileName) &&
                this._stagedBlobs.get(blob).equals(this._currentHeadPointer.getBranch())) {
                this._stagedBlobs.remove(blob);
                new File(".gitlet/staged/" + fileName +  "_" + blob).delete();    
                return true;
            }
        }
        return false;
    }
    private boolean setUntracked(String fileName) {
        for (String blob: this._trackedBlobs.keySet()) {
            if (searchBlobSHA1(blob).getIdentity().equals(fileName) &&
                searchCommitSHA1(this._currentHeadPointer.getCurrentCommit()).isTracked(blob)) {
                Utils.writeContents(new File(".gitlet/untracked/" + searchBlobSHA1(blob).getIdentity() + "_" + this._currentHeadPointer.getBranch() + "-" + blob), searchBlobSHA1(blob).toString());
                return true;
            }
        }
        return false;
    }
    private SHA1Tree<String> getHeadPointer(String identifier) {
        for (SHA1Tree<String> temp: this._currentCommitPointers.keySet()) {
            if (temp.getSHA1().equals(identifier)) {
                return temp; 
            }
        }
        return null;
    }
    private SHA1Tree<String> getHeadPointer() {
        return getHeadPointer(this._currentHeadPointer.getCurrentCommit());
    }
    
    private void writeCurrentPointers() {
        for (String loc: Utils.plainFilenamesIn(new File(".gitlet"))) {
            new File(".gitlet/" + loc).delete();
        }
        Utils.writeContents(new File(".gitlet/" + this._currentHeadPointer.getBranch()), this._currentHeadPointer.toString());
    }
    private void removeStagedBlobs() {
        for (String loc: Utils.plainFilenamesIn(new File(".gitlet/staged"))) {
            new File(".gitlet/staged/" + loc).delete();
        }
    }
    private void writeCommits() {
        for (Map.Entry<String, Commit> hashCommit: this._commitBindings.entrySet()) {
            File commitFile = new File(".gitlet/info/" + hashCommit.getValue().getParent() + "_" + hashCommit.getKey());
            Utils.writeContents(commitFile, hashCommit.getValue().toString());
        } 
    }
    private void writeCommitsRecursive(SHA1Tree<String> commitTree) {
        if (commitTree != null) {
            String parent = "";
            if (commitTree.getParent() == null) {
                parent = "null";
            } else {
                parent = commitTree.getParent().getSHA1();
            }
            File commitFile = new File(".gitlet/info/" + parent + "_" + commitTree.getSHA1());
            Utils.writeContents(commitFile, searchCommitSHA1(commitTree.getSHA1()).toString());
            for (SHA1Tree<String> child: commitTree.getChildren()) {
                writeCommitsRecursive(child);
            }
        }
    }
    private void writeBlobs() {
        writeStagedBlobs();
        writeTrackedBlobs();
    }
    private void writeTrackedBlobs() {
        for (Map.Entry<String, String> blobBranch: this._trackedBlobs.entrySet()) {
            File branchDir = new File(".gitlet/tracked/" + blobBranch.getValue());
            if (!branchDir.isDirectory()) { 
                branchDir.mkdirs();
            }
            File blobFile = new File(".gitlet/tracked/" + blobBranch.getValue() + "/" + searchBlobSHA1(blobBranch.getKey()).getIdentity() + "_" + blobBranch.getKey());
            Utils.writeContents(blobFile, searchBlobSHA1(blobBranch.getKey()).toString());
        }
    }
    private void writeStagedBlobs() {
        for (Map.Entry<String, String> blobBranch: this._stagedBlobs.entrySet()) {
            File blobFile = new File(".gitlet/staged/" + searchBlobSHA1(blobBranch.getKey()).getIdentity() + "_" + blobBranch.getKey());
            Utils.writeContents(blobFile, searchBlobSHA1(blobBranch.getKey()).toString());
        }
    }
    private void generateCurrentPointers() {
        this._currentHeadPointer = new HeadPointer(new File(".gitlet/" + Utils.plainFilenamesIn(new File(".gitlet/")).get(0))); 
    }
    private void generateBlobs() {
        this._blobBindings = new LinkedHashMap<String, Blob>();
        generateTrackedBlobs(new File(".gitlet/tracked"));
        generateStagedBlobs(new File(".gitlet/staged"));
        generateUntrackedBlobs(new File(".gitlet/untracked"));
    }
    private void generateCommits() {
        List<String> commits = Utils.plainFilenamesIn(new File(".gitlet/info"));
        Map<String, List<String>> parentChildrenMap = processCommitMap(commits);
        generateCommitTree(parentChildrenMap);
        generateCommitBindings(commits);
    }
    private void generateCommitBindings(List<String> commits) {
        this._commitBindings = new LinkedHashMap<String, Commit>();
        for (String commitName: commits) {
            String[] commitContents = Utils.readContentsAsString(new File(".gitlet/info/" + commitName)).split("-");
            Commit temp = null;
            if (commitContents[3].equals(" ")) {
                temp = new Commit(commitContents[0], commitContents[1], new Date(Long.parseLong(commitContents[2])), new LinkedHashSet<String>()); 
            } else {
                String[] blobContents = commitContents[3].trim().split("_");
                Set<String> blobs = new LinkedHashSet<String>(Arrays.asList(blobContents));
                temp = new Commit(commitContents[0], commitContents[1], new Date(Long.parseLong(commitContents[2])), blobs);
            }
            this._commitBindings.put(temp.getSHA1(), temp);
        } 
    }
    private Map<String, List<String>> processCommitMap(List<String> commits) {
        Set<String> uniqueParents = new LinkedHashSet<String>();
        Map<String, List<String>> parentChildrenMap = new LinkedHashMap<String, List<String>>();
        for (String parentChild: commits) {
            if (!uniqueParents.contains(parentChild.split("_")[0])) {
                uniqueParents.add(parentChild.split("_")[0]);
            }
        }
        for (String uniqueParent: uniqueParents) {
            List<String> temp = new ArrayList<String>();
            for (String parentChild: commits) {
                if (uniqueParent.equals(parentChild.split("_")[0])) {
                    temp.add(parentChild.split("_")[1]);
                }
            }
            parentChildrenMap.put(uniqueParent, temp);
        }
        return parentChildrenMap;
    }
    private void generateCommitTree(Map<String, List<String>> parentChildrenMap) {
        this._currentCommitPointers = new LinkedHashMap<SHA1Tree<String>, SHA1Tree<String>>();
        this._commitSHA1Tree = new SHA1Tree<String>(parentChildrenMap.get("null").get(0));
        parentChildrenMap.remove("null");
        recursiveGen(parentChildrenMap, this._commitSHA1Tree);
        if (this._currentCommitPointers.isEmpty())
            this._currentCommitPointers.put(this._commitSHA1Tree, null);
    }
    private void recursiveGen(Map<String, List<String>> parentChildrenMap, SHA1Tree<String> tree) {
        if (parentChildrenMap.get(tree.getSHA1()) == null) {
            return;
        }  
        else {
            for (String child: parentChildrenMap.get(tree.getSHA1())) {
                SHA1Tree<String> childTree = tree.addChild(child);
                if (this._currentHeadPointer.isHeadPointer(childTree.getSHA1()))
                    this._currentCommitPointers.put(childTree, childTree.getParent());
                recursiveGen(parentChildrenMap, childTree);
            } 
        }
    }
    private void generateTrackedBlobs(File trackedDir) {
        this._trackedBlobs = new LinkedHashMap<String, String>();
        String[] commits = trackedDir.list(new FilenameFilter() {
            public boolean accept(File current, String name) {
                return new File(current, name).isDirectory();
            }
        });
        for (String commitName: commits) {
            List<String> blobs = Utils.plainFilenamesIn(new File(trackedDir + "/" + commitName));
            for (String blobLoc: blobs) {
                String[] split = blobLoc.split("_", 2);
                Blob temp = new Blob(trackedDir + "/" + commitName + "/" + blobLoc, split[0]);
                this._blobBindings.put(temp.getSHA1(), temp);
                this._trackedBlobs.put(temp.getSHA1(), commitName);
            }
        }
    }
    private void generateStagedBlobs(File stagedDir) {
        this._stagedBlobs = new LinkedHashMap<String, String>();
        List<String> blobs = Utils.plainFilenamesIn(stagedDir);
        for (String blobLoc: blobs) {
            String[] split = blobLoc.split("_", 2);
            Blob temp = new Blob(stagedDir + "/" + blobLoc, split[0]);
            this._blobBindings.put(temp.getSHA1(), temp);
            this._stagedBlobs.put(temp.getSHA1(), this._currentHeadPointer.getBranch());
        }
    }
    private void generateUntrackedBlobs(File untrackedDir) {
        this._untrackedBlobs = new LinkedHashMap<String, String>();
        List<String> blobs = Utils.plainFilenamesIn(untrackedDir);
        for (String blobLoc: blobs) {
            String[] splitBlob = blobLoc.split("_", 2);
            String[] splitBranch = splitBlob[1].split("-", 2);
            Blob temp = new Blob(untrackedDir + "/" + blobLoc, splitBlob[0]);
            this._blobBindings.put(temp.getSHA1(), temp);
            this._untrackedBlobs.put(temp.getSHA1(), splitBranch[0]);
        }
    }
    private void makeFirstCommit() {
        commit("initial commit", new Date(0)); 
    } 
    private void mapCommitPointers(Commit commit) {
        if (this._currentCommitPointers == null) {
            this._commitSHA1Tree = new SHA1Tree<String>(commit.getSHA1());
            this._currentCommitPointers = new LinkedHashMap<SHA1Tree<String>, SHA1Tree<String>>();
            this._currentCommitPointers.put(this._commitSHA1Tree, null);
        }
        else {
            SHA1Tree<String> parent = null;
            for (SHA1Tree<String> i: this._currentCommitPointers.keySet()) {
                if (i.getSHA1().equals(commit.getParent())) {
                    parent = i;
                }
            }
            commit.updateBlobs(processModifications(commit, searchCommitSHA1(parent.getSHA1())));
            this._currentCommitPointers.put(parent.addChild(commit.getSHA1()), parent);
            this._currentCommitPointers.remove(parent);
        } 
    } 
    private Set<String> processModifications(Commit commit, Commit parent) {
        Map<String, String> parentBlobs = parent.getTrackedFiles();
        Map<String, String> commitBlobs = commit.getTrackedFiles();
        parentBlobs.keySet().removeAll(commitBlobs.keySet());
        for (Map.Entry<String, String> untracked: this._untrackedBlobs.entrySet()) {
            if (parent.isTracked(untracked.getKey()) && this._currentHeadPointer.getBranch().equals(untracked.getValue()))
                parentBlobs.values().remove(untracked.getKey());
        }
        return new LinkedHashSet<String>(parentBlobs.values());
    } 
    private void printGlobalLogMessages(SHA1Tree<String> commitTree) {
        if (commitTree != null) {
            printLogMessages(searchCommitSHA1(commitTree.getSHA1()));
            for (SHA1Tree<String> child: commitTree.getChildren())
                printGlobalLogMessages(child);
        }
    }
    private void findSHA1Print(SHA1Tree<String> commitTree, String match) {
        if (commitTree != null) {
            if (searchCommitSHA1(commitTree.getSHA1()).getCommitMessage().equals(match)) {
                System.out.println(commitTree.getSHA1());
            }
            for (SHA1Tree<String> child: commitTree.getChildren())
                findSHA1Print(child, match);
        }
    }
    private void printLogMessages(Commit commit) {
        String date = new SimpleDateFormat("E MMM dd HH:mm:ss yyyy Z").format(commit.getTimeStamp());  
        System.out.println( "===\n" + 
                            "commit " + commit.getSHA1() + "\n" +
                            "Date: " + date + "\n" +
                            commit.getCommitMessage());                         
    }
    private String autocomplete(String shortIdentifier) {
        if (this._commitBindings == null)
            return null;
        for (String identifier: this._commitBindings.keySet()) {
            if (identifier.indexOf(shortIdentifier) != -1) {
                return identifier;
            }
        }
        return null;
    }
    public static Commit searchCommitSHA1(String match) {
        if (_commitBindings == null) 
            return null;
        return _commitBindings.get(match);
    }  
    public static Blob searchBlobSHA1(String match) {
        if (_blobBindings == null)
            return null;
        return _blobBindings.get(match);
    }
    private void print() {
            System.out.println( "blobBindings: " + this._blobBindings + "\n" +
                                "commitBindings: " + this._commitBindings + "\n" +
                                "stagedBlobs: " + this._stagedBlobs + "\n" +
                                "trackedBlobs: " + this._trackedBlobs + "\n" + 
                                "currentCommitPointers: " + this._currentCommitPointers + "\n" +
                                "commitSHA1Tree: " + SHA1Tree.printTree(this._commitSHA1Tree) + "\n" +
                                "untrackedBlobs: " + this._untrackedBlobs + "\n" +  
                                "head commit pointer: " + this._currentHeadPointer
                    ); }
}
