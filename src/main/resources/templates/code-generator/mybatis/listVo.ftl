package ${packageName}.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import com.wdit.common.persistence.DataEntity;


@ApiModel(value = "${classInfo.className}ListVo", description = "${classInfo.classComment}列表")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ${classInfo.className}ListVo{

<#list classInfo.fieldList as fieldItem >
<#if fieldItem.columnName != "id" && fieldItem.columnName != "create_date"
&& fieldItem.columnName != "update_date" &&  fieldItem.columnName != "del_flag"
&&  fieldItem.columnName != "remarks">
    @JsonProperty("${fieldItem.fieldName}")
    private ${fieldItem.fieldClass} ${fieldItem.fieldName};
</#if>
</#list>
}