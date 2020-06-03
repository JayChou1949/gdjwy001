package com.upd.hwcloud.bean.dto.apig;

import java.util.List;

/**
 * 服务详情
 */
public class ServiceDetail {

    private int Id;
    private String Guid;
    private String DomainId;
    private String DomainName;
    private String ProjectId;
    private boolean IsPublic;
    private String ResourcePool;
    private boolean SyncMO;
    private String Name;
    private String Alias;
    private boolean Hosted;
    private String mode;
    private int Catalog;
    private String Tags;
    private String Dependency;
    private String Version;
    private String Description;
    private String Status;
    private String PreStatus;
    private String BlueprintId;
    private String ConsoleEntry;
    private String Parameters;
    private boolean BindingAppend;
    private String BindParameters;
    private String Metadata;
    private String MetadataDefine;
    private String LogoUrl;
    private String SoftwarePkgUrl;
    private String BrokerPkgUrl;
    private String broker_address;
    private int broker_port;
    private int broker_node_port;
    private String broker_app_id;
    private String BrokerCustomTag;
    private String broker_auth;
    private String VolumeForBroker;
    private String DashboardPattern;
    private String ConsoleSource;
    private String ConsolePkgUrl;
    private String console_address;
    private int console_port;
    private String console_domain;
    private String console_app_id;
    private boolean ConsoleUseDomain;
    private String DocUrl;
    private String SdkUrl;
    private String SubItem;
    private boolean NeedMetering;
    private String ProvisionSource;
    private String ProvisionPath;
    private String ProvisionPara;
    private boolean EnableUpdate;
    private String UpdatePath;
    private String UpdateSource;
    private String FailCause;
    private String Create_at;
    private String Update_at;
    private boolean SkipApproval;
    private String AllVersions;
    private String ClusterId;
    private String ProjectName;
    private boolean UsePaasResource;
    private String Rejectreason;
    private boolean SkipConsoleHealthCheck;
    private boolean ConsoleUseHttps;
    private boolean BrokerUseHttps;
    private String Vendor;
    private String ApiProductName;
    private String ExtraPara;
    private List<Plans> plans;

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

    public String getDomainId() {
        return DomainId;
    }

    public void setDomainId(String DomainId) {
        this.DomainId = DomainId;
    }

    public String getDomainName() {
        return DomainName;
    }

    public void setDomainName(String DomainName) {
        this.DomainName = DomainName;
    }

    public String getProjectId() {
        return ProjectId;
    }

    public void setProjectId(String ProjectId) {
        this.ProjectId = ProjectId;
    }

    public boolean isIsPublic() {
        return IsPublic;
    }

    public void setIsPublic(boolean IsPublic) {
        this.IsPublic = IsPublic;
    }

    public String getResourcePool() {
        return ResourcePool;
    }

    public void setResourcePool(String ResourcePool) {
        this.ResourcePool = ResourcePool;
    }

    public boolean isSyncMO() {
        return SyncMO;
    }

    public void setSyncMO(boolean SyncMO) {
        this.SyncMO = SyncMO;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getAlias() {
        return Alias;
    }

    public void setAlias(String Alias) {
        this.Alias = Alias;
    }

    public boolean isHosted() {
        return Hosted;
    }

    public void setHosted(boolean Hosted) {
        this.Hosted = Hosted;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public int getCatalog() {
        return Catalog;
    }

    public void setCatalog(int Catalog) {
        this.Catalog = Catalog;
    }

    public String getTags() {
        return Tags;
    }

    public void setTags(String Tags) {
        this.Tags = Tags;
    }

    public String getDependency() {
        return Dependency;
    }

    public void setDependency(String Dependency) {
        this.Dependency = Dependency;
    }

    public String getVersion() {
        return Version;
    }

    public void setVersion(String Version) {
        this.Version = Version;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public String getPreStatus() {
        return PreStatus;
    }

    public void setPreStatus(String PreStatus) {
        this.PreStatus = PreStatus;
    }

    public String getBlueprintId() {
        return BlueprintId;
    }

    public void setBlueprintId(String BlueprintId) {
        this.BlueprintId = BlueprintId;
    }

    public String getConsoleEntry() {
        return ConsoleEntry;
    }

    public void setConsoleEntry(String ConsoleEntry) {
        this.ConsoleEntry = ConsoleEntry;
    }

    public String getParameters() {
        return Parameters;
    }

    public void setParameters(String Parameters) {
        this.Parameters = Parameters;
    }

    public boolean isBindingAppend() {
        return BindingAppend;
    }

    public void setBindingAppend(boolean BindingAppend) {
        this.BindingAppend = BindingAppend;
    }

    public String getBindParameters() {
        return BindParameters;
    }

    public void setBindParameters(String BindParameters) {
        this.BindParameters = BindParameters;
    }

    public String getMetadata() {
        return Metadata;
    }

    public void setMetadata(String Metadata) {
        this.Metadata = Metadata;
    }

    public String getMetadataDefine() {
        return MetadataDefine;
    }

    public void setMetadataDefine(String MetadataDefine) {
        this.MetadataDefine = MetadataDefine;
    }

    public String getLogoUrl() {
        return LogoUrl;
    }

    public void setLogoUrl(String LogoUrl) {
        this.LogoUrl = LogoUrl;
    }

    public String getSoftwarePkgUrl() {
        return SoftwarePkgUrl;
    }

    public void setSoftwarePkgUrl(String SoftwarePkgUrl) {
        this.SoftwarePkgUrl = SoftwarePkgUrl;
    }

    public String getBrokerPkgUrl() {
        return BrokerPkgUrl;
    }

    public void setBrokerPkgUrl(String BrokerPkgUrl) {
        this.BrokerPkgUrl = BrokerPkgUrl;
    }

    public String getBroker_address() {
        return broker_address;
    }

    public void setBroker_address(String broker_address) {
        this.broker_address = broker_address;
    }

    public int getBroker_port() {
        return broker_port;
    }

    public void setBroker_port(int broker_port) {
        this.broker_port = broker_port;
    }

    public int getBroker_node_port() {
        return broker_node_port;
    }

    public void setBroker_node_port(int broker_node_port) {
        this.broker_node_port = broker_node_port;
    }

    public String getBroker_app_id() {
        return broker_app_id;
    }

    public void setBroker_app_id(String broker_app_id) {
        this.broker_app_id = broker_app_id;
    }

    public String getBrokerCustomTag() {
        return BrokerCustomTag;
    }

    public void setBrokerCustomTag(String BrokerCustomTag) {
        this.BrokerCustomTag = BrokerCustomTag;
    }

    public String getBroker_auth() {
        return broker_auth;
    }

    public void setBroker_auth(String broker_auth) {
        this.broker_auth = broker_auth;
    }

    public String getVolumeForBroker() {
        return VolumeForBroker;
    }

    public void setVolumeForBroker(String VolumeForBroker) {
        this.VolumeForBroker = VolumeForBroker;
    }

    public String getDashboardPattern() {
        return DashboardPattern;
    }

    public void setDashboardPattern(String DashboardPattern) {
        this.DashboardPattern = DashboardPattern;
    }

    public String getConsoleSource() {
        return ConsoleSource;
    }

    public void setConsoleSource(String ConsoleSource) {
        this.ConsoleSource = ConsoleSource;
    }

    public String getConsolePkgUrl() {
        return ConsolePkgUrl;
    }

    public void setConsolePkgUrl(String ConsolePkgUrl) {
        this.ConsolePkgUrl = ConsolePkgUrl;
    }

    public String getConsole_address() {
        return console_address;
    }

    public void setConsole_address(String console_address) {
        this.console_address = console_address;
    }

    public int getConsole_port() {
        return console_port;
    }

    public void setConsole_port(int console_port) {
        this.console_port = console_port;
    }

    public String getConsole_domain() {
        return console_domain;
    }

    public void setConsole_domain(String console_domain) {
        this.console_domain = console_domain;
    }

    public String getConsole_app_id() {
        return console_app_id;
    }

    public void setConsole_app_id(String console_app_id) {
        this.console_app_id = console_app_id;
    }

    public boolean isConsoleUseDomain() {
        return ConsoleUseDomain;
    }

    public void setConsoleUseDomain(boolean ConsoleUseDomain) {
        this.ConsoleUseDomain = ConsoleUseDomain;
    }

    public String getDocUrl() {
        return DocUrl;
    }

    public void setDocUrl(String DocUrl) {
        this.DocUrl = DocUrl;
    }

    public String getSdkUrl() {
        return SdkUrl;
    }

    public void setSdkUrl(String SdkUrl) {
        this.SdkUrl = SdkUrl;
    }

    public String getSubItem() {
        return SubItem;
    }

    public void setSubItem(String SubItem) {
        this.SubItem = SubItem;
    }

    public boolean isNeedMetering() {
        return NeedMetering;
    }

    public void setNeedMetering(boolean NeedMetering) {
        this.NeedMetering = NeedMetering;
    }

    public String getProvisionSource() {
        return ProvisionSource;
    }

    public void setProvisionSource(String ProvisionSource) {
        this.ProvisionSource = ProvisionSource;
    }

    public String getProvisionPath() {
        return ProvisionPath;
    }

    public void setProvisionPath(String ProvisionPath) {
        this.ProvisionPath = ProvisionPath;
    }

    public String getProvisionPara() {
        return ProvisionPara;
    }

    public void setProvisionPara(String ProvisionPara) {
        this.ProvisionPara = ProvisionPara;
    }

    public boolean isEnableUpdate() {
        return EnableUpdate;
    }

    public void setEnableUpdate(boolean EnableUpdate) {
        this.EnableUpdate = EnableUpdate;
    }

    public String getUpdatePath() {
        return UpdatePath;
    }

    public void setUpdatePath(String UpdatePath) {
        this.UpdatePath = UpdatePath;
    }

    public String getUpdateSource() {
        return UpdateSource;
    }

    public void setUpdateSource(String UpdateSource) {
        this.UpdateSource = UpdateSource;
    }

    public String getFailCause() {
        return FailCause;
    }

    public void setFailCause(String FailCause) {
        this.FailCause = FailCause;
    }

    public String getCreate_at() {
        return Create_at;
    }

    public void setCreate_at(String Create_at) {
        this.Create_at = Create_at;
    }

    public String getUpdate_at() {
        return Update_at;
    }

    public void setUpdate_at(String Update_at) {
        this.Update_at = Update_at;
    }

    public boolean isSkipApproval() {
        return SkipApproval;
    }

    public void setSkipApproval(boolean SkipApproval) {
        this.SkipApproval = SkipApproval;
    }

    public String getAllVersions() {
        return AllVersions;
    }

    public void setAllVersions(String AllVersions) {
        this.AllVersions = AllVersions;
    }

    public String getClusterId() {
        return ClusterId;
    }

    public void setClusterId(String ClusterId) {
        this.ClusterId = ClusterId;
    }

    public String getProjectName() {
        return ProjectName;
    }

    public void setProjectName(String ProjectName) {
        this.ProjectName = ProjectName;
    }

    public boolean isUsePaasResource() {
        return UsePaasResource;
    }

    public void setUsePaasResource(boolean UsePaasResource) {
        this.UsePaasResource = UsePaasResource;
    }

    public String getRejectreason() {
        return Rejectreason;
    }

    public void setRejectreason(String Rejectreason) {
        this.Rejectreason = Rejectreason;
    }

    public boolean isSkipConsoleHealthCheck() {
        return SkipConsoleHealthCheck;
    }

    public void setSkipConsoleHealthCheck(boolean SkipConsoleHealthCheck) {
        this.SkipConsoleHealthCheck = SkipConsoleHealthCheck;
    }

    public boolean isConsoleUseHttps() {
        return ConsoleUseHttps;
    }

    public void setConsoleUseHttps(boolean ConsoleUseHttps) {
        this.ConsoleUseHttps = ConsoleUseHttps;
    }

    public boolean isBrokerUseHttps() {
        return BrokerUseHttps;
    }

    public void setBrokerUseHttps(boolean BrokerUseHttps) {
        this.BrokerUseHttps = BrokerUseHttps;
    }

    public String getVendor() {
        return Vendor;
    }

    public void setVendor(String Vendor) {
        this.Vendor = Vendor;
    }

    public String getApiProductName() {
        return ApiProductName;
    }

    public void setApiProductName(String ApiProductName) {
        this.ApiProductName = ApiProductName;
    }

    public String getExtraPara() {
        return ExtraPara;
    }

    public void setExtraPara(String ExtraPara) {
        this.ExtraPara = ExtraPara;
    }

    public List<Plans> getPlans() {
        return plans;
    }

    public void setPlans(List<Plans> plans) {
        this.plans = plans;
    }

    public static class Plans {
        /**
         * id : 426
         * guid : 8ce4520dfd804f4daffef8dd57162843
         * name : 247f3d7e7b1b4d4ca0391249662552a6_default
         * service_guid : 49208536-07ef-e8fc-8382-a50cf515931a
         * is_custom : false
         * multi_app_label :
         * description :
         * metadata : {}
         * blueprint_id : 247f3d7e7b1b4d4ca0391249662552a6
         * metering_items : []
         * is_used : 1
         */

        private int id;
        private String guid;
        private String name;
        private String service_guid;
        private boolean is_custom;
        private String multi_app_label;
        private String description;
        private String metadata;
        private String blueprint_id;
        private String metering_items;
        private int is_used;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getGuid() {
            return guid;
        }

        public void setGuid(String guid) {
            this.guid = guid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getService_guid() {
            return service_guid;
        }

        public void setService_guid(String service_guid) {
            this.service_guid = service_guid;
        }

        public boolean isIs_custom() {
            return is_custom;
        }

        public void setIs_custom(boolean is_custom) {
            this.is_custom = is_custom;
        }

        public String getMulti_app_label() {
            return multi_app_label;
        }

        public void setMulti_app_label(String multi_app_label) {
            this.multi_app_label = multi_app_label;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getMetadata() {
            return metadata;
        }

        public void setMetadata(String metadata) {
            this.metadata = metadata;
        }

        public String getBlueprint_id() {
            return blueprint_id;
        }

        public void setBlueprint_id(String blueprint_id) {
            this.blueprint_id = blueprint_id;
        }

        public String getMetering_items() {
            return metering_items;
        }

        public void setMetering_items(String metering_items) {
            this.metering_items = metering_items;
        }

        public int getIs_used() {
            return is_used;
        }

        public void setIs_used(int is_used) {
            this.is_used = is_used;
        }
    }

}
