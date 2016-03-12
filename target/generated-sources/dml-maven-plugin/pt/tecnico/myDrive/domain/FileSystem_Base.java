package pt.tecnico.myDrive.domain;

import pt.ist.fenixframework.backend.jvstmojb.pstm.RelationList;
import pt.ist.fenixframework.backend.jvstmojb.ojb.OJBFunctionalSetWrapper;
import pt.ist.fenixframework.ValueTypeSerializer;


@SuppressWarnings("all")
public abstract class FileSystem_Base extends pt.ist.fenixframework.backend.jvstmojb.pstm.OneBoxDomainObject {
    // Static Slots
    public final static pt.ist.fenixframework.dml.runtime.RoleMany<pt.tecnico.myDrive.domain.FileSystem,pt.tecnico.myDrive.domain.User> role$$users = new pt.ist.fenixframework.dml.runtime.RoleMany<pt.tecnico.myDrive.domain.FileSystem,pt.tecnico.myDrive.domain.User>() {
        public pt.ist.fenixframework.dml.runtime.RelationBaseSet<pt.tecnico.myDrive.domain.User> getSet(pt.tecnico.myDrive.domain.FileSystem o1) {
            return ((FileSystem_Base)o1).get$rl$users();
        }
        public pt.ist.fenixframework.dml.runtime.Role<pt.tecnico.myDrive.domain.User,pt.tecnico.myDrive.domain.FileSystem> getInverseRole() {
            return pt.tecnico.myDrive.domain.User.role$$filesystem;
        }
        
    };
    public final static pt.ist.fenixframework.dml.runtime.RoleOne<pt.tecnico.myDrive.domain.FileSystem,pt.tecnico.myDrive.domain.Directory> role$$slash = new pt.ist.fenixframework.dml.runtime.RoleOne<pt.tecnico.myDrive.domain.FileSystem,pt.tecnico.myDrive.domain.Directory>() {
        @Override
        public pt.tecnico.myDrive.domain.Directory getValue(pt.tecnico.myDrive.domain.FileSystem o1) {
            return ((FileSystem_Base.DO_State)o1.get$obj$state(false)).slash;
        }
        @Override
        public void setValue(pt.tecnico.myDrive.domain.FileSystem o1, pt.tecnico.myDrive.domain.Directory o2) {
            ((FileSystem_Base.DO_State)o1.get$obj$state(true)).slash = o2;
        }
        public pt.ist.fenixframework.dml.runtime.Role<pt.tecnico.myDrive.domain.Directory,pt.tecnico.myDrive.domain.FileSystem> getInverseRole() {
            return pt.tecnico.myDrive.domain.Directory.role$$filesystem;
        }
        
    };
    public final static pt.ist.fenixframework.dml.runtime.RoleOne<pt.tecnico.myDrive.domain.FileSystem,pt.tecnico.myDrive.domain.MyDriveManager> role$$myDriveManager = new pt.ist.fenixframework.dml.runtime.RoleOne<pt.tecnico.myDrive.domain.FileSystem,pt.tecnico.myDrive.domain.MyDriveManager>() {
        @Override
        public pt.tecnico.myDrive.domain.MyDriveManager getValue(pt.tecnico.myDrive.domain.FileSystem o1) {
            return ((FileSystem_Base.DO_State)o1.get$obj$state(false)).myDriveManager;
        }
        @Override
        public void setValue(pt.tecnico.myDrive.domain.FileSystem o1, pt.tecnico.myDrive.domain.MyDriveManager o2) {
            ((FileSystem_Base.DO_State)o1.get$obj$state(true)).myDriveManager = o2;
        }
        public pt.ist.fenixframework.dml.runtime.Role<pt.tecnico.myDrive.domain.MyDriveManager,pt.tecnico.myDrive.domain.FileSystem> getInverseRole() {
            return pt.tecnico.myDrive.domain.MyDriveManager.role$$filesystem;
        }
        
    };
    
    public static pt.ist.fenixframework.dml.runtime.DirectRelation<pt.tecnico.myDrive.domain.User,pt.tecnico.myDrive.domain.FileSystem> getRelationFileSystemHasUsers() {
        return pt.tecnico.myDrive.domain.User.getRelationFileSystemHasUsers();
    }
    private static pt.ist.fenixframework.dml.runtime.KeyFunction<Comparable<?>,pt.tecnico.myDrive.domain.User> keyFunction$$users = new pt.ist.fenixframework.dml.runtime.KeyFunction<Comparable<?>,pt.tecnico.myDrive.domain.User>() { public Comparable<?> getKey(pt.tecnico.myDrive.domain.User value) { return value.getOid(); } public boolean allowMultipleKeys() {return false; }};
    
    public static pt.ist.fenixframework.dml.runtime.DirectRelation<pt.tecnico.myDrive.domain.Directory,pt.tecnico.myDrive.domain.FileSystem> getRelationFileSystemHasFiles() {
        return pt.tecnico.myDrive.domain.Directory.getRelationFileSystemHasFiles();
    }
    
    public static pt.ist.fenixframework.dml.runtime.DirectRelation<pt.tecnico.myDrive.domain.MyDriveManager,pt.tecnico.myDrive.domain.FileSystem> getRelationMyDriveManagerHasFileSystem() {
        return pt.tecnico.myDrive.domain.MyDriveManager.getRelationMyDriveManagerHasFileSystem();
    }
    
    // Slots
    
    // Role Slots
    private RelationList<pt.tecnico.myDrive.domain.FileSystem,pt.tecnico.myDrive.domain.User> get$rl$users() {
        return get$$relationList("users", getRelationFileSystemHasUsers().getInverseRelation());
        
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
    protected  FileSystem_Base() {
        super();
    }
    
    // Getters and Setters
    
    public java.lang.Integer getIdSeed() {
        return ((DO_State)this.get$obj$state(false)).idSeed;
    }
    
    public void setIdSeed(java.lang.Integer idSeed) {
        ((DO_State)this.get$obj$state(true)).idSeed = idSeed;
    }
    
    private java.lang.Integer get$idSeed() {
        java.lang.Integer value = ((DO_State)this.get$obj$state(false)).idSeed;
        return (value == null) ? null : pt.ist.fenixframework.backend.jvstmojb.repository.ToSqlConverter.getValueForInteger(value);
    }
    
    private final void set$idSeed(java.lang.Integer value, pt.ist.fenixframework.backend.jvstmojb.pstm.OneBoxDomainObject.DO_State  obj$state) {
        ((DO_State)obj$state).idSeed = (java.lang.Integer)((value == null) ? null : value);
    }
    
    // Role Methods
    
    public void addUsers(pt.tecnico.myDrive.domain.User users) {
        getRelationFileSystemHasUsers().add(users, (pt.tecnico.myDrive.domain.FileSystem)this);
    }
    
    public void removeUsers(pt.tecnico.myDrive.domain.User users) {
        getRelationFileSystemHasUsers().remove(users, (pt.tecnico.myDrive.domain.FileSystem)this);
    }
    
    public java.util.Set<pt.tecnico.myDrive.domain.User> getUsersSet() {
        return get$rl$users();
    }
    
    public void set$users(OJBFunctionalSetWrapper users) {
        get$rl$users().setFromOJB(this, "users", users);
    }
    
    @Deprecated
    public java.util.Set<pt.tecnico.myDrive.domain.User> getUsers() {
        return getUsersSet();
    }
    
    @Deprecated
    public int getUsersCount() {
        return getUsersSet().size();
    }
    
    @pt.ist.fenixframework.consistencyPredicates.ConsistencyPredicate
    public final boolean checkMultiplicityOfUsers() {
        if (get$rl$users().size() < 1) return false;
        return true;
    }
    
    public pt.tecnico.myDrive.domain.Directory getSlash() {
        return ((DO_State)this.get$obj$state(false)).slash;
    }
    
    public void setSlash(pt.tecnico.myDrive.domain.Directory slash) {
        getRelationFileSystemHasFiles().add(slash, (pt.tecnico.myDrive.domain.FileSystem)this);
    }
    
    private java.lang.Long get$oidSlash() {
        pt.ist.fenixframework.backend.jvstmojb.pstm.AbstractDomainObject value = (pt.ist.fenixframework.backend.jvstmojb.pstm.AbstractDomainObject) ((DO_State)this.get$obj$state(false)).slash;
        return (value == null) ? null : value.getOid();
    }
    
    @pt.ist.fenixframework.consistencyPredicates.ConsistencyPredicate
    public final boolean checkMultiplicityOfSlash() {
        if (getSlash() == null) return false;
        return true;
    }
    
    public pt.tecnico.myDrive.domain.MyDriveManager getMyDriveManager() {
        return ((DO_State)this.get$obj$state(false)).myDriveManager;
    }
    
    public void setMyDriveManager(pt.tecnico.myDrive.domain.MyDriveManager myDriveManager) {
        getRelationMyDriveManagerHasFileSystem().add(myDriveManager, (pt.tecnico.myDrive.domain.FileSystem)this);
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
    
    
    protected void checkDisconnected() {
        DO_State castedState = (DO_State)this.get$obj$state(false);
        if (get$rl$users().size() > 0) handleAttemptToDeleteConnectedObject("Users");
        if (castedState.slash != null) handleAttemptToDeleteConnectedObject("Slash");
        if (castedState.myDriveManager != null) handleAttemptToDeleteConnectedObject("MyDriveManager");
        
    }
    
    protected void readStateFromResultSet(java.sql.ResultSet rs, pt.ist.fenixframework.backend.jvstmojb.pstm.OneBoxDomainObject.DO_State  state) throws java.sql.SQLException {
        DO_State castedState = (DO_State)state;
        set$idSeed(pt.ist.fenixframework.backend.jvstmojb.repository.ResultSetReader.readInteger(rs, "ID_SEED"), state);
        castedState.slash = pt.ist.fenixframework.backend.jvstmojb.repository.ResultSetReader.readDomainObject(rs, "OID_SLASH");
        castedState.myDriveManager = pt.ist.fenixframework.backend.jvstmojb.repository.ResultSetReader.readDomainObject(rs, "OID_MY_DRIVE_MANAGER");
    }
    protected pt.ist.fenixframework.dml.runtime.Relation get$$relationFor(String attrName) {
        if (attrName.equals("users")) return getRelationFileSystemHasUsers().getInverseRelation();
        return super.get$$relationFor(attrName);
        
    }
    protected pt.ist.fenixframework.backend.jvstmojb.pstm.OneBoxDomainObject.DO_State  make$newState() {
        return new DO_State();
        
    }
    protected void create$allLists() {
        super.create$allLists();
        get$$relationList("users", getRelationFileSystemHasUsers().getInverseRelation());
        
    }
    protected static class DO_State extends pt.ist.fenixframework.backend.jvstmojb.pstm.OneBoxDomainObject.DO_State {
        private java.lang.Integer idSeed;
        private pt.tecnico.myDrive.domain.Directory slash;
        private pt.tecnico.myDrive.domain.MyDriveManager myDriveManager;
        protected void copyTo(pt.ist.fenixframework.backend.jvstmojb.pstm.OneBoxDomainObject.DO_State  newState) {
            super.copyTo(newState);
            DO_State newCasted = (DO_State)newState;
            newCasted.idSeed = this.idSeed;
            newCasted.slash = this.slash;
            newCasted.myDriveManager = this.myDriveManager;
            
        }
        
    }
    
}
