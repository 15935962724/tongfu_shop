package com.tongfu.dao;

import com.tongfu.entity.Receiver;

import java.util.List;
import java.util.Map;

public interface ReceiverDao {
    int deleteByPrimaryKey(Long id);

    int insert(Receiver record);

    int insertSelective(Receiver record);

    Receiver selectByPrimaryKey(Long id);

    List<Receiver> selectByMember(Long member);

    Receiver selectIsDefault(Long member);

    int updateByPrimaryKeySelective(Receiver record);

    int updateByPrimaryKey(Receiver record);

    List<Map> getReceiver(Long memberId);

    int updateReceiverIsDefault(Long memberId);

    Map getReceiverById(Long id);
}