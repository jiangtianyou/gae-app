package ${packageName}.api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

import com.wdit.common.vo.PageVo;
import com.wdit.common.persistence.Page;
import static com.wdit.common.utils.ApiUtils.buildPage;
import ${packageName}.vo.${classInfo.className}Vo;
import ${packageName}.vo.${classInfo.className}ListVo;

<#macro noIdParamList>
    <#list classInfo.fieldList as fieldItem >
        <#if fieldItem.columnName != "id" && fieldItem.columnName != "create_date" &&  fieldItem.columnName != "update_date" &&  fieldItem.columnName != "del_flag" &&  fieldItem.columnName != "remarks">
            @RequestParam(name="${fieldItem.fieldName}") ${fieldItem.fieldClass} ${fieldItem.fieldName}<#if fieldItem_has_next>,</#if><#t>
        </#if>
    </#list>
</#macro>

<#macro noIdParamListRequireFalse>
    <#list classInfo.fieldList as fieldItem >
        <#if fieldItem.columnName != "id">
            @RequestParam(name="${fieldItem.fieldName}", required= false) ${fieldItem.fieldClass} ${fieldItem.fieldName}<#if fieldItem_has_next>,</#if><#t>
        </#if>
    </#list>
</#macro>
@Api("${classInfo.classComment}管理")
@RequestMapping(value = "${r"${modulePath}"}/${classInfo.className ? uncap_first}")
@RestController
public interface ${classInfo.className}Api {

    @ApiOperation(value = "新增")
    @PostMapping("/insert")
    Boolean insert(<@noIdParamList/>);

    @ApiOperation(value = "删除")
    @PostMapping("/delete")
    Boolean delete(@RequestParam(name="id") String id);

    @ApiOperation(value = "更新")
    @PostMapping("/update")
    Boolean update(@RequestParam(name="id") String id,<@noIdParamListRequireFalse/>);

    @ApiOperation(value = "详情")
    @PostMapping("/get")
    ${classInfo.className}Vo get(@RequestParam(name="id") String id);

    @ApiOperation(value = "列表")
    @PostMapping("/findList")
    PageVo<${classInfo.className}ListVo> findList(@RequestParam(name = "pageNo") Integer pageNo,
                     @RequestParam(name = "pageSize") Integer pageSize);

}
