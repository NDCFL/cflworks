package top.cflwork.service;

import top.cflwork.vo.LogDO;
import top.cflwork.vo.PageDO;
import top.cflwork.util.Query;
import org.springframework.stereotype.Service;

@Service
public interface LogService {
	void save(LogDO logDO);
	PageDO<LogDO> queryList(Query query);
	int remove(Long id);
	int batchRemove(Long[] ids);
}
