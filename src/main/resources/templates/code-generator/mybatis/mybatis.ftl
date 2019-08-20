<#assign upper = "com.app.modules.common.utils.freemarker.UpperDirective"?new()>
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${packageName}.dao.${classInfo.className}Dao">
<#if isJoin?exists && isJoin != "NO">
    <resultMap id="joinedResultMap" type="${packageName}.entity.${classInfo.className}" >
        <#if classInfo.fieldList?exists && classInfo.fieldList?size gt 0>
            <#list classInfo.fieldList as fieldItem >
                <result column="${fieldItem.columnName}" property="${fieldItem.fieldName}" />
            </#list>
        </#if>
                <collection property="list属性名" ofType="list中的类型">
                    <id property="id" column="example_id"/>
                    <result property="body" column="post_body"/>
                </collection>
    </resultMap>
</#if>

    <sql id="baseColumns">
        <#if classInfo.fieldList?exists && classInfo.fieldList?size gt 0>
            <#list classInfo.fieldList as fieldItem >
                ${fieldItem.columnName}<#if fieldItem_has_next>,</#if>
            </#list>
        </#if>
    </sql>

    <#-- 写一行是为了控制生成字符串不必要的空格 -->
    <#macro queryReturn><#if isJoin?exists && isJoin != "NO">resultMap="joinedResultMap"<#else>resultType="${packageName}.entity.${classInfo.className}"</#if></#macro>


    <select id="findList" <@queryReturn/>>
        SELECT <include refid="baseColumns" />
        FROM ${classInfo.tableName}
        <where>
            del_flag = 0
        </where>
    </select>


    <select id="get" resultType="${packageName}.entity.${classInfo.className}">
        SELECT <include refid="baseColumns" />
        FROM ${classInfo.tableName}
        WHERE id = ${r"#{id}"}
    </select>

    <insert id="insert" parameterType="${packageName}.entity.${classInfo.className}">
        INSERT INTO ${classInfo.tableName}
        <trim prefix="(" suffix=")" suffixOverrides=",">
            <#if classInfo.fieldList?exists && classInfo.fieldList?size gt 0>
                <#list classInfo.fieldList as fieldItem >
                    <#if fieldItem.columnName != "del_flag" >
                        ${fieldItem.columnName}<#if fieldItem_has_next>,</#if>
                    </#if>
                </#list>
            </#if>
        </trim>
        <trim prefix="values (" suffix=")" suffixOverrides=",">
            <#if classInfo.fieldList?exists && classInfo.fieldList?size gt 0>
                <#list classInfo.fieldList as fieldItem >
                    <#if fieldItem.columnName != "del_flag" >
                        ${r"#{"}${fieldItem.fieldName}${r"}"}<#if fieldItem_has_next>,</#if>
                    </#if>
                </#list>
            </#if>
        </trim>
    </insert>

    <update id="update" parameterType="${packageName}.entity.${classInfo.className}">
        UPDATE ${classInfo.tableName}
        <set>
        <#list classInfo.fieldList as fieldItem >
            <#if fieldItem.columnName != "id" && fieldItem.columnName != "create_date" && fieldItem.columnName != "del_flag" >
            ${r"<if test ='null != "}${fieldItem.fieldName}${r"'>"}${fieldItem.columnName} = ${r"#{"}${fieldItem.fieldName}${r"}"}<#if fieldItem_has_next>,</#if>${r"</if>"}
            </#if>
        </#list>
        </set>
        WHERE id = ${r"#{"}id${r"}"}
    </update>

    <udpate id="delete" >
        UPDATE ${classInfo.tableName}
        SET del_flag = 1
        WHERE id = ${r"#{id}"}
    </udpate>


</mapper>