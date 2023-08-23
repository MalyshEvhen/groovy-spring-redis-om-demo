package com.example.domain.models

import com.redis.om.spring.annotations.Document
import com.redis.om.spring.annotations.Indexed
import com.redis.om.spring.annotations.Searchable
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.annotation.Reference

import java.time.LocalDateTime

@Document
class Event {

    @Id
    @Indexed
    private String id

    @Searchable
    private String title

    private String content

    @Indexed
    private Set<String> tags = []

    @Reference
    Set<User> artists = []

    @Indexed
    private LocalDateTime beginDate

    @Indexed
    private LocalDateTime endDate

    @CreatedDate
    private Date createdDate

    @LastModifiedDate
    private Date lastModifiedDate

    private Event(String title,
                  String content,
                  LocalDateTime beginDate,
                  LocalDateTime endDate
    ) {
        this.title = title
        this.content = content
        this.beginDate = beginDate
        this.endDate = endDate
    }

    static Event of(String title,
                    String content,
                    LocalDateTime begin,
                    LocalDateTime end
    ) {
        return new Event(title, content, begin, end)
    }

    static Event of(String title,
                    String content,
                    Collection<User> artists,
                    LocalDateTime begin,
                    LocalDateTime end
    ) {
        def event = new Event(title, content, begin, end)
        event.addGuests(artists)
        return event
    }

    void addGuests(Collection<User> guests) {
        this.artists.addAll(guests)
    }

    void addGuest(User user) {
        this.artists.add(user)
    }

    void addTag(String tag) {
        this.tags.add(tag)
    }

    void addTag(Closure<String> tagClosure) {
        String tag = tagClosure()
        this.tags.add(tag)
    }

    String getId() {
        return id
    }

    void setId(String id) {
        this.id = id
    }

    String getTitle() {
        return title
    }

    void setTitle(String title) {
        this.title = title
    }

    String getContent() {
        return content
    }

    void setContent(String content) {
        this.content = content
    }

    Set<String> getTags() {
        return tags
    }

    void setTags(Set<String> tags) {
        this.tags = tags
    }

    Set<User> getArtists() {
        return artists
    }

    void setArtists(Set<User> artists) {
        this.artists = artists
    }

    LocalDateTime getBeginDate() {
        return beginDate
    }

    void setBeginDate(LocalDateTime beginDate) {
        this.beginDate = beginDate
    }

    LocalDateTime getEndDate() {
        return endDate
    }

    void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate
    }

    Date getCreatedDate() {
        return createdDate
    }

    void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate
    }

    Date getLastModifiedDate() {
        return lastModifiedDate
    }

    void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate
    }

    @Override
    String toString() {
        return "Event{" +
                "id='$id', " +
                "title='$title', " +
                "content='$content', " +
                "tags=$tags, " +
                "artists=$artists, " +
                "beginDate=$beginDate, " +
                "endDate=$endDate, " +
                "createdDate=$createdDate, " +
                "lastModifiedDate=$lastModifiedDate" +
                "}"
    }

    boolean equals(o) {
        if (this.is(o)) return true
        if (o == null || getClass() != o.class) return false

        Event event = (Event) o

        if (artists != event.artists) return false
        if (beginDate != event.beginDate) return false
        if (content != event.content) return false
        if (createdDate != event.createdDate) return false
        if (endDate != event.endDate) return false
        if (id != event.id) return false
        if (lastModifiedDate != event.lastModifiedDate) return false
        if (tags != event.tags) return false
        if (title != event.title) return false

        return true
    }

    int hashCode() {
        int result
        result = (id != null ? id.hashCode() : 0)
        result = 31 * result + (title != null ? title.hashCode() : 0)
        result = 31 * result + (content != null ? content.hashCode() : 0)
        result = 31 * result + (tags != null ? tags.hashCode() : 0)
        result = 31 * result + (artists != null ? artists.hashCode() : 0)
        result = 31 * result + (beginDate != null ? beginDate.hashCode() : 0)
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0)
        result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0)
        result = 31 * result + (lastModifiedDate != null ? lastModifiedDate.hashCode() : 0)
        return result
    }
}
