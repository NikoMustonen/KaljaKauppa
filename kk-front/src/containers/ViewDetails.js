import React, { Component } from 'react'
import { connect } from 'react-redux'
import { fetchDetailsIfNeeded, fetchReviewsIfNeeded } from '../actions'
import Details from '../components/Details'
import Reviews from '../components/Reviews'
import { PropTypes } from 'prop-types'

class ViewDetails extends Component {
  componentDidMount() {
    const { dispatch, selectedBeerId } = this.props
    dispatch(fetchDetailsIfNeeded(selectedBeerId))
    dispatch(fetchReviewsIfNeeded(selectedBeerId))
  }

  componentDidUpdate(prevProps) {
    if (true) {
      const { dispatch, selectedBeerId } = this.props
      dispatch(fetchDetailsIfNeeded(selectedBeerId))
    }
  }

  render() {
    const { details, reviews, fetchingDetails, fetchingReviews, lastUpdated } = this.props
    let rr = (reviews === undefined) ? [] : reviews
    return (
      <div>
        <p>
        {lastUpdated &&
          <span>
            Last updated at {new Date(lastUpdated).toLocaleTimeString()}.
            {' '}
          </span>
        }
        {fetchingDetails && <span>Loading...</span>}
        </p>
        {!fetchingDetails &&
          <Details details={details} />
        }
        {!fetchingReviews &&
          <Reviews reviews={rr} />}
      </div>
    )
  }
}

ViewDetails.propTypes = {
  selectedBeerId: PropTypes.string.isRequired,
  fetchingDetails: PropTypes.bool.isRequired,
  lastUpdated: PropTypes.number,
  dispatch: PropTypes.func.isRequired
}

function mapStateToProps(state, ownProps) {
  const selectedBeerId = ownProps.match.params.id
  const { beerDetailsById, beerReviewsById } = state

  const {
    fetchingDetails,
    details } = beerDetailsById[selectedBeerId] ||
    {
      fetchingDetails: true,
      details: []
    }

  const {
    fetchingReviews,
    reviews } = beerReviewsById[selectedBeerId]
    || {
      fetchingReviews: true,
      reviews: []
    }

  return {
    selectedBeerId,
    fetchingDetails,
    details,
    fetchingReviews,
    reviews
  }
}

export default connect(mapStateToProps)(ViewDetails)
