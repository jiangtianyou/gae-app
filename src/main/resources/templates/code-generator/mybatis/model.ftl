import java.io.Serializable;
import java.util.Date;
import java.util.List;
import com.wdit.common.persistence.DataEntity;


public class ${classInfo.className} extends DataEntity {

<#if classInfo.fieldList?exists && classInfo.fieldList?size gt 0>
    <#list classInfo.fieldList as fieldItem >
        <#if fieldItem.columnName != "id" && fieldItem.columnName != "createDate">
        private ${fieldItem.fieldClass} ${fieldItem.fieldName}; //${fieldItem.fieldComment}
        </#if>
    </#list>
</#if>
}