package com.example.domain.models

import com.redis.om.spring.annotations.Document
import com.redis.om.spring.annotations.Indexed
import com.redis.om.spring.annotations.Searchable
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate

@Document
class Article {

    @Id
    @Indexed
    private String id

    @Searchable
    private String title

    private String content

    @Indexed
    private Set<String> tags = []

    @Indexed
    private User author

    @CreatedDate
    private Date createdDate

    @LastModifiedDate
    private Date lastModifiedDate

    private Article(String title,
                    String content,
                    User author) {
        this.title = title
        this.content = content
        this.tags = tags
        this.author = author
    }

    static Article of(String title,
                      String content,
                      User author) {
        return new Article(title, content, author)
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

    User getAuthor() {
        return author
    }

    void setAuthor(User author) {
        this.author = author
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
        return "Article{" +
                "id='$id'," +
                " title='$title'," +
                " content='$content'," +
                " tags=$tags," +
                " author=$author," +
                " createdDate=$createdDate," +
                " lastModifiedDate=$lastModifiedDate" +
                "}"
    }

    boolean equals(o) {
        if (this.is(o)) return true
        if (o == null || getClass() != o.class) return false

        Article article = (Article) o

        if (author != article.author) return false
        if (content != article.content) return false
        if (createdDate != article.createdDate) return false
        if (id != article.id) return false
        if (lastModifiedDate != article.lastModifiedDate) return false
        if (tags != article.tags) return false
        if (title != article.title) return false

        return true
    }

    int hashCode() {
        int result
        result = (id != null ? id.hashCode() : 0)
        result = 31 * result + (title != null ? title.hashCode() : 0)
        result = 31 * result + (content != null ? content.hashCode() : 0)
        result = 31 * result + (tags != null ? tags.hashCode() : 0)
        result = 31 * result + (author != null ? author.hashCode() : 0)
        result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0)
        result = 31 * result + (lastModifiedDate != null ? lastModifiedDate.hashCode() : 0)
        return result
    }
}
