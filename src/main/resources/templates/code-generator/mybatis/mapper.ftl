import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import java.util.List;

@MybatisDao
public interface ${classInfo.className}Dao extends CrudDao<${classInfo.className}>{

}