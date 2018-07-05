package com.way.base.menu.service;

import com.way.base.menu.common.BaseConstants;
import com.way.base.menu.dao.MenuDao;
import com.way.base.menu.dto.MenuDto;
import com.way.base.menu.entity.Menu;
import com.way.common.log.WayLogger;
import com.way.common.result.ServiceResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuDao menuDao;

    /**
     * 根据id获取菜单信息
     */
    @Override
    public ServiceResult<MenuDto> getMenuById(Long menuId) {
        WayLogger.debug("获取菜单信息[getMenuById] begin: id=" + menuId);
        Menu menu = menuDao.get(menuId);
        MenuDto menuDto;
        if (null == menu) {
            WayLogger.debug("获取菜单信息[getMenuById] end: menu=null");
            return ServiceResult.newSuccess(null);
        } else {
            menuDto = new MenuDto();
            BeanUtils.copyProperties(menu, menuDto);
            WayLogger.debug("获取菜单信息[getMenuById] end: menu=" + menuDto);
            return ServiceResult.newSuccess(menuDto);
        }
    }

    /**
     * 获取顶级菜单
     */
    @Override
    public ServiceResult<List<MenuDto>> getTopMenu() {
        WayLogger.debug("获取顶级菜单 [getTopMenu] begin");
        List<Menu> menuList = menuDao.getTopMenu();
        List<MenuDto> menuDtoList = new ArrayList<MenuDto>();
        MenuDto dto;
        for (Menu menu : menuList) {
            dto = new MenuDto();
            BeanUtils.copyProperties(menu, dto);
            menuDtoList.add(dto);
        }
        WayLogger.debug("获取顶级菜单 [getTopMenu] end:menuDtoList= " + menuDtoList);
        return ServiceResult.newSuccess(menuDtoList);
    }

    /**
     * 获取菜单的子菜单
     */
    @Override
    public ServiceResult<List<MenuDto>> getChildrenMenu(Long id) {
        WayLogger.debug("获取子菜单 [getChildrenMenu] begin: id=" + id);
        List<Menu> menuList = menuDao.getChildrenMenus(id);
        List<MenuDto> menuDtoList = new ArrayList<MenuDto>();
        MenuDto dto;
        for (Menu menu : menuList) {
            dto = new MenuDto();
            BeanUtils.copyProperties(menu, dto);
            menuDtoList.add(dto);
        }
        WayLogger.debug("获取子菜单 [getChildrenMenu] end: menuDtoList=" + menuDtoList);
        return ServiceResult.newSuccess(menuDtoList);
    }

    /**
     * 获取所有的菜单
     */
    @Override
    public ServiceResult<List<MenuDto>> getAllMenu() {
        WayLogger.debug("获取所有的菜单 [getAllMenu] begin");
        List<Menu> menuList = menuDao.getTopMenu();
        List<MenuDto> menuDtoList = formatMenuListForShow(menuList);
        WayLogger.debug("获取所有的菜单 [getAllMenu] end: menuDtoList=" + menuDtoList);
        return ServiceResult.newSuccess(menuDtoList);
    }

    /**
     * 删除菜单
     */
    @Override
    @Transactional("transactionManager")
    public ServiceResult<String> delete(Long id) {
        WayLogger.debug("删除菜单 [delete] begin: id=" + id);
        menuDao.remove(id);
        WayLogger.debug("删除菜单 [delete] end: id=" + id);
        return ServiceResult.newSuccess();
    }

    /**
     * 添加编辑菜单
     */
    @Override
    public ServiceResult<String> edit(MenuDto menuDto) {
        if (null == menuDto) {
            WayLogger.debug("添加编辑菜单菜单 [edit] begin: menu=null");
            return ServiceResult.newSuccess();
        }
        WayLogger.debug("添加编辑菜单菜单 [edit] begin: menu=" + menuDto);
        Menu menu = new Menu();
        BeanUtils.copyProperties(menuDto, menu);
        if (BaseConstants.SYS_MENU_ROOT_ID.equals(menuDto.getParentId())) {
            menu.setMenuLevel(BaseConstants.SYS_MENU_LEVEL_0);
        } else {
            menu.setMenuLevel(BaseConstants.SYS_MENU_LEVEL_1);
        }
        if (null != menu.getId()) {
            // 编辑菜单
            menuDao.update(menu);

        } else {
            // 添加菜单
            menu.setMenuCode(generateMenuCode());
            menuDao.add(menu);
        }
        WayLogger.debug("添加编辑菜单菜单 [edit] end: menu=" + menuDto);
        return ServiceResult.newSuccess();
    }

    /**
     * 
     * @param menuList
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @Description:格式化菜单显示，添加菜单叶
     */
    private List<MenuDto> formatMenuListForShow(List<Menu> menuList) {

        List<MenuDto> menuDtoList = new ArrayList<MenuDto>();
        MenuDto dto;
        // 得到子菜单
        for (Menu menu : menuList) {
            dto = new MenuDto();
            BeanUtils.copyProperties(menu, dto);

            List<MenuDto> subMenuDtoList = new ArrayList<MenuDto>();
            MenuDto subDto;
            List<Menu> childen = menuDao.getChildrenMenus(menu.getId());
            for (Menu submenu : childen) {
                subDto = new MenuDto();
                BeanUtils.copyProperties(submenu, subDto);
                subDto.setMenuName("　┠" + submenu.getMenuName());
                subMenuDtoList.add(subDto);
            }
            dto.setChirdlen(subMenuDtoList);
            dto.setMenuName("┠" + menu.getMenuName());
            menuDtoList.add(dto);
        }
        return menuDtoList;
    }

    private synchronized String generateMenuCode() {
        String prefix = "IDM";
        String code;
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        String formatDate = format.format(date);
        // 获取当天最大编码
        String lastCode = menuDao.getLastMenuCode(formatDate);//
        if (null == lastCode) {
            code = prefix + formatDate + "001";
        } else {
            code = prefix + formatDate + String.format("%03d", 1 + Integer.parseInt(lastCode.substring(11, 14)));
        }
        return code;
    }

    /**
     * 描述： 系统页面左侧的菜单
     * 
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    private List<MenuDto> formatMenuList(List<Menu> menuTempList) {
        List<Long> parentIds = new ArrayList<Long>();
        // 得到所有的顶级菜单
        for (Menu menu : menuTempList) {
            if (0 != menu.getMenuLevel() && null != menu.getParentId()) {
                if (!parentIds.contains(menu.getParentId())) {
                    parentIds.add(menu.getParentId());
                }
            }
        }
        // 得到顶级菜单
        List<Menu> level0MenusList = menuDao.getMenuListByIds(parentIds, true);
        List<MenuDto> menuDtoList = new ArrayList<MenuDto>();
        MenuDto menuDto;
        List<MenuDto> childenList;
        MenuDto childMenuDto;
        for (Menu level0Menu : level0MenusList) {
            menuDto = new MenuDto();
            BeanUtils.copyProperties(level0Menu, menuDto);
            childenList = new ArrayList<MenuDto>();
            // 添加子菜单
            for (Menu menutemp : menuTempList) {
                if (level0Menu.getId().equals(menutemp.getParentId())) {
                    childMenuDto = new MenuDto();
                    BeanUtils.copyProperties(menutemp, childMenuDto);
                    childenList.add(childMenuDto);
                }
            }

            menuDto.setChirdlen(childenList);
            menuDtoList.add(menuDto);
        }
        return menuDtoList;
    }

    @Override
    public ServiceResult<List<MenuDto>> getMenuListByIds(List<Long> ids) {
        WayLogger.debug("根据ids获取菜单 [getMenuListByIds] begin: ids=" + ids);
        if (null == ids || ids.isEmpty()) {
            List<MenuDto> dtos = new ArrayList<MenuDto>();
            return ServiceResult.newSuccess(dtos);
        }
        List<Menu> menuTempList = menuDao.getMenuListByIds(ids, false);
        List<MenuDto> menuDtoList = formatMenuList(menuTempList);
        WayLogger.debug("根据ids获取菜单 [getMenuListByIds] end: menuDtoList=" + menuDtoList);
        return ServiceResult.newSuccess(menuDtoList);
    }

    @Override
    public boolean isMenuExist(String menuName) {
        int count = menuDao.queryMenuCountByName(menuName);
        if (count > 0) {
            return true;
        }
        return false;
    }

}