import React, { Component } from 'react'
import { PropTypes } from 'prop-types'

export default class Reviews extends Component {
  render() {
    return (
      <div className="ReviewList">
        {this.props.reviews.map((review, i) => (
          <div className="ReviewNode" key={i}>
            <h3>{review.user.username} - {review.title}</h3>
            <h4>{review.rating} Tähteä</h4>
            <p>{review.text}</p>
          </div>
        ))}
      </div>
    )
  }
}

Reviews.propTypes = {
  reviews: PropTypes.array.isRequired
}
