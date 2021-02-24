package com.jarcheng.courseware.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jarcheng.mbg.dao.CwUserCoursewareMapper;
import com.jarcheng.mbg.model.CwCourseware;
import com.jarcheng.mbg.dao.CwCoursewareMapper;
import com.jarcheng.courseware.service.CwCoursewareService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jarcheng.mbg.model.CwUserCourseware;
import com.jarcheng.mbg.model.dto.UserDto;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 起凡
 * @since 2021-02-17
 */
@Service
public class CwCoursewareServiceImpl extends ServiceImpl<CwCoursewareMapper, CwCourseware> implements CwCoursewareService {
    @Autowired
    CwCoursewareMapper cwCoursewareMapper;
    @Autowired
    CwUserCoursewareMapper cwUserCoursewareMapper;

    @Override
    @Transactional
    public boolean addCW(CwCourseware cwCourseware) {
        save(cwCourseware);
        return true;
    }

    @Override
    public boolean updateCW(CwCourseware cwCourseware) {
        cwCoursewareMapper.updateById(cwCourseware);
        return true;
    }

    @Override
    public boolean deleteCW(Integer id) {
        cwCoursewareMapper.deleteById(id);
        return true;
    }

    @Override
    public CwCourseware getCW(Integer id) {
        UserDto userDto = (UserDto) SecurityUtils.getSubject().getPrincipal();
        List<CwUserCourseware> cwUserCoursewares = cwUserCoursewareMapper.selectList(Wrappers.<CwUserCourseware>lambdaQuery().eq(CwUserCourseware::getUserId, userDto.getId()));
        boolean b = cwUserCoursewares.stream().anyMatch(x -> x.getCwId().equals(id));
        if (b) {
            return cwCoursewareMapper.selectById(id);
        }
        return null;
    }

    @Override
    public IPage<CwCourseware> listCW(Integer start) {
        IPage<CwCourseware> page = new Page<>(start, 10);
        IPage<CwCourseware> cwCoursewareIPage1 = cwCoursewareMapper.selectPage(page, null);
        if (!SecurityUtils.getSubject().hasRole("管理员")) {
            List<CwCourseware> collect = cwCoursewareIPage1.getRecords().stream().peek(x -> x.setUrl(null)).collect(Collectors.toList());
            cwCoursewareIPage1.setRecords(collect);
        }
        return cwCoursewareIPage1;
    }

    @Override
    public List<CwCourseware> getCarousel() {
        List<CwCourseware> cwCoursewares = cwCoursewareMapper.selectList(Wrappers.<CwCourseware>lambdaQuery().gt(CwCourseware::getIsCarousel, 0).orderByAsc(CwCourseware::getIsCarousel));
        if (!SecurityUtils.getSubject().hasRole("管理员")) {
            List<CwCourseware> collect = cwCoursewares.stream().peek(x -> x.setUrl(null)).collect(Collectors.toList());
            return collect;
        }
        return cwCoursewares;
    }
}
