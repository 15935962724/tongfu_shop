package com.tongfu.dao;

import com.tongfu.entity.Department;

import java.util.List;
import java.util.Map;

public interface DepartmentDao {
    int deleteByPrimaryKey(Long id);

    int insert(Department record);

    int insertSelective(Department record);

    Department selectByPrimaryKey(Long id);

    List<Department> selectDepartment(Map query_map);

    int updateByPrimaryKeySelective(Department record);

    int updateByPrimaryKey(Department record);
}