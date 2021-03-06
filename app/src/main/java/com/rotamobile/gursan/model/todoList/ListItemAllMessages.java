package com.rotamobile.gursan.model.todoList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListItemAllMessages {

    @SerializedName("ID")
    @Expose
    private Integer ID;
    @SerializedName("ProjectName")
    @Expose
    private String ProjectName;
    @SerializedName("SubjectText")
    @Expose
    private String SubjectText;
    @SerializedName("BuildingName")
    @Expose
    private String BuildingName;
    @SerializedName("DeviceName")
    @Expose
    private String DeviceName;
    @SerializedName("StartDate")
    @Expose
    private String StartDate;
    @SerializedName("EndDate")
    @Expose
    private String EndDate;
    @SerializedName("WorkUser")
    @Expose
    private String WorkUser;
    @SerializedName("TerritoryName")
    @Expose
    private String TerritoryName;
    @SerializedName("AreaName")
    @Expose
    private String AreaName;
    @SerializedName("ProjectID")
    @Expose
    private Integer ProjectID;
    @SerializedName("TerritoryID")
    @Expose
    private Integer TerritoryID;
    @SerializedName("BuildingID")
    @Expose
    private Integer BuildingID;
    @SerializedName("AreaID")
    @Expose
    private Integer AreaID;
    @SerializedName("DeviceID")
    @Expose
    private Integer DeviceID;
    @SerializedName("SubjectID")
    @Expose
    private Integer SubjectID;
    @SerializedName("InsertUserID")
    @Expose
    private Integer InsertUserID;
    @SerializedName("AssignedUserID")
    @Expose
    private Integer AssignedUserID;
    @SerializedName("AuthorizationUpdate")
    @Expose
    private Boolean AuthorizationUpdate;
    @SerializedName("Description")
    @Expose
    private String Description;
    @SerializedName("WorkID")
    @Expose
    private Integer WorkID;
    @SerializedName("WorkOrderServiceID")
    @Expose
    private Integer WorkOrderServiceID;
    @SerializedName("WorkOrderCategoryID")
    @Expose
    private Integer WorkOrderCategoryID;
    @SerializedName("WorkOrderTypeID")
    @Expose
    private Integer WorkOrderTypeID;
    @SerializedName("WorkImportanceID")
    @Expose
    private Integer WorkImportanceID;
    @SerializedName("MoveTypeID")
    @Expose
    private Integer MoveTypeID;
    @SerializedName("FirmName")
    @Expose
    private String FirmName;
    @SerializedName("FirmPhone")
    @Expose
    private String FirmPhone;

    public ListItemAllMessages(Integer id, String projectName, String subjectText, String buildingName, String deviceName, String startDate, String endDate, String workUser
            , String territoryName, String areaName, Integer project_id, Integer teritory_id, Integer building_id, Integer area_id, Integer device_id, Integer subject_id
            , Integer insert_user_id, Integer assigned_user_id, Boolean authorizaUpdata, String description_update, Integer workID, Integer WorkOrderServiceID, Integer WorkOrderCategoryID
            , Integer WorkOrderTypeID, Integer WorkImportanceID, Integer MoveTypeID) {

        ProjectName = projectName;
        SubjectText = subjectText;
        BuildingName = buildingName;
        DeviceName = deviceName;
        StartDate = startDate;
        EndDate = endDate;
        WorkUser = workUser;
        TerritoryName = territoryName;
        AreaName = areaName;
        ProjectID = project_id;
        TerritoryID = teritory_id;
        BuildingID = building_id;
        AreaID = area_id;
        DeviceID = device_id;
        SubjectID = subject_id;
        InsertUserID = insert_user_id;
        ID = id;
        AssignedUserID = assigned_user_id;
        AuthorizationUpdate = authorizaUpdata;
        Description = description_update;
        WorkID = workID;
        this.WorkOrderServiceID = WorkOrderServiceID;
        this.WorkOrderCategoryID = WorkOrderCategoryID;
        this.WorkOrderTypeID = WorkOrderTypeID;
        this.WorkImportanceID = WorkImportanceID;
        this.MoveTypeID = MoveTypeID;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getBuildingName() {
        return BuildingName;
    }

    public void setBuildingName(String buildingName) {
        BuildingName = buildingName;
    }

    public String getDeviceName() {
        return DeviceName;
    }

    public void setDeviceName(String deviceName) {
        DeviceName = deviceName;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    public String getWorkUser() {
        return WorkUser;
    }

    public void setWorkUser(String workUser) {
        WorkUser = workUser;
    }

    public String getTerritoryName() {
        return TerritoryName;
    }

    public void setTerritoryName(String territoryName) {
        TerritoryName = territoryName;
    }

    public String getAreaName() {
        return AreaName;
    }

    public void setAreaName(String areaName) {
        AreaName = areaName;
    }

    public String getProjectName() {
        return ProjectName;
    }

    public void setProjectName(String projectName) {
        ProjectName = projectName;
    }

    public String getSubjectText() {
        return SubjectText;
    }

    public void setSubjectText(String subjectText) {
        SubjectText = subjectText;
    }

    public Integer getProjectID() {
        return ProjectID;
    }

    public void setProjectID(Integer projectID) {
        ProjectID = projectID;
    }

    public Integer getTerritoryID() {
        return TerritoryID;
    }

    public void setTerritoryID(Integer territoryID) {
        TerritoryID = territoryID;
    }

    public Integer getBuildingID() {
        return BuildingID;
    }

    public void setBuildingID(Integer buildingID) {
        BuildingID = buildingID;
    }

    public Integer getAreaID() {
        return AreaID;
    }

    public void setAreaID(Integer areaID) {
        AreaID = areaID;
    }

    public Integer getDeviceID() {
        return DeviceID;
    }

    public void setDeviceID(Integer deviceID) {
        DeviceID = deviceID;
    }

    public Integer getSubjectID() {
        return SubjectID;
    }

    public void setSubjectID(Integer subjectID) {
        SubjectID = subjectID;
    }

    public Integer getInsertUserID() {
        return InsertUserID;
    }

    public void setInsertUserID(Integer insertUserID) {
        InsertUserID = insertUserID;
    }

    public Integer getAssignedUserID() {
        return AssignedUserID;
    }

    public void setAssignedUserID(Integer assignedUserID) {
        AssignedUserID = assignedUserID;
    }

    public Boolean getAuthorizationUpdate() {
        return AuthorizationUpdate;
    }

    public void setAuthorizationUpdate(Boolean authorizationUpdate) {
        AuthorizationUpdate = authorizationUpdate;
    }


    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }


    public Integer getWorkID() {
        return WorkID;
    }

    public void setWorkID(Integer workID) {
        WorkID = workID;
    }

    public Integer getWorkOrderServiceID() {
        return WorkOrderServiceID;
    }

    public void setWorkOrderServiceID(Integer workOrderServiceID) {
        WorkOrderServiceID = workOrderServiceID;
    }

    public Integer getWorkOrderCategoryID() {
        return WorkOrderCategoryID;
    }

    public void setWorkOrderCategoryID(Integer workOrderCategoryID) {
        WorkOrderCategoryID = workOrderCategoryID;
    }

    public Integer getWorkOrderTypeID() {
        return WorkOrderTypeID;
    }

    public void setWorkOrderTypeID(Integer workOrderTypeID) {
        WorkOrderTypeID = workOrderTypeID;
    }

    public Integer getWorkImportanceID() {
        return WorkImportanceID;
    }

    public void setWorkImportanceID(Integer workImportanceID) {
        WorkImportanceID = workImportanceID;
    }

    public Integer getMoveTypeID() {
        return MoveTypeID;
    }

    public void setMoveTypeID(Integer moveTypeID) {
        MoveTypeID = moveTypeID;
    }

    public String getFirmName() {
        return FirmName;
    }

    public void setFirmName(String firmName) {
        FirmName = firmName;
    }

    public String getFirmPhone() {
        return FirmPhone;
    }

    public void setFirmPhone(String firmPhone) {
        FirmPhone = firmPhone;
    }

}
