package ${packageName}.converter;

import com.wdit.common.persistence.Page;
import com.wdit.common.utils.ApiUtils;
import com.wdit.common.vo.PageVo;
import com.wdit.modules.survey.entity.AnswerSheet;
import com.wdit.modules.survey.vo.SubmitMemberListVo;

import static org.apache.commons.lang3.StringUtils.defaultString;

public class ${classInfo.className}Converter {
    public static PageVo<${classInfo.className}ListVo> convert(Page<${classInfo.className}> page) {
        return ApiUtils.entityPageToVo(page, ${classInfo.className}Converter::convertToListVo);
    }


    private static ${classInfo.className}ListVo convertToListVo(${classInfo.className} entity) {
        return null;
    }

    public static ${classInfo.className}Vo convert(${classInfo.className} entity) {
        return null;
    }
}