package com.way.base.log.service;

import com.way.base.log.dao.OperatorLogDao;
import com.way.base.log.dto.OperatorLogDto;
import com.way.base.log.entity.OperatorLog;
import com.way.common.log.WayLogger;
import com.way.common.result.ServiceResult;
import com.way.common.rom.annotation.Pagination;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 
 * @ClassName: LogServiceImpl
 * @Description: 日志服务(这里用一句话描述这个类的作用)
 * @author 罗荣
 * @date 2016年8月22日 下午7:29:13
 *
 */
@Service
public class LogServiceImpl implements LogService {
    @Autowired
    OperatorLogDao baseDao;

    @Override
    public ServiceResult<Integer> deleteByPrimaryKey(Long id) {
        int result = baseDao.deleteByPrimaryKey(id);
        return ServiceResult.newSuccess(result);
    }

    @Override
    public ServiceResult<Integer> insert(OperatorLogDto record) {
        OperatorLog log = new OperatorLog();
        BeanUtils.copyProperties(record, log);
        int result = baseDao.insert(log);
        return ServiceResult.newSuccess(result);
    }
    @Override
    public ServiceResult<Integer> insert(Long userId,String userName,String Ip,String desc) {
        OperatorLog log = new OperatorLog();
        log.setOperatorIp(Ip);
        log.setOperatorTime(new Date());
        log.setUserName(userName);
        log.setUserId(userId);
        log.setOperatorDesc(desc);
        int result = baseDao.insert(log);
        return ServiceResult.newSuccess(result);
    }

    @Override
    public ServiceResult<Pagination<OperatorLogDto>> queryPageList(String queryUserName, Date startDate, Date endDate,
                                                                   Integer pageSize, Integer pageIndex) {

        Pagination<OperatorLog> entityPage = new Pagination<OperatorLog>(pageIndex, pageSize);
        Pagination<OperatorLogDto> returnPage = new Pagination<OperatorLogDto>(pageIndex, pageSize);
        
        WayLogger.info("LogServiceImpl.queryPageList 输入参数:queryUserName[" + queryUserName + "]startDate[" + startDate
                + "]endDate[" + endDate + "]pageSize[" + pageSize + "]pageIndex[" + pageIndex + "]");
        
        List<OperatorLog> OperatorLogs = baseDao.queryPageList(queryUserName, startDate, endDate, entityPage);

        List<OperatorLogDto> productDtos = new ArrayList<OperatorLogDto>();
        for (OperatorLog log : OperatorLogs) {
            OperatorLogDto temp = new OperatorLogDto();
            BeanUtils.copyProperties(log, temp);
            productDtos.add(temp);
        }
        returnPage.setResult(productDtos);
        returnPage.setTotal(entityPage.getTotal());
        return ServiceResult.newSuccess(returnPage);
    }

}
