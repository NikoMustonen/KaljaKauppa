package fi.tamk.beerbros.kaljakauppa.components.review;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fi.tamk.beerbros.kaljakauppa.components.beer.Beer;
import fi.tamk.beerbros.kaljakauppa.components.user.User;
import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.*;

/**
 * Entity class for review entities.
 *
 * @author Niko Mustonen mustonen.niko@gmail.com
 * @version %I%, %G%
 * @since 1.8
 */
@Entity(name = "review")
@Table(uniqueConstraints
        = @UniqueConstraint(columnNames = {"user_id", "beer_id"}))
public class Review implements Serializable {

    /**
     * Review id number.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, unique = true)
    private Long id;

    /**
     * Reviewing user.
     */
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, updatable = false)
    private User user;

    /**
     * Reviewed beer.
     */
    @ManyToOne
    @JoinColumn(name = "beer_id", referencedColumnName = "id", nullable = false, updatable = false)
    private Beer beer;

    /**
     * Review rating between 1-5.
     */
    @Column(name = "rating", nullable = false)
    private short rating;

    /**
     * Review title String.
     */
    @Column(name = "title", nullable = false)
    private String title;

    /**
     * Review text String.
     */
    @Column(name = "text", nullable = false)
    private String text;

    /**
     * Review adding time.
     */
    @Column(name = "time_added")
    private Timestamp timeAdded;

    /**
     * Constructs empty review entity object.
     */
    public Review() {
    }

    /**
     * Constructs review object with given id.
     * 
     * Is used for fetching reviews from database.
     * 
     * @param id Id number.
     */
    public Review(Long id) {
        this.id = id;
    }

    /**
     * Gets review id number.
     * 
     * @return Id number.
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets review id number.
     * 
     * @param id 
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets reviewing user.
     * 
     * @return User entity object.
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets reviewing user.
     * 
     * @param userId Users id number. 
     */
    public void setUser(Integer userId) {
        this.user = new User(userId);
    }

    /**
     * Gets reviewed beer object.
     * 
     * @return Beer entity object.
     */
    public Beer getBeer() {
        return beer;
    }

    /**
     * Sets beer for the review.
     * 
     * @param beerId Beer entity object.
     */
    public void setBeer(Integer beerId) {
        this.beer = new Beer(beerId);
    }

    /**
     * Gets rating for this beer.
     * 
     * @return Rating value between 1-5.
     */
    public short getRating() {
        return rating;
    }

    /**
     * Sets rating value.
     * 
     * If higher than 5 will be 5 if lower than 1 will be set to one.
     * 
     * @param rating Rating value number.
     */
    public void setRating(short rating) {
        if (rating < 1) {
            rating = 1;
        }
        if (rating > 5) {
            rating = 5;
        }

        this.rating = rating;
    }

    /**
     * Gets review title.
     * 
     * @return Title string.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets review title.
     * 
     * @param title Title String.
     */
    public void setTitle(String title) {
        if (title.length() > 100) {
            title = title.substring(0, 100);
        }
        this.title = title;
    }

    /**
     * Gets review text.
     * 
     * @return Review text as string.
     */
    public String getText() {
        return text;
    }

    /**
     * Sets review text.
     * 
     * If size over 255 characters.... No it wont!!!
     * 
     * @param text Review text as String.
     */
    public void setText(String text) {
        if (text.length() > 255) {
            text = text.substring(0, 255);
        }
        this.text = text;
    }

    /**
     * Gets review adding time.
     * 
     * @return Adding TimeStamp.
     */
    public Timestamp getTimeAdded() {
        return timeAdded;
    }

    /**
     * Sets adding time.
     * 
     * @param timeAdded TimeStamp object.
     */
    public void setTimeAdded(Timestamp timeAdded) {
        this.timeAdded = timeAdded;
    }

    /**
     * Sets beers to be null for this review.
     * 
     * Is used for preventing stackoverflow.
     */
    @JsonIgnore
    public void setBeerNull() {
        this.beer = null;
    }
}
