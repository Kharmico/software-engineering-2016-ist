package pt.tecnico.myDrive.domain;

import pt.ist.fenixframework.backend.jvstmojb.pstm.RelationList;
import pt.ist.fenixframework.backend.jvstmojb.ojb.OJBFunctionalSetWrapper;
import pt.ist.fenixframework.ValueTypeSerializer;


@SuppressWarnings("all")
public abstract class Directory_Base extends pt.tecnico.myDrive.domain.File {
    // Static Slots
    public final static pt.ist.fenixframework.dml.runtime.RoleMany<pt.tecnico.myDrive.domain.Directory,pt.tecnico.myDrive.domain.File> role$$files = new pt.ist.fenixframework.dml.runtime.RoleMany<pt.tecnico.myDrive.domain.Directory,pt.tecnico.myDrive.domain.File>() {
        public pt.ist.fenixframework.dml.runtime.RelationBaseSet<pt.tecnico.myDrive.domain.File> getSet(pt.tecnico.myDrive.domain.Directory o1) {
            return ((Directory_Base)o1).get$rl$files();
        }
        public pt.ist.fenixframework.dml.runtime.Role<pt.tecnico.myDrive.domain.File,pt.tecnico.myDrive.domain.Directory> getInverseRole() {
            return pt.tecnico.myDrive.domain.File.role$$parentDirectory;
        }
        
    };
    public final static pt.ist.fenixframework.dml.runtime.RoleOne<pt.tecnico.myDrive.domain.Directory,pt.tecnico.myDrive.domain.MyDriveManager> role$$myDriveManager = new pt.ist.fenixframework.dml.runtime.RoleOne<pt.tecnico.myDrive.domain.Directory,pt.tecnico.myDrive.domain.MyDriveManager>() {
        @Override
        public pt.tecnico.myDrive.domain.MyDriveManager getValue(pt.tecnico.myDrive.domain.Directory o1) {
            return ((Directory_Base.DO_State)o1.get$obj$state(false)).myDriveManager;
        }
        @Override
        public void setValue(pt.tecnico.myDrive.domain.Directory o1, pt.tecnico.myDrive.domain.MyDriveManager o2) {
            ((Directory_Base.DO_State)o1.get$obj$state(true)).myDriveManager = o2;
        }
        public pt.ist.fenixframework.dml.runtime.Role<pt.tecnico.myDrive.domain.MyDriveManager,pt.tecnico.myDrive.domain.Directory> getInverseRole() {
            return pt.tecnico.myDrive.domain.MyDriveManager.role$$currentDirectory;
        }
        
    };
    public final static pt.ist.fenixframework.dml.runtime.RoleOne<pt.tecnico.myDrive.domain.Directory,pt.tecnico.myDrive.domain.User> role$$user = new pt.ist.fenixframework.dml.runtime.RoleOne<pt.tecnico.myDrive.domain.Directory,pt.tecnico.myDrive.domain.User>() {
        @Override
        public pt.tecnico.myDrive.domain.User getValue(pt.tecnico.myDrive.domain.Directory o1) {
            return ((Directory_Base.DO_State)o1.get$obj$state(false)).user;
        }
        @Override
        public void setValue(pt.tecnico.myDrive.domain.Directory o1, pt.tecnico.myDrive.domain.User o2) {
            ((Directory_Base.DO_State)o1.get$obj$state(true)).user = o2;
        }
        public pt.ist.fenixframework.dml.runtime.Role<pt.tecnico.myDrive.domain.User,pt.tecnico.myDrive.domain.Directory> getInverseRole() {
            return pt.tecnico.myDrive.domain.User.role$$homeDirectory;
        }
        
    };
    public final static pt.ist.fenixframework.dml.runtime.RoleOne<pt.tecnico.myDrive.domain.Directory,pt.tecnico.myDrive.domain.FileSystem> role$$filesystem = new pt.ist.fenixframework.dml.runtime.RoleOne<pt.tecnico.myDrive.domain.Directory,pt.tecnico.myDrive.domain.FileSystem>() {
        @Override
        public pt.tecnico.myDrive.domain.FileSystem getValue(pt.tecnico.myDrive.domain.Directory o1) {
            return ((Directory_Base.DO_State)o1.get$obj$state(false)).filesystem;
        }
        @Override
        public void setValue(pt.tecnico.myDrive.domain.Directory o1, pt.tecnico.myDrive.domain.FileSystem o2) {
            ((Directory_Base.DO_State)o1.get$obj$state(true)).filesystem = o2;
        }
        public pt.ist.fenixframework.dml.runtime.Role<pt.tecnico.myDrive.domain.FileSystem,pt.tecnico.myDrive.domain.Directory> getInverseRole() {
            return pt.tecnico.myDrive.domain.FileSystem.role$$slash;
        }
        
    };
    
    private final static class DirectoryHasFiles {
        private static final pt.ist.fenixframework.backend.jvstmojb.pstm.LoggingRelation<pt.tecnico.myDrive.domain.Directory,pt.tecnico.myDrive.domain.File> relation = new pt.ist.fenixframework.backend.jvstmojb.pstm.LoggingRelation<pt.tecnico.myDrive.domain.Directory,pt.tecnico.myDrive.domain.File>(role$$files, "DirectoryHasFiles");
    }
    public static pt.ist.fenixframework.dml.runtime.DirectRelation<pt.tecnico.myDrive.domain.Directory,pt.tecnico.myDrive.domain.File> getRelationDirectoryHasFiles() {
        return DirectoryHasFiles.relation;
    }
    
    static {
        DirectoryHasFiles.relation.setRelationName("pt.tecnico.myDrive.domain.Directory.DirectoryHasFiles");
    }
    private static pt.ist.fenixframework.dml.runtime.KeyFunction<Comparable<?>,pt.tecnico.myDrive.domain.File> keyFunction$$files = new pt.ist.fenixframework.dml.runtime.KeyFunction<Comparable<?>,pt.tecnico.myDrive.domain.File>() { public Comparable<?> getKey(pt.tecnico.myDrive.domain.File value) { return value.getOid(); } public boolean allowMultipleKeys() {return false; }};
    
    public static pt.ist.fenixframework.dml.runtime.DirectRelation<pt.tecnico.myDrive.domain.MyDriveManager,pt.tecnico.myDrive.domain.Directory> getRelationMyDriveManagerHasCurrentDirectoryy() {
        return pt.tecnico.myDrive.domain.MyDriveManager.getRelationMyDriveManagerHasCurrentDirectoryy();
    }
    
    public static pt.ist.fenixframework.dml.runtime.DirectRelation<pt.tecnico.myDrive.domain.User,pt.tecnico.myDrive.domain.Directory> getRelationUserHasHomeDirectory() {
        return pt.tecnico.myDrive.domain.User.getRelationUserHasHomeDirectory();
    }
    
    private final static class FileSystemHasFiles {
        private static final pt.ist.fenixframework.backend.jvstmojb.pstm.LoggingRelation<pt.tecnico.myDrive.domain.Directory,pt.tecnico.myDrive.domain.FileSystem> relation = new pt.ist.fenixframework.backend.jvstmojb.pstm.LoggingRelation<pt.tecnico.myDrive.domain.Directory,pt.tecnico.myDrive.domain.FileSystem>(role$$filesystem, "FileSystemHasFiles");
    }
    public static pt.ist.fenixframework.dml.runtime.DirectRelation<pt.tecnico.myDrive.domain.Directory,pt.tecnico.myDrive.domain.FileSystem> getRelationFileSystemHasFiles() {
        return FileSystemHasFiles.relation;
    }
    
    static {
        FileSystemHasFiles.relation.setRelationName("pt.tecnico.myDrive.domain.Directory.FileSystemHasFiles");
    }
    
    // Slots
    
    // Role Slots
    private RelationList<pt.tecnico.myDrive.domain.Directory,pt.tecnico.myDrive.domain.File> get$rl$files() {
        return get$$relationList("files", getRelationDirectoryHasFiles());
        
    }
    
    // Init Instance
    
    private void initInstance() {
        init$Instance(true);
    }
    
    @Override
    protected void init$Instance(boolean allocateOnly) {
        super.init$Instance(allocateOnly);
        
    }
    
    // Constructors
    protected  Directory_Base() {
        super();
    }
    
    // Getters and Setters
    
    // Role Methods
    
    public void addFiles(pt.tecnico.myDrive.domain.File files) {
        getRelationDirectoryHasFiles().add((pt.tecnico.myDrive.domain.Directory)this, files);
    }
    
    public void removeFiles(pt.tecnico.myDrive.domain.File files) {
        getRelationDirectoryHasFiles().remove((pt.tecnico.myDrive.domain.Directory)this, files);
    }
    
    public java.util.Set<pt.tecnico.myDrive.domain.File> getFilesSet() {
        return get$rl$files();
    }
    
    public void set$files(OJBFunctionalSetWrapper files) {
        get$rl$files().setFromOJB(this, "files", files);
    }
    
    @Deprecated
    public java.util.Set<pt.tecnico.myDrive.domain.File> getFiles() {
        return getFilesSet();
    }
    
    @Deprecated
    public int getFilesCount() {
        return getFilesSet().size();
    }
    
    @pt.ist.fenixframework.consistencyPredicates.ConsistencyPredicate
    public final boolean checkMultiplicityOfFiles() {
        if (get$rl$files().size() < 2) return false;
        return true;
    }
    
    public pt.tecnico.myDrive.domain.MyDriveManager getMyDriveManager() {
        return ((DO_State)this.get$obj$state(false)).myDriveManager;
    }
    
    public void setMyDriveManager(pt.tecnico.myDrive.domain.MyDriveManager myDriveManager) {
        getRelationMyDriveManagerHasCurrentDirectoryy().add(myDriveManager, (pt.tecnico.myDrive.domain.Directory)this);
    }
    
    private java.lang.Long get$oidMyDriveManager() {
        pt.ist.fenixframework.backend.jvstmojb.pstm.AbstractDomainObject value = (pt.ist.fenixframework.backend.jvstmojb.pstm.AbstractDomainObject) ((DO_State)this.get$obj$state(false)).myDriveManager;
        return (value == null) ? null : value.getOid();
    }
    
    @pt.ist.fenixframework.consistencyPredicates.ConsistencyPredicate
    public final boolean checkMultiplicityOfMyDriveManager() {
        if (getMyDriveManager() == null) return false;
        return true;
    }
    
    public pt.tecnico.myDrive.domain.User getUser() {
        return ((DO_State)this.get$obj$state(false)).user;
    }
    
    public void setUser(pt.tecnico.myDrive.domain.User user) {
        getRelationUserHasHomeDirectory().add(user, (pt.tecnico.myDrive.domain.Directory)this);
    }
    
    private java.lang.Long get$oidUser() {
        pt.ist.fenixframework.backend.jvstmojb.pstm.AbstractDomainObject value = (pt.ist.fenixframework.backend.jvstmojb.pstm.AbstractDomainObject) ((DO_State)this.get$obj$state(false)).user;
        return (value == null) ? null : value.getOid();
    }
    
    @pt.ist.fenixframework.consistencyPredicates.ConsistencyPredicate
    public final boolean checkMultiplicityOfUser() {
        if (getUser() == null) return false;
        return true;
    }
    
    public pt.tecnico.myDrive.domain.FileSystem getFilesystem() {
        return ((DO_State)this.get$obj$state(false)).filesystem;
    }
    
    public void setFilesystem(pt.tecnico.myDrive.domain.FileSystem filesystem) {
        getRelationFileSystemHasFiles().add((pt.tecnico.myDrive.domain.Directory)this, filesystem);
    }
    
    private java.lang.Long get$oidFilesystem() {
        pt.ist.fenixframework.backend.jvstmojb.pstm.AbstractDomainObject value = (pt.ist.fenixframework.backend.jvstmojb.pstm.AbstractDomainObject) ((DO_State)this.get$obj$state(false)).filesystem;
        return (value == null) ? null : value.getOid();
    }
    
    @pt.ist.fenixframework.consistencyPredicates.ConsistencyPredicate
    public final boolean checkMultiplicityOfFilesystem() {
        if (getFilesystem() == null) return false;
        return true;
    }
    
    
    protected void checkDisconnected() {
        super.checkDisconnected();
        DO_State castedState = (DO_State)this.get$obj$state(false);
        if (get$rl$files().size() > 0) handleAttemptToDeleteConnectedObject("Files");
        if (castedState.myDriveManager != null) handleAttemptToDeleteConnectedObject("MyDriveManager");
        if (castedState.user != null) handleAttemptToDeleteConnectedObject("User");
        if (castedState.filesystem != null) handleAttemptToDeleteConnectedObject("Filesystem");
        
    }
    
    protected void readStateFromResultSet(java.sql.ResultSet rs, pt.ist.fenixframework.backend.jvstmojb.pstm.OneBoxDomainObject.DO_State  state) throws java.sql.SQLException {
        super.readStateFromResultSet(rs, state);
        DO_State castedState = (DO_State)state;
        castedState.myDriveManager = pt.ist.fenixframework.backend.jvstmojb.repository.ResultSetReader.readDomainObject(rs, "OID_MY_DRIVE_MANAGER");
        castedState.user = pt.ist.fenixframework.backend.jvstmojb.repository.ResultSetReader.readDomainObject(rs, "OID_USER");
        castedState.filesystem = pt.ist.fenixframework.backend.jvstmojb.repository.ResultSetReader.readDomainObject(rs, "OID_FILESYSTEM");
    }
    protected pt.ist.fenixframework.dml.runtime.Relation get$$relationFor(String attrName) {
        if (attrName.equals("files")) return getRelationDirectoryHasFiles();
        return super.get$$relationFor(attrName);
        
    }
    protected pt.ist.fenixframework.backend.jvstmojb.pstm.OneBoxDomainObject.DO_State  make$newState() {
        return new DO_State();
        
    }
    protected void create$allLists() {
        super.create$allLists();
        get$$relationList("files", getRelationDirectoryHasFiles());
        
    }
    protected static class DO_State extends pt.tecnico.myDrive.domain.File.DO_State {
        private pt.tecnico.myDrive.domain.MyDriveManager myDriveManager;
        private pt.tecnico.myDrive.domain.User user;
        private pt.tecnico.myDrive.domain.FileSystem filesystem;
        protected void copyTo(pt.ist.fenixframework.backend.jvstmojb.pstm.OneBoxDomainObject.DO_State  newState) {
            super.copyTo(newState);
            DO_State newCasted = (DO_State)newState;
            newCasted.myDriveManager = this.myDriveManager;
            newCasted.user = this.user;
            newCasted.filesystem = this.filesystem;
            
        }
        
    }
    
}
