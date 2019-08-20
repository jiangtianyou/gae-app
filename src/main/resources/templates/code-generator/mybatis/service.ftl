import java.util.Map;
import com.wdit.common.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wdit.common.service.impl.CrudServiceImpl;
import com.wdit.common.utils.cache.CacheManager;
import com.wdit.modules.survey.dao.${classInfo.className}Dao;

@Service
@Transactional
public class ${classInfo.className}Service extends CrudServiceImpl<${classInfo.className}Dao,${classInfo.className}> {
    @Autowired
    public SurveyService(SurveyDao dao, CacheManager cacheManager) {
        super(dao, cacheManager);
    }

}
