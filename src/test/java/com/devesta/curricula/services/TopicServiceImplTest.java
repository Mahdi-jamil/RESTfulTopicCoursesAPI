package com.devesta.curricula.services;

import com.devesta.curricula.DataTestUtil;
import com.devesta.curricula.domain.entities.Topic;
import com.devesta.curricula.repositories.TopicRepository;
import com.devesta.curricula.services.impl.TopicServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Optional;
import static org.mockito.Mockito.*;
/*
* Arrange -> Bring the base objects to make the test
* Act -> Do the actual call to tested method
* Assert -> Check for the result
*/
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class TopicServiceImplTest {
    @Mock
    private TopicRepository topicRepository;

    @InjectMocks
    private TopicServiceImpl underTest;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetTopic() {
        Long topicId = 123L;
        Topic expectedTopic = DataTestUtil.createTopicInstance();
        when(topicRepository.findById(topicId)).thenReturn(Optional.of(expectedTopic));

        Topic actualTopic = underTest.getTopic(topicId);

        Assertions.assertThat(actualTopic).isEqualTo(expectedTopic);
        verify(topicRepository, times(1)).findById(topicId);

    }

    @Test
    public void testAddTopic() {
        Topic topic = DataTestUtil.createTopicInstance();
        when(topicRepository.save(topic)).thenReturn(topic);

        Topic result = underTest.addTopic(topic);

        Assertions.assertThat(topic).isEqualTo(result);
    }

    @Test
    public void testUpdateTopic() {
        Topic topic = DataTestUtil.createTopicInstance();
        long id = topic.getId();
        when(topicRepository.save(topic)).thenReturn(topic);

        Topic result = underTest.updateTopic(topic, id);

        Assertions.assertThat(topic).isEqualTo(result);
    }

    @Test
    public void testRemoveTopic() {
        long id = 1L;

        underTest.removeTopic(id);

        verify(topicRepository, times(1)).deleteById(id);
    }

}

