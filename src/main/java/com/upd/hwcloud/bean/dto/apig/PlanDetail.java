package com.upd.hwcloud.bean.dto.apig;

/**
 * 套餐详情
 */
public class PlanDetail {


    /**
     * Id : 108
     * Guid : 5b33a57449e147b1a48979faa1e55819
     * Name : 01ad30370ca343abae6ae5dafa5ab836_default
     * ServiceGuid : 896f596e-1688-05a2-34a2-f1e71358432f
     * IsCustom : false
     * MultiAppLabel :
     * Description :
     * Metadata : {}
     * BlueprintId : 01ad30370ca343abae6ae5dafa5ab836
     * MeteringItems : []
     * Created :
     * Updated : 2018-12-24T19:54:58Z
     */

    private int Id;
    private String Guid;
    private String Name;
    private String ServiceGuid;
    private boolean IsCustom;
    private String MultiAppLabel;
    private String Description;
    private String Metadata;
    private String BlueprintId;
    private String MeteringItems;
    private String Created;
    private String Updated;

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getGuid() {
        return Guid;
    }

    public void setGuid(String Guid) {
        this.Guid = Guid;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getServiceGuid() {
        return ServiceGuid;
    }

    public void setServiceGuid(String ServiceGuid) {
        this.ServiceGuid = ServiceGuid;
    }

    public boolean isIsCustom() {
        return IsCustom;
    }

    public void setIsCustom(boolean IsCustom) {
        this.IsCustom = IsCustom;
    }

    public String getMultiAppLabel() {
        return MultiAppLabel;
    }

    public void setMultiAppLabel(String MultiAppLabel) {
        this.MultiAppLabel = MultiAppLabel;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public String getMetadata() {
        return Metadata;
    }

    public void setMetadata(String Metadata) {
        this.Metadata = Metadata;
    }

    public String getBlueprintId() {
        return BlueprintId;
    }

    public void setBlueprintId(String BlueprintId) {
        this.BlueprintId = BlueprintId;
    }

    public String getMeteringItems() {
        return MeteringItems;
    }

    public void setMeteringItems(String MeteringItems) {
        this.MeteringItems = MeteringItems;
    }

    public String getCreated() {
        return Created;
    }

    public void setCreated(String Created) {
        this.Created = Created;
    }

    public String getUpdated() {
        return Updated;
    }

    public void setUpdated(String Updated) {
        this.Updated = Updated;
    }

}
