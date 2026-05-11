package com.example.twitter_backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.time.LocalDateTime;
import java.util.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Table(name = "users" , schema = "twitter")
public class User  implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nick_name")
    @NotBlank(message = "Kullanıcı ismi boş bırakılamaz.")
    @Size(max = 100 , min = 3)
    private String nickName;

    @Column(name = "first_name")
    @NotBlank(message = "FirstName alanı boş bırakılamaz")
    @Size(max = 100)
    private String firstName;

    @Column(name = "last_name")
    @NotBlank(message = "LastName alanı boş bırakılamaz")
    @Size(max = 100)
    private String lastName;

    @Column(nullable = false , unique = true)
    @Email(message = "Geçerli bir email giriniz")
    @NotBlank(message = "Email boş bırakılamaz")
    @Size(max = 50 , message = "Email 50 karaketerden fazla olamaz")
    private String email;


    @Column(nullable = false)
    @NotBlank(message = "Password boş bırakılamaz")
    @Size(max = 255)
    private String password;

    @Size(max = 255)
    @Column(name = "bio")
    private String bio;

    @Column(name = "created_at" , nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;


    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    //Relationship

    @OneToMany(mappedBy = "user" , fetch = FetchType.LAZY)
    private List<Tweet> tweets = new ArrayList<>();

    @OneToMany(mappedBy = "user" , fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "user" , fetch = FetchType.LAZY)
    private Set<Like> likes = new HashSet<>();

    @OneToMany(mappedBy = "user" , fetch = FetchType.LAZY)
    private Set<Retweet> retweets = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles" ,
                schema = "twitter" ,
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new  HashSet<>();

    // --------Helper methods--------

    public void addRole(Role role){
        if(role != null){
            this.roles.add(role);
        }
    }

    public void removeRole(Role role){
        if(role != null){
            this.roles.remove(role);
        }
    }

    public void addTweet(Tweet tweet){
        if (tweet != null) {
            this.tweets.add(tweet);
            tweet.setUser(this);
        }
    }

    public void removeTweet(Tweet tweet){
        if (tweet != null){
            this.tweets.remove(tweet);
            tweet.setUser(null);
        }
    }

    public void addComment(Comment comment){
        if (comment != null) {
            this.comments.add(comment);
            comment.setUser(this);
        }
    }

    public void removeComment(Comment comment){
        if (comment != null) {
            this.comments.remove(comment);
            comment.setUser(null);
        }
    }

    public void addLike(Like like){
        if (like != null){
            this.likes.add(like);
            like.setUser(this);
        }
    }

    public void removeLike(Like like){
        if (like != null){
            this.likes.remove(like);
            like.setUser(null);
        }
    }

    public void addRetweet(Retweet retweet){
        if (retweet != null) {
            this.retweets.add(retweet);
            retweet.setUser(this);
        }
    }

    public void removeRetweet(Retweet retweet){
        if (retweet != null) {
            this.retweets.remove(retweet);
            retweet.setUser(null);
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
