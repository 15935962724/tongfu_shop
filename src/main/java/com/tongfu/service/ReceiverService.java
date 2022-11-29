package com.tongfu.service;

import com.tongfu.entity.Receiver;

import java.util.List;
import java.util.Map;


public interface ReceiverService {


    int insertReceiver(Receiver receiver);

    List<Receiver> selectByMember(Long member);

    Receiver selectById(Long id);

    int updateReceiver(Receiver receiver);

    int deleteReceiver(Long id);

    Receiver selectIsDefault(Long member);

    List<Map> getReceiver(Long memberId);

    int updateReceiverIsDefault(Long memberId);

    Map getReceiverById(Long id);


}
