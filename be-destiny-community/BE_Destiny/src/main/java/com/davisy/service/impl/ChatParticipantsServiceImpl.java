package com.davisy.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.davisy.dao.ChatParticipantsDAO;
import com.davisy.entity.ChatParticipants;
import com.davisy.service.ChatParticipantsService;

@Service
public class ChatParticipantsServiceImpl implements ChatParticipantsService {
	@Autowired
	ChatParticipantsDAO participantsDAO;

	@Override
	public List<ChatParticipants> findAllId(int id) {
		List<ChatParticipants> list = participantsDAO.findAllId(id);
		if (list == null)
			return null;
		return list;
	}

	@Override
	public void create(ChatParticipants chatParticipants) {
		if (chatParticipants != null) {
			participantsDAO.save(chatParticipants);
		}
	}

	@Override
	public void update(ChatParticipants chatParticipants) {
		if (chatParticipants != null) {
			participantsDAO.saveAndFlush(chatParticipants);
		}
	}

	@Override
	public void delete(ChatParticipants chatParticipants) {
		if (chatParticipants != null) {
			participantsDAO.delete(chatParticipants);
		}
	}

}
