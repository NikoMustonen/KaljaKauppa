package fi.tamk.beerbros.kaljakauppa.components.review;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fi.tamk.beerbros.kaljakauppa.components.beer.Beer;
import fi.tamk.beerbros.kaljakauppa.components.user.User;
import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.*;

@Entity(name = "review")
@Table(uniqueConstraints = 
        @UniqueConstraint(columnNames = {"user_id", "beer_id"}))
public class Review implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, unique = true)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, updatable = false)
    private User user;
    
    @ManyToOne
    @JoinColumn(name = "beer_id", referencedColumnName = "id", nullable = false, updatable = false)
    private Beer beer;
    
    @Column(name = "rating", nullable = false)
    private short rating;
    
    @Column(name = "title", nullable = false)
    private String title;
    
    @Column(name = "text", nullable = false)
    private String text;
    
    @Column(name = "time_added")
    private Timestamp timeAdded;
    
    public Review() {}
    
    public Review(Long id) {this.id = id;}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(Integer userId) {
        this.user = new User(userId);
    }

    public Beer getBeer() {
        return beer;
    }

    public void setBeer(Integer beerId) {
        this.beer = new Beer(beerId);
    }

    public short getRating() {
        return rating;
    }

    public void setRating(short rating) {
        if(rating < 1) rating = 1;
        if(rating > 5) rating = 5;
        
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if(title.length() > 100) title = title.substring(0, 100);
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        if(text.length() > 255) text = text.substring(0, 255);
        this.text = text;
    }

    public Timestamp getTimeAdded() {
        return timeAdded;
    }

    public void setTimeAdded(Timestamp timeAdded) {
        this.timeAdded = timeAdded;
    }
    
    @JsonIgnore
    public void setBeerNull() {
        this.beer = null;
    }
}