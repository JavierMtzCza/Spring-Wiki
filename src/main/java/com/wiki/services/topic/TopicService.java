package com.wiki.services.topic;

import com.wiki.models.topic.dtos.TopicDTOBasicResponse;
import com.wiki.models.topic.entities.Topic;

public interface TopicService {

   TopicDTOBasicResponse createTopic(Topic topic);
}
