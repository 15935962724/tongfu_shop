package com.tongfu.service.impl;

import com.tongfu.dao.ReceiverDao;
import com.tongfu.entity.Receiver;
import com.tongfu.service.ReceiverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ReceiverServiceImpl implements ReceiverService {

    @Autowired
    ReceiverDao receiverDao;

    @Override
    public int insertReceiver(Receiver receiver) {
        return receiverDao.insertSelective(receiver);
    }

    @Override
    public List<Receiver> selectByMember(Long member) {
        return receiverDao.selectByMember(member);
    }

    @Override
    public Receiver selectById(Long id) {
        return receiverDao.selectByPrimaryKey(id);
    }

    @Override
    public int updateReceiver(Receiver receiver) {
        return receiverDao.updateByPrimaryKeySelective(receiver);
    }

    @Override
    public int deleteReceiver(Long id) {
        return receiverDao.deleteByPrimaryKey(id);
    }

    @Override
    public Receiver selectIsDefault(Long member) {
        return receiverDao.selectIsDefault(member);
    }

    @Override
    public List<Map> getReceiver(Long memberId) {
        return receiverDao.getReceiver(memberId);
    }

    @Override
    public int updateReceiverIsDefault(Long memberId) {
        return receiverDao.updateReceiverIsDefault(memberId);
    }

    @Override
    public Map getReceiverById(Long id) {
        return receiverDao.getReceiverById(id);
    }
}
