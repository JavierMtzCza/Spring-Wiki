package com.wiki.models.topic.repositories;

import com.wiki.models.topic.entities.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepository extends JpaRepository<Topic,Long> {
}
