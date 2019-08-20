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

<#macro allParamList>
    <#list classInfo.fieldList as fieldItem >
        ${fieldItem.fieldClass} ${fieldItem.fieldName}<#if fieldItem_has_next>,</#if><#t>
    </#list>
</#macro>


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
<#--    entity.set${fieldItem.fieldName?cap_first}(ifNull(${fieldItem.fieldName},entity.get${fieldItem.fieldName?cap_first}()));-->
        entity.set${fieldItem.fieldName?cap_first}(${fieldItem.fieldName});
        </#if>
    </#list>
</#macro>




public class ${classInfo.className}Controller extends BaseController implements ${classInfo.className}Api {

    @Autowired
    private ${classInfo.className}Service ${classInfo.className?uncap_first}Service;


    @Override
    public Boolean insert(<@noIdParamList/>){
        ${classInfo.className} entity = readForm(<@noIdParamValueList/>);
        ${classInfo.className?uncap_first}Service.save(entity);
        return true;
    }


    @Override
    public Boolean delete(String id){
        ${classInfo.className?uncap_first}Service.delete(id);
        return true;
    }


    @Override
    public Boolean update(<@allParamList/>){
        ${classInfo.className} entity = ${classInfo.className?uncap_first}Service.get(id);
        if(entity == null){
             throw new BusinessException("找不到id对应${classInfo.classComment}");
        }
        <@noIdSelectiveSet/>
        ${classInfo.className?uncap_first}Service.save(entity);
        return true;
    }

    @Override
    public ${classInfo.className}Vo get(String id){
        ${classInfo.className} entity = ${classInfo.className?uncap_first}Service.get(id);
        if(entity == null){
            throw new BusinessException("找不到id对应${classInfo.classComment}");
        }
        return ${classInfo.className}Converter.convert(entity);
    }

    @Override
    public PageVo<${classInfo.className}ListVo> findList(Integer pageNo,Integer pageSize) {

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