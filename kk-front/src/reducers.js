import { combineReducers } from 'redux'
import {
  INVALIDATE_BEERS, REQUEST_BEERS, RECEIVE_BEERS, SELECT_BEERTYPE,
  REQUEST_DETAILS, RECEIVE_DETAILS, INVALIDATE_DETAILS,
  REQUEST_REVIEWS, RECEIVE_REVIEWS, INVALIDATE_REVIEWS
} from './actions'

function selectedBeerType(state = 'erikoisuus', action) {
  switch (action.type) {
  case SELECT_BEERTYPE:
    return action.beerType
  default:
    return state
  }
}

function beers(state = {
  isFetching: false,
  didInvalidate: false,
  items: []
}, action) {
  switch (action.type) {
    case INVALIDATE_BEERS:
      return Object.assign({}, state, {
        didInvalidate: true
      })
    case REQUEST_BEERS:
      return Object.assign({}, state, {
        isFetching: true,
        didInvalidate: false
      })
    case RECEIVE_BEERS:
      return Object.assign({}, state, {
        isFetching: false,
        didInvalidate: false,
        items: action.beers,
        lastUpdated: action.receivedAt
      })
    default:
      return state
  }
}

function details(state = {
  fetchingDetails: false,
  detailsInvalidate: false,
  details: []
}, action) {
  switch (action.type) {
    case REQUEST_DETAILS:
      return Object.assign({}, state, {
        fetchingDetails: true
      })
    case RECEIVE_DETAILS:
      return Object.assign({}, state, {
        fetchingDetails: false,
        details: action.details,
        lastUpdated: action.receivedAt
      })
    default:
      return state;
  }
}

function reviews(state = {
  fetchingReviews: false,
  reviewsInvalidate: false,
  reviews: []
}, action) {
  switch (action.type) {
    case REQUEST_REVIEWS:
      return Object.assign({}, state, {
        fetchingReviews: true
      })
    case RECEIVE_REVIEWS:
      return Object.assign({}, state, {
        fetchingReviews: false,
        reviews: action.reviews,
        lastUpdated: action.receivedAt
      })
    default:
      return state;
  }
}

function beersByBeerType(state = { }, action) {
  switch (action.type) {
    case INVALIDATE_BEERS:
    case RECEIVE_BEERS:
    case REQUEST_BEERS:
      return Object.assign({}, state, {
        [action.beerType]: beers(state[action.beerType], action)
      })
    default:
      return state
  }
}

function beerDetailsById(state = {}, action) {
  switch (action.type) {
    case INVALIDATE_DETAILS:
    case RECEIVE_DETAILS:
    case REQUEST_DETAILS:
      return Object.assign({}, state, {
        [action.beerId]: details(state[action.beerId], action)
      })
    default:
      return state
  }
}

function beerReviewsById(state = {}, action) {
  switch (action.type) {
    case INVALIDATE_REVIEWS:
    case REQUEST_REVIEWS:
    case RECEIVE_REVIEWS:
      return Object.assign({}, state, {
        [action.beerId]: reviews(state[action.beerId], action)
      })
    default:
      return state
  }
}

const rootReducer = combineReducers({
  beersByBeerType,
  selectedBeerType,
  beerDetailsById,
  beerReviewsById
})

export default rootReducer
