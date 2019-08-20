package ${packageName}.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import ${packageName}.api.${classInfo.className}Api;
import com.wdit.common.vo.PageVo;
import com.wdit.common.persistence.Page;
import static com.wdit.common.utils.ApiUtils.buildPage;
import ${packageName}.vo.${classInfo.className}Vo;
import ${packageName}.vo.${classInfo.className}ListVo;
<#-- 方法签名上用-->
<#macro noIdParamListMethod>
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


<#-- 方法体内用-->
<#macro noIdParamList>
    <#list classInfo.fieldList as fieldItem >
        <#if fieldItem.columnName != "id">
            ${fieldItem.fieldClass} ${fieldItem.fieldName}<#if fieldItem_has_next>,</#if><#t>
        </#if>
    </#list>
</#macro>

<#macro noIdParamValueList>
    <#list classInfo.fieldList as fieldItem >
        <#if fieldItem.columnName != "id">
            ${fieldItem.fieldName}<#if fieldItem_has_next>,</#if><#t>
        </#if>
    </#list>
</#macro>

<#macro noIdSelectiveSet>
    <#list classInfo.fieldList as fieldItem >
        <#if fieldItem.columnName != "id">
        entity.set${fieldItem.fieldName?cap_first}(ifNull(${fieldItem.fieldName},entity.get${fieldItem.fieldName?cap_first}));
        </#if>
    </#list>
</#macro>

@RequestMapping(value = "${r"${modulePath}"}/${classInfo.className ? uncap_first}")
@RestController
public class ${classInfo.className}Controller extends BaseController{

    @Autowired
    private ${classInfo.className}Service ${classInfo.className?uncap_first}Service;


    @PostMapping("/insert")
    public ReturnMsg<Object> insert(<@noIdParamListMethod/>){
        ${classInfo.className} entity = readForm(<@noIdParamValueList/>);
        ${classInfo.className?uncap_first}Service.save(entity);
        return successResult();
    }

    @PostMapping("/delete")
    public ReturnMsg<Object> delete(@RequestParam(name="id") String id){
        ${classInfo.className?uncap_first}Service.delete(id);
        return successResult();
    }


    @PostMapping("/update")
    public ReturnMsg<Object> update(@RequestParam(name="id") String id,<@noIdParamListRequireFalse/>){
        ${classInfo.className} entity = ${classInfo.className?uncap_first}Service.get(id);
        if(entity == null){
             throw failResult("找不到id对应${classInfo.classComment}");
        }
        <@noIdSelectiveSet/>
        ${classInfo.className?uncap_first}Service.save(entity);
        return successResult();
    }

    @PostMapping("/get")
    public ReturnMsg<${classInfo.className}Vo> get(@RequestParam(name="id") String id){
        ${classInfo.className} entity = ${classInfo.className?uncap_first}Service.get(id);
        if(entity == null){
            throw failResult("找不到id对应${classInfo.classComment}");
        }
        return ${classInfo.className}Converter.convert(entity);
    }

    @PostMapping("/findList")
    public ReturnMsg${r"<PageVo<"}${classInfo.className}ListVo>> findList(@RequestParam(name = "pageNo") Integer pageNo,
                     @RequestParam(name = "pageSize") Integer pageSize) {

        ${classInfo.className} entity = new ${classInfo.className}();
        entity.setQueryProp("xxxx", siteId);
        Page<${classInfo.className}> pageResult = ${classInfo.className?uncap_first}Service.findPage(entity, buildPage(pageNo, pageSize));
        return ${classInfo.className}Converter.convert(pageResult);
    }



    private ${classInfo.className} readForm(<@noIdParamList/>) {
        ${classInfo.className} entity = new ${classInfo.className}();
        <#list classInfo.fieldList as fieldItem >
        <#if fieldItem.columnName != "id">
        entity.set${fieldItem.fieldName?cap_first}(${fieldItem.fieldName});
        </#if>
        </#list>
        return entity;
    }
}