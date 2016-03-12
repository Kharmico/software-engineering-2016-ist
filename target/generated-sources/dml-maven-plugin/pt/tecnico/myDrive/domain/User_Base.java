package pt.tecnico.myDrive.domain;

import pt.ist.fenixframework.backend.jvstmojb.pstm.RelationList;
import pt.ist.fenixframework.backend.jvstmojb.ojb.OJBFunctionalSetWrapper;
import pt.ist.fenixframework.ValueTypeSerializer;


@SuppressWarnings("all")
public abstract class User_Base extends pt.ist.fenixframework.backend.jvstmojb.pstm.OneBoxDomainObject {
    // Static Slots
    public final static pt.ist.fenixframework.dml.runtime.RoleOne<pt.tecnico.myDrive.domain.User,pt.tecnico.myDrive.domain.File> role$$file = new pt.ist.fenixframework.dml.runtime.RoleOne<pt.tecnico.myDrive.domain.User,pt.tecnico.myDrive.domain.File>() {
        @Override
        public pt.tecnico.myDrive.domain.File getValue(pt.tecnico.myDrive.domain.User o1) {
            return ((User_Base.DO_State)o1.get$obj$state(false)).file;
        }
        @Override
        public void setValue(pt.tecnico.myDrive.domain.User o1, pt.tecnico.myDrive.domain.File o2) {
            ((User_Base.DO_State)o1.get$obj$state(true)).file = o2;
        }
        public pt.ist.fenixframework.dml.runtime.Role<pt.tecnico.myDrive.domain.File,pt.tecnico.myDrive.domain.User> getInverseRole() {
            return pt.tecnico.myDrive.domain.File.role$$owner;
        }
        
    };
    public final static pt.ist.fenixframework.dml.runtime.RoleOne<pt.tecnico.myDrive.domain.User,pt.tecnico.myDrive.domain.Directory> role$$homeDirectory = new pt.ist.fenixframework.dml.runtime.RoleOne<pt.tecnico.myDrive.domain.User,pt.tecnico.myDrive.domain.Directory>() {
        @Override
        public pt.tecnico.myDrive.domain.Directory getValue(pt.tecnico.myDrive.domain.User o1) {
            return ((User_Base.DO_State)o1.get$obj$state(false)).homeDirectory;
        }
        @Override
        public void setValue(pt.tecnico.myDrive.domain.User o1, pt.tecnico.myDrive.domain.Directory o2) {
            ((User_Base.DO_State)o1.get$obj$state(true)).homeDirectory = o2;
        }
        public pt.ist.fenixframework.dml.runtime.Role<pt.tecnico.myDrive.domain.Directory,pt.tecnico.myDrive.domain.User> getInverseRole() {
            return pt.tecnico.myDrive.domain.Directory.role$$user;
        }
        
    };
    public final static pt.ist.fenixframework.dml.runtime.RoleOne<pt.tecnico.myDrive.domain.User,pt.tecnico.myDrive.domain.MyDriveManager> role$$myDriveManager = new pt.ist.fenixframework.dml.runtime.RoleOne<pt.tecnico.myDrive.domain.User,pt.tecnico.myDrive.domain.MyDriveManager>() {
        @Override
        public pt.tecnico.myDrive.domain.MyDriveManager getValue(pt.tecnico.myDrive.domain.User o1) {
            return ((User_Base.DO_State)o1.get$obj$state(false)).myDriveManager;
        }
        @Override
        public void setValue(pt.tecnico.myDrive.domain.User o1, pt.tecnico.myDrive.domain.MyDriveManager o2) {
            ((User_Base.DO_State)o1.get$obj$state(true)).myDriveManager = o2;
        }
        public pt.ist.fenixframework.dml.runtime.Role<pt.tecnico.myDrive.domain.MyDriveManager,pt.tecnico.myDrive.domain.User> getInverseRole() {
            return pt.tecnico.myDrive.domain.MyDriveManager.role$$currentUser;
        }
        
    };
    public final static pt.ist.fenixframework.dml.runtime.RoleOne<pt.tecnico.myDrive.domain.User,pt.tecnico.myDrive.domain.FileSystem> role$$filesystem = new pt.ist.fenixframework.dml.runtime.RoleOne<pt.tecnico.myDrive.domain.User,pt.tecnico.myDrive.domain.FileSystem>() {
        @Override
        public pt.tecnico.myDrive.domain.FileSystem getValue(pt.tecnico.myDrive.domain.User o1) {
            return ((User_Base.DO_State)o1.get$obj$state(false)).filesystem;
        }
        @Override
        public void setValue(pt.tecnico.myDrive.domain.User o1, pt.tecnico.myDrive.domain.FileSystem o2) {
            ((User_Base.DO_State)o1.get$obj$state(true)).filesystem = o2;
        }
        public pt.ist.fenixframework.dml.runtime.Role<pt.tecnico.myDrive.domain.FileSystem,pt.tecnico.myDrive.domain.User> getInverseRole() {
            return pt.tecnico.myDrive.domain.FileSystem.role$$users;
        }
        
    };
    
    private final static class FileHasOwner {
        private static final pt.ist.fenixframework.backend.jvstmojb.pstm.LoggingRelation<pt.tecnico.myDrive.domain.User,pt.tecnico.myDrive.domain.File> relation = new pt.ist.fenixframework.backend.jvstmojb.pstm.LoggingRelation<pt.tecnico.myDrive.domain.User,pt.tecnico.myDrive.domain.File>(role$$file, "FileHasOwner");
    }
    public static pt.ist.fenixframework.dml.runtime.DirectRelation<pt.tecnico.myDrive.domain.User,pt.tecnico.myDrive.domain.File> getRelationFileHasOwner() {
        return FileHasOwner.relation;
    }
    
    static {
        FileHasOwner.relation.setRelationName("pt.tecnico.myDrive.domain.User.FileHasOwner");
    }
    
    private final static class UserHasHomeDirectory {
        private static final pt.ist.fenixframework.backend.jvstmojb.pstm.LoggingRelation<pt.tecnico.myDrive.domain.User,pt.tecnico.myDrive.domain.Directory> relation = new pt.ist.fenixframework.backend.jvstmojb.pstm.LoggingRelation<pt.tecnico.myDrive.domain.User,pt.tecnico.myDrive.domain.Directory>(role$$homeDirectory, "UserHasHomeDirectory");
    }
    public static pt.ist.fenixframework.dml.runtime.DirectRelation<pt.tecnico.myDrive.domain.User,pt.tecnico.myDrive.domain.Directory> getRelationUserHasHomeDirectory() {
        return UserHasHomeDirectory.relation;
    }
    
    static {
        UserHasHomeDirectory.relation.setRelationName("pt.tecnico.myDrive.domain.User.UserHasHomeDirectory");
    }
    
    public static pt.ist.fenixframework.dml.runtime.DirectRelation<pt.tecnico.myDrive.domain.MyDriveManager,pt.tecnico.myDrive.domain.User> getRelationMyDriveManagerHasCurrentUser() {
        return pt.tecnico.myDrive.domain.MyDriveManager.getRelationMyDriveManagerHasCurrentUser();
    }
    
    private final static class FileSystemHasUsers {
        private static final pt.ist.fenixframework.backend.jvstmojb.pstm.LoggingRelation<pt.tecnico.myDrive.domain.User,pt.tecnico.myDrive.domain.FileSystem> relation = new pt.ist.fenixframework.backend.jvstmojb.pstm.LoggingRelation<pt.tecnico.myDrive.domain.User,pt.tecnico.myDrive.domain.FileSystem>(role$$filesystem, "FileSystemHasUsers");
    }
    public static pt.ist.fenixframework.dml.runtime.DirectRelation<pt.tecnico.myDrive.domain.User,pt.tecnico.myDrive.domain.FileSystem> getRelationFileSystemHasUsers() {
        return FileSystemHasUsers.relation;
    }
    
    static {
        FileSystemHasUsers.relation.setRelationName("pt.tecnico.myDrive.domain.User.FileSystemHasUsers");
    }
    
    // Slots
    
    // Role Slots
    
    // Init Instance
    
    private void initInstance() {
        init$Instance(true);
    }
    
    @Override
    protected void init$Instance(boolean allocateOnly) {
        super.init$Instance(allocateOnly);
        
    }
    
    // Constructors
    protected  User_Base() {
        super();
    }
    
    // Getters and Setters
    
    public java.lang.String getUsername() {
        return ((DO_State)this.get$obj$state(false)).username;
    }
    
    public void setUsername(java.lang.String username) {
        ((DO_State)this.get$obj$state(true)).username = username;
    }
    
    private java.lang.String get$username() {
        java.lang.String value = ((DO_State)this.get$obj$state(false)).username;
        return (value == null) ? null : pt.ist.fenixframework.backend.jvstmojb.repository.ToSqlConverter.getValueForString(value);
    }
    
    private final void set$username(java.lang.String value, pt.ist.fenixframework.backend.jvstmojb.pstm.OneBoxDomainObject.DO_State  obj$state) {
        ((DO_State)obj$state).username = (java.lang.String)((value == null) ? null : value);
    }
    
    public java.lang.String getPassword() {
        return ((DO_State)this.get$obj$state(false)).password;
    }
    
    public void setPassword(java.lang.String password) {
        ((DO_State)this.get$obj$state(true)).password = password;
    }
    
    private java.lang.String get$password() {
        java.lang.String value = ((DO_State)this.get$obj$state(false)).password;
        return (value == null) ? null : pt.ist.fenixframework.backend.jvstmojb.repository.ToSqlConverter.getValueForString(value);
    }
    
    private final void set$password(java.lang.String value, pt.ist.fenixframework.backend.jvstmojb.pstm.OneBoxDomainObject.DO_State  obj$state) {
        ((DO_State)obj$state).password = (java.lang.String)((value == null) ? null : value);
    }
    
    public java.lang.String getName() {
        return ((DO_State)this.get$obj$state(false)).name;
    }
    
    public void setName(java.lang.String name) {
        ((DO_State)this.get$obj$state(true)).name = name;
    }
    
    private java.lang.String get$name() {
        java.lang.String value = ((DO_State)this.get$obj$state(false)).name;
        return (value == null) ? null : pt.ist.fenixframework.backend.jvstmojb.repository.ToSqlConverter.getValueForString(value);
    }
    
    private final void set$name(java.lang.String value, pt.ist.fenixframework.backend.jvstmojb.pstm.OneBoxDomainObject.DO_State  obj$state) {
        ((DO_State)obj$state).name = (java.lang.String)((value == null) ? null : value);
    }
    
    public java.lang.String getUmask() {
        return ((DO_State)this.get$obj$state(false)).umask;
    }
    
    public void setUmask(java.lang.String umask) {
        ((DO_State)this.get$obj$state(true)).umask = umask;
    }
    
    private java.lang.String get$umask() {
        java.lang.String value = ((DO_State)this.get$obj$state(false)).umask;
        return (value == null) ? null : pt.ist.fenixframework.backend.jvstmojb.repository.ToSqlConverter.getValueForString(value);
    }
    
    private final void set$umask(java.lang.String value, pt.ist.fenixframework.backend.jvstmojb.pstm.OneBoxDomainObject.DO_State  obj$state) {
        ((DO_State)obj$state).umask = (java.lang.String)((value == null) ? null : value);
    }
    
    // Role Methods
    
    public pt.tecnico.myDrive.domain.File getFile() {
        return ((DO_State)this.get$obj$state(false)).file;
    }
    
    public void setFile(pt.tecnico.myDrive.domain.File file) {
        getRelationFileHasOwner().add((pt.tecnico.myDrive.domain.User)this, file);
    }
    
    private java.lang.Long get$oidFile() {
        pt.ist.fenixframework.backend.jvstmojb.pstm.AbstractDomainObject value = (pt.ist.fenixframework.backend.jvstmojb.pstm.AbstractDomainObject) ((DO_State)this.get$obj$state(false)).file;
        return (value == null) ? null : value.getOid();
    }
    
    @pt.ist.fenixframework.consistencyPredicates.ConsistencyPredicate
    public final boolean checkMultiplicityOfFile() {
        if (getFile() == null) return false;
        return true;
    }
    
    public pt.tecnico.myDrive.domain.Directory getHomeDirectory() {
        return ((DO_State)this.get$obj$state(false)).homeDirectory;
    }
    
    public void setHomeDirectory(pt.tecnico.myDrive.domain.Directory homeDirectory) {
        getRelationUserHasHomeDirectory().add((pt.tecnico.myDrive.domain.User)this, homeDirectory);
    }
    
    private java.lang.Long get$oidHomeDirectory() {
        pt.ist.fenixframework.backend.jvstmojb.pstm.AbstractDomainObject value = (pt.ist.fenixframework.backend.jvstmojb.pstm.AbstractDomainObject) ((DO_State)this.get$obj$state(false)).homeDirectory;
        return (value == null) ? null : value.getOid();
    }
    
    @pt.ist.fenixframework.consistencyPredicates.ConsistencyPredicate
    public final boolean checkMultiplicityOfHomeDirectory() {
        if (getHomeDirectory() == null) return false;
        return true;
    }
    
    public pt.tecnico.myDrive.domain.MyDriveManager getMyDriveManager() {
        return ((DO_State)this.get$obj$state(false)).myDriveManager;
    }
    
    public void setMyDriveManager(pt.tecnico.myDrive.domain.MyDriveManager myDriveManager) {
        getRelationMyDriveManagerHasCurrentUser().add(myDriveManager, (pt.tecnico.myDrive.domain.User)this);
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
    
    public pt.tecnico.myDrive.domain.FileSystem getFilesystem() {
        return ((DO_State)this.get$obj$state(false)).filesystem;
    }
    
    public void setFilesystem(pt.tecnico.myDrive.domain.FileSystem filesystem) {
        getRelationFileSystemHasUsers().add((pt.tecnico.myDrive.domain.User)this, filesystem);
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
        DO_State castedState = (DO_State)this.get$obj$state(false);
        if (castedState.file != null) handleAttemptToDeleteConnectedObject("File");
        if (castedState.homeDirectory != null) handleAttemptToDeleteConnectedObject("HomeDirectory");
        if (castedState.myDriveManager != null) handleAttemptToDeleteConnectedObject("MyDriveManager");
        if (castedState.filesystem != null) handleAttemptToDeleteConnectedObject("Filesystem");
        
    }
    
    protected void readStateFromResultSet(java.sql.ResultSet rs, pt.ist.fenixframework.backend.jvstmojb.pstm.OneBoxDomainObject.DO_State  state) throws java.sql.SQLException {
        DO_State castedState = (DO_State)state;
        set$username(pt.ist.fenixframework.backend.jvstmojb.repository.ResultSetReader.readString(rs, "USERNAME"), state);
        set$password(pt.ist.fenixframework.backend.jvstmojb.repository.ResultSetReader.readString(rs, "PASSWORD"), state);
        set$name(pt.ist.fenixframework.backend.jvstmojb.repository.ResultSetReader.readString(rs, "NAME"), state);
        set$umask(pt.ist.fenixframework.backend.jvstmojb.repository.ResultSetReader.readString(rs, "UMASK"), state);
        castedState.file = pt.ist.fenixframework.backend.jvstmojb.repository.ResultSetReader.readDomainObject(rs, "OID_FILE");
        castedState.homeDirectory = pt.ist.fenixframework.backend.jvstmojb.repository.ResultSetReader.readDomainObject(rs, "OID_HOME_DIRECTORY");
        castedState.myDriveManager = pt.ist.fenixframework.backend.jvstmojb.repository.ResultSetReader.readDomainObject(rs, "OID_MY_DRIVE_MANAGER");
        castedState.filesystem = pt.ist.fenixframework.backend.jvstmojb.repository.ResultSetReader.readDomainObject(rs, "OID_FILESYSTEM");
    }
    protected pt.ist.fenixframework.dml.runtime.Relation get$$relationFor(String attrName) {
        return super.get$$relationFor(attrName);
        
    }
    protected pt.ist.fenixframework.backend.jvstmojb.pstm.OneBoxDomainObject.DO_State  make$newState() {
        return new DO_State();
        
    }
    protected void create$allLists() {
        super.create$allLists();
        
    }
    protected static class DO_State extends pt.ist.fenixframework.backend.jvstmojb.pstm.OneBoxDomainObject.DO_State {
        private java.lang.String username;
        private java.lang.String password;
        private java.lang.String name;
        private java.lang.String umask;
        private pt.tecnico.myDrive.domain.File file;
        private pt.tecnico.myDrive.domain.Directory homeDirectory;
        private pt.tecnico.myDrive.domain.MyDriveManager myDriveManager;
        private pt.tecnico.myDrive.domain.FileSystem filesystem;
        protected void copyTo(pt.ist.fenixframework.backend.jvstmojb.pstm.OneBoxDomainObject.DO_State  newState) {
            super.copyTo(newState);
            DO_State newCasted = (DO_State)newState;
            newCasted.username = this.username;
            newCasted.password = this.password;
            newCasted.name = this.name;
            newCasted.umask = this.umask;
            newCasted.file = this.file;
            newCasted.homeDirectory = this.homeDirectory;
            newCasted.myDriveManager = this.myDriveManager;
            newCasted.filesystem = this.filesystem;
            
        }
        
    }
    
}
