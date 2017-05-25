import React, {Component} from 'react';
import ReviewNode from './ReviewNode';

class ReviewList extends Component {
  constructor(props) {
    super(props);

    let loadedReviews = [];
    let reviews = [];
    let reviewsum = 0;
    let reviewsAvg;

    if (this.props.reviews.reviews !== undefined) {
      reviews = this.props.reviews.reviews.slice();
    }

    for (let review of reviews) {
      reviewsum += review.rating;
      loadedReviews.push(
        <ReviewNode key={review.id} data={review} />
      )
    }

    if (reviews.length === 0) {
      reviewsAvg = <p>Ei yhtään arvostelua vielä</p>;
    } else {
      reviewsAvg = <p>Käyttäjäarvosteluiden keskiarvo: {reviewsum/reviews.length}</p>;
    }

    this.state = {
      'reviews': loadedReviews,
      'average': reviewsAvg
    };
  }

  render() {
    return (
      <div>
        {this.state.average}
        <div>{this.state.reviews}</div>
      </div>
  )
  }
}

export default ReviewList;
