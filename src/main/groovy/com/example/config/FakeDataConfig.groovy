package com.example.config

import com.example.domain.models.Article
import com.example.domain.models.Event
import com.example.domain.models.User
import com.example.repositories.ArticleRepository
import com.example.repositories.EventRepository
import com.example.repositories.UserRepository
import com.example.util.FakeDataGenerator
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

import static com.example.domain.models.User.Role.ARTIST
import static com.example.domain.models.User.Role.AUTHOR
import static com.example.util.FakeDataGenerator.getUser

@Configuration
class FakeDataConfig {

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
