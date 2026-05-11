package com.example.twitter_backend.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "tweets" , schema = "twitter")
public class Tweet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Size(max = 280)
    @NotBlank(message = "Tweet içeriği boş olamaz")
    private String content;

    @Column(nullable = false , name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    // ----RELATİONSHİPS----
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id" , nullable = false)
    private User user;

    @OneToMany(mappedBy = "tweet" , cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "tweet" , cascade = CascadeType.ALL)
    private Set<Retweet> retweets = new HashSet<>();

    @OneToMany(mappedBy = "tweet" , cascade = CascadeType.ALL)
    private Set<Like> likes = new HashSet<>();


    // ----Helper Methods----

    public void addComment(Comment comment){

        if(comment == null) {
            return;
        }
        if (!comments.contains(comment)){
            comments.add(comment);
            comment.setTweet(this);
        }
    }

    public void removeComment(Comment comment){
        if (comment != null) {
            this.comments.remove(comment);
            comment.setTweet(null);
        }
    }

 }


