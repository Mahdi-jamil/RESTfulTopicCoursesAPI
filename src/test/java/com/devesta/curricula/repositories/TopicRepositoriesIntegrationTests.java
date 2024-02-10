package com.devesta.curricula.repositories;

import com.devesta.curricula.DataTestUtil;
import com.devesta.curricula.domain.entities.Topic;
import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TopicRepositoriesIntegrationTests {

    private final TopicRepository underTest;
    @Autowired
    public TopicRepositoriesIntegrationTests(TopicRepository underTest) {
        this.underTest = underTest;
    }

    @Test
    public void testThatTopicCanStoredAndGet(){
        Topic topic = DataTestUtil.createTopicInstance();
        underTest.save(topic);
        Optional<Topic> result = underTest.findById(topic.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(topic);
    }

    @Test
    public void testTopicCanBeUpdated(){
        Topic topic = DataTestUtil.createTopicInstance();
        underTest.save(topic);
        topic.setName("saved name");

        underTest.save(topic);
        Optional<Topic> result = underTest.findById(topic.getId());
        Assertions.assertThat(result).isPresent();
        Assertions.assertThat(result.get()).isEqualTo(topic);
    }

    @Test
    public void testTopicCanBeDeleted(){
        Topic topic = DataTestUtil.createTopicInstance();
        underTest.save(topic);
        underTest.delete(topic);
        Optional<Topic> result = underTest.findById(topic.getId());
        Assertions.assertThat(result).isEmpty();
    }


}
