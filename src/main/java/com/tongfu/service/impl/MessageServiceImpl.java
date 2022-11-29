package com.tongfu.service.impl;


import com.tongfu.dao.MessageDao;
import com.tongfu.entity.Message;
import com.tongfu.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class MessageServiceImpl implements MessageService {

	@Autowired
	MessageDao messageDao;

	@Override
	public int deleteByPrimaryKey(Long id) {
		return 0;
	}

	@Override
	public int insert(Message record) {
		return 0;
	}

	@Override
	public int insertSelective(Message message) {
		return messageDao.insertSelective(message);
	}

	@Override
	public Message selectByPrimaryKey(Long id) {
		return null;
	}

	@Override
	public int updateByPrimaryKeySelective(Message record) {
		return 0;
	}

	@Override
	public int updateByPrimaryKeyWithBLOBs(Message record) {
		return 0;
	}

	@Override
	public int updateByPrimaryKey(Message record) {
		return 0;
	}

}
