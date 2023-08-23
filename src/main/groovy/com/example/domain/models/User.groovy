package com.example.domain.models

import com.redis.om.spring.annotations.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate

import static com.example.constraints.SharedConstraints.MAX_CONTENT_LENGTH
import static com.example.constraints.SharedConstraints.MAX_FIELD_LENGTH
import static com.example.constraints.UserConstraints.*

@Document
class User {

    @Id
    @Indexed
    private String id

    @NotNull
    @NotBlank
    @Size(min = MIN_NAME_LENGTH, max = MAX_NAME_LENGTH)
    @Searchable
    private String firstName

    @NotNull
    @NotBlank
    @Size(min = MIN_NAME_LENGTH, max = MAX_NAME_LENGTH)
    @Searchable
    private String lastName

    @NotNull
    @NotBlank
    @Pattern(regexp = EMAIL_REGEXP)
    @Size(max = MAX_FIELD_LENGTH)
    @TagIndexed
    private String email

    @Size(max = MAX_CONTENT_LENGTH)
    @TextIndexed
    private String bio

    @Indexed
    private Set<String> roles = []

    @CreatedDate
    private Date createdDate

    @LastModifiedDate
    private Date lastModifiedDate

    private User(String firstName,
                 String lastName,
                 String email,
                 String bio) {
        this.firstName = firstName
        this.lastName = lastName
        this.email = email
        this.bio = bio
    }

    private User(String firstName,
                 String lastName,
                 String email,
                 String bio,
                 Role role
    ) {
        this.firstName = firstName
        this.lastName = lastName
        this.email = email
        this.bio = bio
        this.roles.add(role.getValue())
    }

    static User of(String firstname,
                   String lastname,
                   String email,
                   String bio) {
        return new User(firstname, lastname, email, bio)
    }

    static User of(String firstname,
                   String lastname,
                   String email,
                   String bio,
                   Role role
    ) {
        return new User(firstname, lastname, email, bio, role)
    }

    void addRole(Role role) {
        this.roles.add(role.getValue())
    }

    String getId() {
        return id
    }

    void setId(String id) {
        this.id = id
    }

    String getFirstName() {
        return firstName
    }

    void setFirstName(String firstName) {
        this.firstName = firstName
    }

    String getLastName() {
        return lastName
    }

    void setLastName(String lastName) {
        this.lastName = lastName
    }

    String getEmail() {
        return email
    }

    void setEmail(String email) {
        this.email = email
    }

    String getBio() {
        return bio
    }

    void setBio(String bio) {
        this.bio = bio
    }

    Set<String> getRoles() {
        return roles
    }

    void setRoles(Set<String> roles) {
        this.roles = roles
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
        return "User{" +
                "id='$id', " +
                "firstName='$firstName', " +
                "lastName='$lastName', " +
                "email='$email', " +
                "bio='$bio', " +
                "roles=$roles, " +
                "createdDate=$createdDate, " +
                "lastModifiedDate=$lastModifiedDate" +
                "}"
    }

    boolean equals(o) {
        if (this.is(o)) return true
        if (o == null || getClass() != o.class) return false

        User user = (User) o

        if (bio != user.bio) return false
        if (createdDate != user.createdDate) return false
        if (email != user.email) return false
        if (firstName != user.firstName) return false
        if (id != user.id) return false
        if (lastModifiedDate != user.lastModifiedDate) return false
        if (lastName != user.lastName) return false
        if (roles != user.roles) return false

        return true
    }

    int hashCode() {
        int result
        result = (id != null ? id.hashCode() : 0)
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0)
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0)
        result = 31 * result + (email != null ? email.hashCode() : 0)
        result = 31 * result + (bio != null ? bio.hashCode() : 0)
        result = 31 * result + (roles != null ? roles.hashCode() : 0)
        result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0)
        result = 31 * result + (lastModifiedDate != null ? lastModifiedDate.hashCode() : 0)
        return result
    }

    enum Role {
        ARTIST('artist'),
        AUTHOR('author');

        private final String value

        Role(String value) {
            this.value = value
        }

        String getValue() {
            return value
        }
    }
}
