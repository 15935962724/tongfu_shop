package com.tongfu.service;


import com.tongfu.entity.Department;

import java.util.List;
import java.util.Map;

/**
 * 医院
 */
public interface DepartmentService {


    int deleteByPrimaryKey(Long id);

    int insert(Department record);

    int insertSelective(Department record);

    Department selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Department record);

    int updateByPrimaryKey(Department record);

    List<Department> selectDepartment(Map query_map);


}
