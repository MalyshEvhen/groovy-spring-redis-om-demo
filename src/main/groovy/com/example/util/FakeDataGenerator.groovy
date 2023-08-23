package com.example.util

import com.example.domain.models.Article
import com.example.domain.models.Event
import com.example.domain.models.User
import com.github.javafaker.Faker

import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.concurrent.ThreadLocalRandom

class FakeDataGenerator {

    private static final Faker FAKER = new Faker()

    private static final Closure<String> RANDOM_TAG = { -> FAKER.book().genre() }
    private static final Closure<String> RANDOM_TEXT = { -> FAKER.lorem().paragraph() }
    private static final Closure<String> RANDOM_TITLE = { -> FAKER.book().title() }
    private static final Closure<String> RANDOM_EMAIL = { -> FAKER.internet().emailAddress() }
    private static final Closure<String> RANDOM_LASTNAME = { -> FAKER.address().lastName() }
    private static final Closure<String> RANDOM_FIRSTNAME = { -> FAKER.name().firstName() }

    private static final Closure<Article> ADD_TAGS_TO_ARTICLE = { Article a ->
        int counter = ThreadLocalRandom.current().nextInt(5)
        for (it in (0..counter)) {
            a.addTag(RANDOM_TAG)
        }
        return a
    }

    private static final Closure<Event> EVENT_WO_TAGS = { Set<User> artists ->
        String title = RANDOM_TITLE()
        String content = RANDOM_TEXT()
        LocalDateTime begin = FUTURE_DATE()
        LocalDateTime end = FUTURE_DATE(begin)
        return Event.of(title, content, artists, begin, end)
    }

    private static final Closure<Event> ADD_TAGS_TO_EVENT = { Event e ->
        int counter = ThreadLocalRandom.current().nextInt(5)
        for (it in (0..counter)) {
            e.addTag(RANDOM_TAG)
        }
        return e
    }

    private static final Closure<LocalDateTime> FUTURE_DATE = { LocalDateTime from ->
        long min = from == null ? LocalDateTime.now().toEpochSecond(ZoneOffset.MIN) : from.toEpochSecond(ZoneOffset.MIN)
        long max = min + 2_000_000L
        long randomSecond = ThreadLocalRandom.current().nextLong(min, max)
        return LocalDateTime.ofEpochSecond(randomSecond, 0, ZoneOffset.MIN)
    }

    private static final Closure<Article> ARTICLE_WO_TAGS = { User user ->
        String title = RANDOM_TITLE()
        String content = RANDOM_TEXT()

        return Article.of(title, content, user)
    }

    static User getUser(User.Role role) {
        String firstname = RANDOM_FIRSTNAME()
        String lastname = RANDOM_LASTNAME()
        String email = RANDOM_EMAIL()
        String bio = RANDOM_TEXT()

        return User.of(firstname, lastname, email, bio, role)
    }

    static Article getArticle(User user) {
        return ARTICLE_WO_TAGS.andThen(ADD_TAGS_TO_ARTICLE)(user)
    }

    static Event getEvent(Set<User> artists) {
        return EVENT_WO_TAGS.andThen(ADD_TAGS_TO_EVENT)(artists)
    }

}
