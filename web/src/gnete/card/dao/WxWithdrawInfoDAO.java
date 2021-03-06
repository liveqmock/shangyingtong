package gnete.card.dao;

import java.util.List;
import java.util.Map;

import flink.util.Paginater;
import gnete.card.entity.WxWithdrawInfo;

public interface WxWithdrawInfoDAO extends BaseDAO {
	
	Paginater findPage(Map<String, Object> params, int pageNumber, int pageSize);
	
	List<WxWithdrawInfo> findList(Map<String, Object> params);
}