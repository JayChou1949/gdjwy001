package com.upd.hwcloud.bean.dto;

/**
 * @Description:
 * @author: yyc
 * @date: 2018-10-19 11:25
 **/
public class Appconditon {
    private String applyPerson;
    private String status;

    public String getApplyPerson() {
        return applyPerson;
    }

    public void setApplyPerson(String applyPerson) {
        this.applyPerson = applyPerson;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Appconditon{" +
                "applyPerson='" + applyPerson + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
