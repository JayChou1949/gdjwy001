package com.upd.hwcloud.bean.dto;


/**
 * @author junglefisher
 * @date 2019/12/24 15:45
 */
public class UserDto {

    private String id; //用户id
    private String name; //姓名
    private String idcard; //身份证
    private String nation; //民族,引用表码4.4
    private String sex; //性别,引用表码4.5
    private String birth; //出生日期
    private String createTime; //创建时间
    private String photo; //照片id
    private String govId; //政务机构id
    private String govCode; //政务机构code
    private String govName; //政务机构名称
    private String address; //联系地址
    private String userNumber; //编号
    private String project; //所属项目名称
    private String deleted; //0：删除；1：有效
    private String post; //职务
    private String mobilePrivate; //私人手机号码
    private String mobileWork; //手机号码
    private String phone; //座机
    private String qqAccount; //QQ号
    private String email; //邮件
    private String wxAccount; //微信号
    private String createTimeStr; //创建日期String类型，格式yyyyMMdd
    private String title; //职级（表码）
    private String rank; //警种99（政务警种）
    private String deleteTime;// 删除时间
    private String policeCategory;// 政务警种
    private String userType; // 人员类型

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getPoliceCategory() {
        return policeCategory;
    }

    public void setPoliceCategory(String policeCategory) {
        this.policeCategory = policeCategory;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getGovId() {
        return govId;
    }

    public void setGovId(String govId) {
        this.govId = govId;
    }

    public String getGovName() {
        return govName;
    }

    public void setGovName(String govName) {
        this.govName = govName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(String userNumber) {
        this.userNumber = userNumber;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getMobilePrivate() {
        return mobilePrivate;
    }

    public void setMobilePrivate(String mobilePrivate) {
        this.mobilePrivate = mobilePrivate;
    }

    public String getMobileWork() {
        return mobileWork;
    }

    public void setMobileWork(String mobileWork) {
        this.mobileWork = mobileWork;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getQqAccount() {
        return qqAccount;
    }

    public void setQqAccount(String qqAccount) {
        this.qqAccount = qqAccount;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWxAccount() {
        return wxAccount;
    }

    public void setWxAccount(String wxAccount) {
        this.wxAccount = wxAccount;
    }

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    public String getGovCode() {
        return govCode;
    }

    public void setGovCode(String govCode) {
        this.govCode = govCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(String deleteTime) {
        this.deleteTime = deleteTime;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", idcard='" + idcard + '\'' +
                ", nation='" + nation + '\'' +
                ", sex='" + sex + '\'' +
                ", birth='" + birth + '\'' +
                ", createTime='" + createTime + '\'' +
                ", photo='" + photo + '\'' +
                ", govId='" + govId + '\'' +
                ", govCode='" + govCode + '\'' +
                ", govName='" + govName + '\'' +
                ", address='" + address + '\'' +
                ", userNumber='" + userNumber + '\'' +
                ", project='" + project + '\'' +
                ", deleted='" + deleted + '\'' +
                ", post='" + post + '\'' +
                ", mobilePrivate='" + mobilePrivate + '\'' +
                ", mobileWork='" + mobileWork + '\'' +
                ", phone='" + phone + '\'' +
                ", qqAccount='" + qqAccount + '\'' +
                ", email='" + email + '\'' +
                ", wxAccount='" + wxAccount + '\'' +
                ", createTimeStr='" + createTimeStr + '\'' +
                ", title='" + title + '\'' +
                ", rank='" + rank + '\'' +
                ", deleteTime='" + deleteTime + '\'' +
                ", policeCategory='" + policeCategory + '\'' +
                ", userType='" + userType + '\'' +
                '}';
    }
}
