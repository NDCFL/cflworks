package top.cflwork.dao;

import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.CreateCache;
import top.cflwork.domain.BookDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 图书表
 * @author 陈飞龙
 * @email 275300091@qq.com
 * @date 2018-10-22 14:17:18
 */
@Mapper
public interface BookDao {
	BookDO get(Long id);
	
	List<BookDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(BookDO book);
	
	int update(BookDO book);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
