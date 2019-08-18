import java.io.Serializable;
import java.util.Date;
import java.util.List;
import com.wdit.common.persistence.DataEntity;


public class ${classInfo.className} extends DataEntity {
<#if classInfo.fieldList?exists && classInfo.fieldList?size gt 0>
    <#list classInfo.fieldList as fieldItem >
    <#if fieldItem.columnName != "id" && fieldItem.columnName != "create_date"
    && fieldItem.columnName != "update_date" &&  fieldItem.columnName != "del_flag"
    &&  fieldItem.columnName != "remarks">
    private ${fieldItem.fieldClass} ${fieldItem.fieldName}; //${fieldItem.fieldComment}
    </#if>
    </#list>
</#if>
}