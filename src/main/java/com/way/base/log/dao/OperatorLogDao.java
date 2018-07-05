package com.way.base.log.dao;

import com.way.base.log.entity.OperatorLog;
import com.way.common.rom.IBaseMapper;
import com.way.common.rom.annotation.Pagination;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 
 * @ClassName: OperatorLogDao 
 * @Description: 操作日志DAO(这里用一句话描述这个类的作用) 
 * @author 罗荣 
 * @date 2016年8月30日 下午2:45:40 
 *
 */
public interface OperatorLogDao extends IBaseMapper {
    /**
     * 
     * @名称 deleteByPrimaryKey 
     * @描述 删除一条数据(这里用一句话描述这个方法的作用) 
     * @返回类型 int     
     * @日期 2016年8月30日 下午2:44:24
     * @创建人  罗荣
     * @更新人  罗荣
     *
     */
    int deleteByPrimaryKey(Long id);
    /**
     * 
     * @名称 insert 
     * @描述 插入(这里用一句话描述这个方法的作用) 
     * @返回类型 int     
     * @日期 2016年8月30日 下午2:44:27
     * @创建人  罗荣
     * @更新人  罗荣
     *
     */
    int insert(OperatorLog record);
    /**
     * 
     * @名称 selectByPrimaryKey 
     * @描述 获取一个实体(这里用一句话描述这个方法的作用) 
     * @返回类型 OperatorLog     
     * @日期 2016年8月30日 下午2:44:33
     * @创建人  罗荣
     * @更新人  罗荣
     *
     */
    OperatorLog selectByPrimaryKey(Long id);
    /**
     * 
     * @名称 selectAll 
     * @描述 选择所有数据(这里用一句话描述这个方法的作用) 
     * @返回类型 List<OperatorLog>     
     * @日期 2016年8月30日 下午2:44:39
     * @创建人  罗荣
     * @更新人  罗荣
     *
     */
    List<OperatorLog> selectAll();
    /**
     * 
     * @名称 updateByPrimaryKey 
     * @描述 更新(这里用一句话描述这个方法的作用) 
     * @返回类型 int     
     * @日期 2016年8月30日 下午2:44:43
     * @创建人  罗荣
     * @更新人  罗荣
     *
     */
    int updateByPrimaryKey(OperatorLog record);
    /**
     * 
     * @名称 queryPageList 
     * @描述 分页查询(这里用一句话描述这个方法的作用) 
     * @返回类型 List<OperatorLog>     
     * @日期 2016年8月30日 下午2:44:47
     * @创建人  罗荣
     * @更新人  罗荣
     *
     */
    List<OperatorLog> queryPageList(@Param(value = "queryUserName") String queryUserName,
                                    @Param(value = "startDate") Date startDate, @Param(value = "endDate") Date endDate,
                                    @Param(value = "page") Pagination<OperatorLog> pagination);
}