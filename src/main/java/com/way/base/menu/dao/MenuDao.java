package com.way.base.menu.dao;

import com.way.base.menu.entity.Menu;
import com.way.common.rom.IBaseMapperDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MenuDao extends IBaseMapperDao<Menu, Long> {

    Menu get(Long id);

    List<Menu> getTopMenu();

    List<Menu> getChildrenMenus(Long id);

    void add(Menu menu);

    Integer remove(Long id);

    Integer update(Menu menu);
    
    List<Menu> getMenuListByIds(@Param(value = "ids") List<Long> ids, @Param(value = "isTop") boolean isTop);

    String getLastMenuCode(String formatDate);

    int queryMenuCountByName(String menuName);


}
