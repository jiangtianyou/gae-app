package ${packageName}.dao;

import org.apache.ibatis.annotations.Param;
import java.util.List;
import com.wdit.common.persistence.CrudDao;
import com.wdit.common.persistence.annotation.MyBatisDao;
import ${packageName}.entity.${classInfo.className};

@MybatisDao
public interface ${classInfo.className}Dao extends CrudDao<${classInfo.className}>{

}