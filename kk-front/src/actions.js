import fetch from 'isomorphic-fetch'

export const REQUEST_BEERS = 'REQUEST_BEERS'
export const RECEIVE_BEERS = 'RECEIVE_BEERS'
export const SELECT_BEERTYPE = 'SELECT_BEERTYPE'
export const INVALIDATE_BEERS = 'INVALIDATE_BEERS'

export const REQUEST_DETAILS = 'REQUEST_DETAILS'
export const RECEIVE_DETAILS = 'RECEIVE_DETAILS'
export const INVALIDATE_DETAILS = 'INVALIDATE_DETAILS'

export const REQUEST_REVIEWS = 'REQUEST_REVIEWS'
export const RECEIVE_REVIEWS = 'RECEIVE_REVIEWS'
export const INVALIDATE_REVIEWS = 'INVALIDATE_REVIEWS'

const host = (!process.env.NODE_ENV || process.env.NODE_ENV === 'development')
  ? 'http://localhost:8888'
  : window.location.origin;

export function selectBeerType(beerType) {
  return {
    type: SELECT_BEERTYPE,
    beerType
  }
}

export function invalidateBeerType(beerType) {
  return {
    type: INVALIDATE_BEERS,
    beerType
  }
}

function requestBeers(beerType) {
  return {
    type: REQUEST_BEERS,
    beerType
  }
}

function receiveBeers(beerType, json) {
  return {
    type: RECEIVE_BEERS,
    beerType,
    beers: json._embedded.beers,
    receivedAt: Date.now()
  }
}

function fetchBeers(beerType) {
  return dispatch => {
    dispatch(requestBeers(beerType))
    return fetch(host + `/kaljakauppa/beertypes/${beerType}/beers`)
      .then(response => response.json())
      .then(json => dispatch(receiveBeers(beerType, json)))
  }
}

function shouldFetchBeers(state, beerType) {
  const beers = state.beersByBeerType[beerType]
  if (!beers) {
    return true
  } else if (beers.isFetching) {
    return false
  } else {
    return beers.didInvalidate
  }
}

export function fetchBeersIfNeeded(beerType) {
  return (dispatch, getState) => {
    if (shouldFetchBeers(getState(), beerType)) {
      return dispatch(fetchBeers(beerType))
    }
  }
}

function requestDetails(beerId) {
  return {
    type: REQUEST_DETAILS,
    beerId
  }
}

function receiveDetails(beerId, json) {
  return {
    type: RECEIVE_DETAILS,
    beerId,
    details: json,
    receivedAt: Date.now()
  }
}

function requestReviews(beerId) {
  return {
    type: REQUEST_REVIEWS,
    beerId
  }
}

function receiveReviews(beerId, json) {
  return {
    type: RECEIVE_REVIEWS,
    beerId,
    reviews: json,
    receivedAt: Date.now()
  }
}

function fetchDetails(beerId) {
  return dispatch => {
    dispatch(requestDetails(beerId))
    return fetch(host + `/kaljakauppa/beers/${beerId}`)
      .then(response => response.json())
      .then(json => dispatch(receiveDetails(beerId, json)))
  }
}

function fetchReviews(beerId) {
  return dispatch => {
    dispatch(requestReviews(beerId))
    return fetch(host + `/kaljakauppa/beers/${beerId}/reviews`)
      .then(response => response.json())
      .then(json => {
        let temp = (json['_embedded'] === undefined) ? [] : json._embedded.reviews
        dispatch(receiveReviews(beerId, temp ))})
  }
}

function shouldFetchDetails(state, beerId) {
  const beers = state.beerDetailsById[beerId]
  if (!beers) {
    return true
  } else if (beers.fetchingDetails) {
    return false
  } else {
    return beers.didInvalidate
  }
}

function shouldFetchReviews(state, beerId) {
  const beers = state.beerReviewsById[beerId]
  if (!beers) {
    return true
  } else if (beers.fetchingReviews) {
    return false
  } else {
    return beers.didInvalidate
  }
}

export function fetchDetailsIfNeeded(beerId) {
  return (dispatch, getState) => {
    if (shouldFetchDetails(getState(), beerId)) {
      return dispatch(fetchDetails(beerId))
    }
  }
}

export function fetchReviewsIfNeeded(beerId) {
  return (dispatch, getState) => {
    if (shouldFetchReviews(getState(), beerId)) {
      return dispatch(fetchReviews(beerId))
    }
  }
}
