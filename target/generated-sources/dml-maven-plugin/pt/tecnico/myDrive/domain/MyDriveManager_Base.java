package pt.tecnico.myDrive.domain;

import pt.ist.fenixframework.backend.jvstmojb.pstm.RelationList;
import pt.ist.fenixframework.backend.jvstmojb.ojb.OJBFunctionalSetWrapper;
import pt.ist.fenixframework.ValueTypeSerializer;


@SuppressWarnings("all")
public abstract class MyDriveManager_Base extends pt.ist.fenixframework.backend.jvstmojb.pstm.OneBoxDomainObject {
    // Static Slots
    public final static pt.ist.fenixframework.dml.runtime.RoleOne<pt.tecnico.myDrive.domain.MyDriveManager,pt.tecnico.myDrive.domain.Directory> role$$currentDirectory = new pt.ist.fenixframework.dml.runtime.RoleOne<pt.tecnico.myDrive.domain.MyDriveManager,pt.tecnico.myDrive.domain.Directory>() {
        @Override
        public pt.tecnico.myDrive.domain.Directory getValue(pt.tecnico.myDrive.domain.MyDriveManager o1) {
            return ((MyDriveManager_Base.DO_State)o1.get$obj$state(false)).currentDirectory;
        }
        @Override
        public void setValue(pt.tecnico.myDrive.domain.MyDriveManager o1, pt.tecnico.myDrive.domain.Directory o2) {
            ((MyDriveManager_Base.DO_State)o1.get$obj$state(true)).currentDirectory = o2;
        }
        public pt.ist.fenixframework.dml.runtime.Role<pt.tecnico.myDrive.domain.Directory,pt.tecnico.myDrive.domain.MyDriveManager> getInverseRole() {
            return pt.tecnico.myDrive.domain.Directory.role$$myDriveManager;
        }
        
    };
    public final static pt.ist.fenixframework.dml.runtime.RoleOne<pt.tecnico.myDrive.domain.MyDriveManager,pt.tecnico.myDrive.domain.User> role$$currentUser = new pt.ist.fenixframework.dml.runtime.RoleOne<pt.tecnico.myDrive.domain.MyDriveManager,pt.tecnico.myDrive.domain.User>() {
        @Override
        public pt.tecnico.myDrive.domain.User getValue(pt.tecnico.myDrive.domain.MyDriveManager o1) {
            return ((MyDriveManager_Base.DO_State)o1.get$obj$state(false)).currentUser;
        }
        @Override
        public void setValue(pt.tecnico.myDrive.domain.MyDriveManager o1, pt.tecnico.myDrive.domain.User o2) {
            ((MyDriveManager_Base.DO_State)o1.get$obj$state(true)).currentUser = o2;
        }
        public pt.ist.fenixframework.dml.runtime.Role<pt.tecnico.myDrive.domain.User,pt.tecnico.myDrive.domain.MyDriveManager> getInverseRole() {
            return pt.tecnico.myDrive.domain.User.role$$myDriveManager;
        }
        
    };
    public final static pt.ist.fenixframework.dml.runtime.RoleOne<pt.tecnico.myDrive.domain.MyDriveManager,pt.ist.fenixframework.DomainRoot> role$$rootFenix = new pt.ist.fenixframework.dml.runtime.RoleOne<pt.tecnico.myDrive.domain.MyDriveManager,pt.ist.fenixframework.DomainRoot>() {
        @Override
        public pt.ist.fenixframework.DomainRoot getValue(pt.tecnico.myDrive.domain.MyDriveManager o1) {
            return ((MyDriveManager_Base.DO_State)o1.get$obj$state(false)).rootFenix;
        }
        @Override
        public void setValue(pt.tecnico.myDrive.domain.MyDriveManager o1, pt.ist.fenixframework.DomainRoot o2) {
            ((MyDriveManager_Base.DO_State)o1.get$obj$state(true)).rootFenix = o2;
        }
        public pt.ist.fenixframework.dml.runtime.Role<pt.ist.fenixframework.DomainRoot,pt.tecnico.myDrive.domain.MyDriveManager> getInverseRole() {
            return pt.ist.fenixframework.DomainRoot.role$$myDriveManager;
        }
        
    };
    public final static pt.ist.fenixframework.dml.runtime.RoleOne<pt.tecnico.myDrive.domain.MyDriveManager,pt.tecnico.myDrive.domain.FileSystem> role$$filesystem = new pt.ist.fenixframework.dml.runtime.RoleOne<pt.tecnico.myDrive.domain.MyDriveManager,pt.tecnico.myDrive.domain.FileSystem>() {
        @Override
        public pt.tecnico.myDrive.domain.FileSystem getValue(pt.tecnico.myDrive.domain.MyDriveManager o1) {
            return ((MyDriveManager_Base.DO_State)o1.get$obj$state(false)).filesystem;
        }
        @Override
        public void setValue(pt.tecnico.myDrive.domain.MyDriveManager o1, pt.tecnico.myDrive.domain.FileSystem o2) {
            ((MyDriveManager_Base.DO_State)o1.get$obj$state(true)).filesystem = o2;
        }
        public pt.ist.fenixframework.dml.runtime.Role<pt.tecnico.myDrive.domain.FileSystem,pt.tecnico.myDrive.domain.MyDriveManager> getInverseRole() {
            return pt.tecnico.myDrive.domain.FileSystem.role$$myDriveManager;
        }
        
    };
    
    private final static class MyDriveManagerHasCurrentDirectoryy {
        private static final pt.ist.fenixframework.backend.jvstmojb.pstm.LoggingRelation<pt.tecnico.myDrive.domain.MyDriveManager,pt.tecnico.myDrive.domain.Directory> relation = new pt.ist.fenixframework.backend.jvstmojb.pstm.LoggingRelation<pt.tecnico.myDrive.domain.MyDriveManager,pt.tecnico.myDrive.domain.Directory>(role$$currentDirectory, "MyDriveManagerHasCurrentDirectoryy");
    }
    public static pt.ist.fenixframework.dml.runtime.DirectRelation<pt.tecnico.myDrive.domain.MyDriveManager,pt.tecnico.myDrive.domain.Directory> getRelationMyDriveManagerHasCurrentDirectoryy() {
        return MyDriveManagerHasCurrentDirectoryy.relation;
    }
    
    static {
        MyDriveManagerHasCurrentDirectoryy.relation.setRelationName("pt.tecnico.myDrive.domain.MyDriveManager.MyDriveManagerHasCurrentDirectoryy");
    }
    
    private final static class MyDriveManagerHasCurrentUser {
        private static final pt.ist.fenixframework.backend.jvstmojb.pstm.LoggingRelation<pt.tecnico.myDrive.domain.MyDriveManager,pt.tecnico.myDrive.domain.User> relation = new pt.ist.fenixframework.backend.jvstmojb.pstm.LoggingRelation<pt.tecnico.myDrive.domain.MyDriveManager,pt.tecnico.myDrive.domain.User>(role$$currentUser, "MyDriveManagerHasCurrentUser");
    }
    public static pt.ist.fenixframework.dml.runtime.DirectRelation<pt.tecnico.myDrive.domain.MyDriveManager,pt.tecnico.myDrive.domain.User> getRelationMyDriveManagerHasCurrentUser() {
        return MyDriveManagerHasCurrentUser.relation;
    }
    
    static {
        MyDriveManagerHasCurrentUser.relation.setRelationName("pt.tecnico.myDrive.domain.MyDriveManager.MyDriveManagerHasCurrentUser");
    }
    
    public static pt.ist.fenixframework.dml.runtime.DirectRelation<pt.ist.fenixframework.DomainRoot,pt.tecnico.myDrive.domain.MyDriveManager> getRelationDomainRootHasMyDriveManager() {
        return pt.ist.fenixframework.DomainRoot.getRelationDomainRootHasMyDriveManager();
    }
    
    private final static class MyDriveManagerHasFileSystem {
        private static final pt.ist.fenixframework.backend.jvstmojb.pstm.LoggingRelation<pt.tecnico.myDrive.domain.MyDriveManager,pt.tecnico.myDrive.domain.FileSystem> relation = new pt.ist.fenixframework.backend.jvstmojb.pstm.LoggingRelation<pt.tecnico.myDrive.domain.MyDriveManager,pt.tecnico.myDrive.domain.FileSystem>(role$$filesystem, "MyDriveManagerHasFileSystem");
    }
    public static pt.ist.fenixframework.dml.runtime.DirectRelation<pt.tecnico.myDrive.domain.MyDriveManager,pt.tecnico.myDrive.domain.FileSystem> getRelationMyDriveManagerHasFileSystem() {
        return MyDriveManagerHasFileSystem.relation;
    }
    
    static {
        MyDriveManagerHasFileSystem.relation.setRelationName("pt.tecnico.myDrive.domain.MyDriveManager.MyDriveManagerHasFileSystem");
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
    protected  MyDriveManager_Base() {
        super();
    }
    
    // Getters and Setters
    
    // Role Methods
    
    public pt.tecnico.myDrive.domain.Directory getCurrentDirectory() {
        return ((DO_State)this.get$obj$state(false)).currentDirectory;
    }
    
    public void setCurrentDirectory(pt.tecnico.myDrive.domain.Directory currentDirectory) {
        getRelationMyDriveManagerHasCurrentDirectoryy().add((pt.tecnico.myDrive.domain.MyDriveManager)this, currentDirectory);
    }
    
    private java.lang.Long get$oidCurrentDirectory() {
        pt.ist.fenixframework.backend.jvstmojb.pstm.AbstractDomainObject value = (pt.ist.fenixframework.backend.jvstmojb.pstm.AbstractDomainObject) ((DO_State)this.get$obj$state(false)).currentDirectory;
        return (value == null) ? null : value.getOid();
    }
    
    @pt.ist.fenixframework.consistencyPredicates.ConsistencyPredicate
    public final boolean checkMultiplicityOfCurrentDirectory() {
        if (getCurrentDirectory() == null) return false;
        return true;
    }
    
    public pt.tecnico.myDrive.domain.User getCurrentUser() {
        return ((DO_State)this.get$obj$state(false)).currentUser;
    }
    
    public void setCurrentUser(pt.tecnico.myDrive.domain.User currentUser) {
        getRelationMyDriveManagerHasCurrentUser().add((pt.tecnico.myDrive.domain.MyDriveManager)this, currentUser);
    }
    
    private java.lang.Long get$oidCurrentUser() {
        pt.ist.fenixframework.backend.jvstmojb.pstm.AbstractDomainObject value = (pt.ist.fenixframework.backend.jvstmojb.pstm.AbstractDomainObject) ((DO_State)this.get$obj$state(false)).currentUser;
        return (value == null) ? null : value.getOid();
    }
    
    @pt.ist.fenixframework.consistencyPredicates.ConsistencyPredicate
    public final boolean checkMultiplicityOfCurrentUser() {
        if (getCurrentUser() == null) return false;
        return true;
    }
    
    public pt.ist.fenixframework.DomainRoot getRootFenix() {
        return ((DO_State)this.get$obj$state(false)).rootFenix;
    }
    
    public void setRootFenix(pt.ist.fenixframework.DomainRoot rootFenix) {
        getRelationDomainRootHasMyDriveManager().add(rootFenix, (pt.tecnico.myDrive.domain.MyDriveManager)this);
    }
    
    private java.lang.Long get$oidRootFenix() {
        pt.ist.fenixframework.backend.jvstmojb.pstm.AbstractDomainObject value = (pt.ist.fenixframework.backend.jvstmojb.pstm.AbstractDomainObject) ((DO_State)this.get$obj$state(false)).rootFenix;
        return (value == null) ? null : value.getOid();
    }
    
    public pt.tecnico.myDrive.domain.FileSystem getFilesystem() {
        return ((DO_State)this.get$obj$state(false)).filesystem;
    }
    
    public void setFilesystem(pt.tecnico.myDrive.domain.FileSystem filesystem) {
        getRelationMyDriveManagerHasFileSystem().add((pt.tecnico.myDrive.domain.MyDriveManager)this, filesystem);
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
        if (castedState.currentDirectory != null) handleAttemptToDeleteConnectedObject("CurrentDirectory");
        if (castedState.currentUser != null) handleAttemptToDeleteConnectedObject("CurrentUser");
        if (castedState.rootFenix != null) handleAttemptToDeleteConnectedObject("RootFenix");
        if (castedState.filesystem != null) handleAttemptToDeleteConnectedObject("Filesystem");
        
    }
    
    protected void readStateFromResultSet(java.sql.ResultSet rs, pt.ist.fenixframework.backend.jvstmojb.pstm.OneBoxDomainObject.DO_State  state) throws java.sql.SQLException {
        DO_State castedState = (DO_State)state;
        castedState.currentDirectory = pt.ist.fenixframework.backend.jvstmojb.repository.ResultSetReader.readDomainObject(rs, "OID_CURRENT_DIRECTORY");
        castedState.currentUser = pt.ist.fenixframework.backend.jvstmojb.repository.ResultSetReader.readDomainObject(rs, "OID_CURRENT_USER");
        castedState.rootFenix = pt.ist.fenixframework.backend.jvstmojb.repository.ResultSetReader.readDomainObject(rs, "OID_ROOT_FENIX");
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
        private pt.tecnico.myDrive.domain.Directory currentDirectory;
        private pt.tecnico.myDrive.domain.User currentUser;
        private pt.ist.fenixframework.DomainRoot rootFenix;
        private pt.tecnico.myDrive.domain.FileSystem filesystem;
        protected void copyTo(pt.ist.fenixframework.backend.jvstmojb.pstm.OneBoxDomainObject.DO_State  newState) {
            super.copyTo(newState);
            DO_State newCasted = (DO_State)newState;
            newCasted.currentDirectory = this.currentDirectory;
            newCasted.currentUser = this.currentUser;
            newCasted.rootFenix = this.rootFenix;
            newCasted.filesystem = this.filesystem;
            
        }
        
    }
    
}
