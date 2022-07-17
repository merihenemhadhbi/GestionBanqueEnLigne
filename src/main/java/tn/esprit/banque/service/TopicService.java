package tn.esprit.gbanque.service;

import java.util.List;

import tn.esprit.gbanque.model.Topic;

public interface TopicService {
	Topic addTopic(Topic topic);
	Topic updateTopic(Topic topic);
	void deleteTopic(Long id);
	List<Topic> findAllTopic();
	Topic findTopicById(Long id);
}
