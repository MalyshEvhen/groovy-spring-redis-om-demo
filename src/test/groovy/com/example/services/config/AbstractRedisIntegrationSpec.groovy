package com.example.services.config

import com.example.domain.models.Article
import com.example.domain.models.Event
import com.example.domain.models.User
import com.example.repositories.ArticleRepository
import com.example.repositories.EventRepository
import com.example.repositories.UserRepository
import com.example.util.FakeDataGenerator
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.testcontainers.containers.GenericContainer
import org.testcontainers.spock.Testcontainers
import org.testcontainers.utility.DockerImageName
import spock.lang.Specification

import static com.example.domain.models.User.Role.ARTIST
import static com.example.domain.models.User.Role.AUTHOR
import static com.example.util.FakeDataGenerator.getUser

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Testcontainers
class AbstractRedisIntegrationSpec extends Specification {

    private static GenericContainer redis

    static {
        redis = new GenericContainer(DockerImageName.parse('redis/redis-stack:7.2.0-v0'))
                .withExposedPorts(6379)
        redis.start()
        System.setProperty("spring.data.redis.host", redis.getHost())
        System.setProperty("spring.data.redis.port", redis.getMappedPort(6379).toString())
    }

    def cleanupSpec() {
        redis.stop()
    }

    @TestConfiguration
    static class TestDataInitializer {

        @Bean
        CommandLineRunner loadTestData(
                UserRepository userRepository,
                ArticleRepository articleRepository,
                EventRepository eventRepository
        ) {
            return (args) -> {
                (1..100).each {
                    User author = userRepository.save(getUser(AUTHOR))

                    (1..10).each {
                        Article article = FakeDataGenerator.getArticle(author)
                        articleRepository.save(article)
                    }
                }

                (1..10).each {
                    Set<User> artists = []

                    (1..10).each {
                        User artist = userRepository.save(getUser(ARTIST))
                        artists.add(artist)
                    }
                    Event event = FakeDataGenerator.getEvent(artists)
                    eventRepository.save(event)
                }
            }
        }

    }

}
