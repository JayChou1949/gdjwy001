package com.upd.hwcloud.bean.dto;

public class GeneralDTO {

    private String name;

    private String shortName;

    private Integer value;

    private String id;

    private String description;

    private String general;

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getGeneral() {
        return general;
    }

    public void setGeneral(String general) {
        this.general = general;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }


    @Override
    public String toString() {
        return "GeneralDTO{" +
                "name='" + name + '\'' +
                ", shortName='" + shortName + '\'' +
                ", value=" + value +
                ", id='" + id + '\'' +
                ", description='" + description + '\'' +
                ", general='" + general + '\'' +
                '}';
    }
}
