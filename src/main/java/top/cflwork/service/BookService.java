package top.cflwork.service;

import com.alicp.jetcache.anno.*;
import top.cflwork.domain.BookDO;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 图书表
 * 
 * @author 陈飞龙
 * @email 275300091@qq.com
 * @date 2018-10-22 14:17:18
 */
public interface BookService {

	@Cached(cacheType = CacheType.LOCAL)
	@CacheRefresh(refresh = 1800, stopRefreshAfterLastAccess = 3600, timeUnit = TimeUnit.SECONDS)
	BookDO get(Long id);
	@Cached(cacheType = CacheType.LOCAL)
	@CacheRefresh(refresh = 1800, stopRefreshAfterLastAccess = 3600, timeUnit = TimeUnit.SECONDS)
	List<BookDO> list(Map<String, Object> map);
    @Cached(cacheType = CacheType.LOCAL)
    @CacheRefresh(refresh = 1800, stopRefreshAfterLastAccess = 3600, timeUnit = TimeUnit.SECONDS)
	int count(Map<String, Object> map);
	
	int save(BookDO book);
	@CacheUpdate(name = "c1", key = "#id", value = "args[1]")
	int update(BookDO book);
	@CacheInvalidate(name = "c1", key = "args[0]")
	int remove(Long id);
	@CacheInvalidate(name = "c1", key = "args[0]")
	int batchRemove(Long[] ids);
}
