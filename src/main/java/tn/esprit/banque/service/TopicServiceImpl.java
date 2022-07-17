package tn.esprit.gbanque.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.gbanque.model.Topic;
import tn.esprit.gbanque.repository.TopicRepository;
@Service
public class TopicServiceImpl implements TopicService {
	@Autowired
	private TopicRepository topicRepository;

	@Override
	public Topic addTopic(Topic topic) {
		return topicRepository.save(topic);
	}

	@Override
	public Topic updateTopic(Topic topic) {
		return topicRepository.save(topic);
	}

	@Override
	public void deleteTopic(Long id) {
		topicRepository.deleteById(id);
	}

	@Override
	public List<Topic> findAllTopic() {
		return (List<Topic>) topicRepository.findAll();
	}

	@Override
	public Topic findTopicById(Long id) {
		return topicRepository.findById(id).get();
	}

}
