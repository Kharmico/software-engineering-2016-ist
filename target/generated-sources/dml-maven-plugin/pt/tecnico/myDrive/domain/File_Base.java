package pt.tecnico.myDrive.domain;

import pt.ist.fenixframework.backend.jvstmojb.pstm.RelationList;
import pt.ist.fenixframework.backend.jvstmojb.ojb.OJBFunctionalSetWrapper;
import pt.ist.fenixframework.ValueTypeSerializer;


@SuppressWarnings("all")
public abstract class File_Base extends pt.ist.fenixframework.backend.jvstmojb.pstm.OneBoxDomainObject {
    // Static Slots
    public final static pt.ist.fenixframework.dml.runtime.RoleOne<pt.tecnico.myDrive.domain.File,pt.tecnico.myDrive.domain.Directory> role$$parentDirectory = new pt.ist.fenixframework.dml.runtime.RoleOne<pt.tecnico.myDrive.domain.File,pt.tecnico.myDrive.domain.Directory>() {
        @Override
        public pt.tecnico.myDrive.domain.Directory getValue(pt.tecnico.myDrive.domain.File o1) {
            return ((File_Base.DO_State)o1.get$obj$state(false)).parentDirectory;
        }
        @Override
        public void setValue(pt.tecnico.myDrive.domain.File o1, pt.tecnico.myDrive.domain.Directory o2) {
            ((File_Base.DO_State)o1.get$obj$state(true)).parentDirectory = o2;
        }
        public pt.ist.fenixframework.dml.runtime.Role<pt.tecnico.myDrive.domain.Directory,pt.tecnico.myDrive.domain.File> getInverseRole() {
            return pt.tecnico.myDrive.domain.Directory.role$$files;
        }
        
    };
    public final static pt.ist.fenixframework.dml.runtime.RoleOne<pt.tecnico.myDrive.domain.File,pt.tecnico.myDrive.domain.User> role$$owner = new pt.ist.fenixframework.dml.runtime.RoleOne<pt.tecnico.myDrive.domain.File,pt.tecnico.myDrive.domain.User>() {
        @Override
        public pt.tecnico.myDrive.domain.User getValue(pt.tecnico.myDrive.domain.File o1) {
            return ((File_Base.DO_State)o1.get$obj$state(false)).owner;
        }
        @Override
        public void setValue(pt.tecnico.myDrive.domain.File o1, pt.tecnico.myDrive.domain.User o2) {
            ((File_Base.DO_State)o1.get$obj$state(true)).owner = o2;
        }
        public pt.ist.fenixframework.dml.runtime.Role<pt.tecnico.myDrive.domain.User,pt.tecnico.myDrive.domain.File> getInverseRole() {
            return pt.tecnico.myDrive.domain.User.role$$file;
        }
        
    };
    
    public static pt.ist.fenixframework.dml.runtime.DirectRelation<pt.tecnico.myDrive.domain.Directory,pt.tecnico.myDrive.domain.File> getRelationDirectoryHasFiles() {
        return pt.tecnico.myDrive.domain.Directory.getRelationDirectoryHasFiles();
    }
    
    public static pt.ist.fenixframework.dml.runtime.DirectRelation<pt.tecnico.myDrive.domain.User,pt.tecnico.myDrive.domain.File> getRelationFileHasOwner() {
        return pt.tecnico.myDrive.domain.User.getRelationFileHasOwner();
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
    protected  File_Base() {
        super();
    }
    
    // Getters and Setters
    
    public java.lang.Integer getId() {
        return ((DO_State)this.get$obj$state(false)).id;
    }
    
    public void setId(java.lang.Integer id) {
        ((DO_State)this.get$obj$state(true)).id = id;
    }
    
    private java.lang.Integer get$id() {
        java.lang.Integer value = ((DO_State)this.get$obj$state(false)).id;
        return (value == null) ? null : pt.ist.fenixframework.backend.jvstmojb.repository.ToSqlConverter.getValueForInteger(value);
    }
    
    private final void set$id(java.lang.Integer value, pt.ist.fenixframework.backend.jvstmojb.pstm.OneBoxDomainObject.DO_State  obj$state) {
        ((DO_State)obj$state).id = (java.lang.Integer)((value == null) ? null : value);
    }
    
    public java.lang.String getFilename() {
        return ((DO_State)this.get$obj$state(false)).filename;
    }
    
    public void setFilename(java.lang.String filename) {
        ((DO_State)this.get$obj$state(true)).filename = filename;
    }
    
    private java.lang.String get$filename() {
        java.lang.String value = ((DO_State)this.get$obj$state(false)).filename;
        return (value == null) ? null : pt.ist.fenixframework.backend.jvstmojb.repository.ToSqlConverter.getValueForString(value);
    }
    
    private final void set$filename(java.lang.String value, pt.ist.fenixframework.backend.jvstmojb.pstm.OneBoxDomainObject.DO_State  obj$state) {
        ((DO_State)obj$state).filename = (java.lang.String)((value == null) ? null : value);
    }
    
    public org.joda.time.DateTime getLastModified() {
        return ((DO_State)this.get$obj$state(false)).lastModified;
    }
    
    public void setLastModified(org.joda.time.DateTime lastModified) {
        ((DO_State)this.get$obj$state(true)).lastModified = lastModified;
    }
    
    private java.sql.Timestamp get$lastModified() {
        org.joda.time.DateTime value = ((DO_State)this.get$obj$state(false)).lastModified;
        return (value == null) ? null : pt.ist.fenixframework.backend.jvstmojb.repository.ToSqlConverter.getValueForDateTime(value);
    }
    
    private final void set$lastModified(org.joda.time.DateTime value, pt.ist.fenixframework.backend.jvstmojb.pstm.OneBoxDomainObject.DO_State  obj$state) {
        ((DO_State)obj$state).lastModified = (org.joda.time.DateTime)((value == null) ? null : value);
    }
    
    public java.lang.String getOwnerPermissions() {
        return ((DO_State)this.get$obj$state(false)).ownerPermissions;
    }
    
    public void setOwnerPermissions(java.lang.String ownerPermissions) {
        ((DO_State)this.get$obj$state(true)).ownerPermissions = ownerPermissions;
    }
    
    private java.lang.String get$ownerPermissions() {
        java.lang.String value = ((DO_State)this.get$obj$state(false)).ownerPermissions;
        return (value == null) ? null : pt.ist.fenixframework.backend.jvstmojb.repository.ToSqlConverter.getValueForString(value);
    }
    
    private final void set$ownerPermissions(java.lang.String value, pt.ist.fenixframework.backend.jvstmojb.pstm.OneBoxDomainObject.DO_State  obj$state) {
        ((DO_State)obj$state).ownerPermissions = (java.lang.String)((value == null) ? null : value);
    }
    
    public java.lang.String getOthersPermissions() {
        return ((DO_State)this.get$obj$state(false)).othersPermissions;
    }
    
    public void setOthersPermissions(java.lang.String othersPermissions) {
        ((DO_State)this.get$obj$state(true)).othersPermissions = othersPermissions;
    }
    
    private java.lang.String get$othersPermissions() {
        java.lang.String value = ((DO_State)this.get$obj$state(false)).othersPermissions;
        return (value == null) ? null : pt.ist.fenixframework.backend.jvstmojb.repository.ToSqlConverter.getValueForString(value);
    }
    
    private final void set$othersPermissions(java.lang.String value, pt.ist.fenixframework.backend.jvstmojb.pstm.OneBoxDomainObject.DO_State  obj$state) {
        ((DO_State)obj$state).othersPermissions = (java.lang.String)((value == null) ? null : value);
    }
    
    // Role Methods
    
    public pt.tecnico.myDrive.domain.Directory getParentDirectory() {
        return ((DO_State)this.get$obj$state(false)).parentDirectory;
    }
    
    public void setParentDirectory(pt.tecnico.myDrive.domain.Directory parentDirectory) {
        getRelationDirectoryHasFiles().add(parentDirectory, (pt.tecnico.myDrive.domain.File)this);
    }
    
    private java.lang.Long get$oidParentDirectory() {
        pt.ist.fenixframework.backend.jvstmojb.pstm.AbstractDomainObject value = (pt.ist.fenixframework.backend.jvstmojb.pstm.AbstractDomainObject) ((DO_State)this.get$obj$state(false)).parentDirectory;
        return (value == null) ? null : value.getOid();
    }
    
    @pt.ist.fenixframework.consistencyPredicates.ConsistencyPredicate
    public final boolean checkMultiplicityOfParentDirectory() {
        if (getParentDirectory() == null) return false;
        return true;
    }
    
    public pt.tecnico.myDrive.domain.User getOwner() {
        return ((DO_State)this.get$obj$state(false)).owner;
    }
    
    public void setOwner(pt.tecnico.myDrive.domain.User owner) {
        getRelationFileHasOwner().add(owner, (pt.tecnico.myDrive.domain.File)this);
    }
    
    private java.lang.Long get$oidOwner() {
        pt.ist.fenixframework.backend.jvstmojb.pstm.AbstractDomainObject value = (pt.ist.fenixframework.backend.jvstmojb.pstm.AbstractDomainObject) ((DO_State)this.get$obj$state(false)).owner;
        return (value == null) ? null : value.getOid();
    }
    
    @pt.ist.fenixframework.consistencyPredicates.ConsistencyPredicate
    public final boolean checkMultiplicityOfOwner() {
        if (getOwner() == null) return false;
        return true;
    }
    
    
    protected void checkDisconnected() {
        DO_State castedState = (DO_State)this.get$obj$state(false);
        if (castedState.parentDirectory != null) handleAttemptToDeleteConnectedObject("ParentDirectory");
        if (castedState.owner != null) handleAttemptToDeleteConnectedObject("Owner");
        
    }
    
    protected void readStateFromResultSet(java.sql.ResultSet rs, pt.ist.fenixframework.backend.jvstmojb.pstm.OneBoxDomainObject.DO_State  state) throws java.sql.SQLException {
        DO_State castedState = (DO_State)state;
        set$id(pt.ist.fenixframework.backend.jvstmojb.repository.ResultSetReader.readInteger(rs, "ID"), state);
        set$filename(pt.ist.fenixframework.backend.jvstmojb.repository.ResultSetReader.readString(rs, "FILENAME"), state);
        set$lastModified(pt.ist.fenixframework.backend.jvstmojb.repository.ResultSetReader.readDateTime(rs, "LAST_MODIFIED"), state);
        set$ownerPermissions(pt.ist.fenixframework.backend.jvstmojb.repository.ResultSetReader.readString(rs, "OWNER_PERMISSIONS"), state);
        set$othersPermissions(pt.ist.fenixframework.backend.jvstmojb.repository.ResultSetReader.readString(rs, "OTHERS_PERMISSIONS"), state);
        castedState.parentDirectory = pt.ist.fenixframework.backend.jvstmojb.repository.ResultSetReader.readDomainObject(rs, "OID_PARENT_DIRECTORY");
        castedState.owner = pt.ist.fenixframework.backend.jvstmojb.repository.ResultSetReader.readDomainObject(rs, "OID_OWNER");
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
        private java.lang.Integer id;
        private java.lang.String filename;
        private org.joda.time.DateTime lastModified;
        private java.lang.String ownerPermissions;
        private java.lang.String othersPermissions;
        private pt.tecnico.myDrive.domain.Directory parentDirectory;
        private pt.tecnico.myDrive.domain.User owner;
        protected void copyTo(pt.ist.fenixframework.backend.jvstmojb.pstm.OneBoxDomainObject.DO_State  newState) {
            super.copyTo(newState);
            DO_State newCasted = (DO_State)newState;
            newCasted.id = this.id;
            newCasted.filename = this.filename;
            newCasted.lastModified = this.lastModified;
            newCasted.ownerPermissions = this.ownerPermissions;
            newCasted.othersPermissions = this.othersPermissions;
            newCasted.parentDirectory = this.parentDirectory;
            newCasted.owner = this.owner;
            
        }
        
    }
    
}
