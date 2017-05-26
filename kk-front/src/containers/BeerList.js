import React, { Component } from 'react'
import { connect } from 'react-redux'
import { selectBeerType, fetchBeersIfNeeded, invalidateBeerType } from '../actions'
import Picker from '../components/Picker'
import Beers from '../components/Beers'
import { PropTypes } from 'prop-types'

class BeerList extends Component {
  constructor(props) {
    super(props)
    this.handleChange = this.handleChange.bind(this)
    this.handleRefreshClick = this.handleRefreshClick.bind(this)
  }

  componentDidMount() {
    const { dispatch, selectedBeerType } = this.props
    dispatch(fetchBeersIfNeeded(selectedBeerType))
  }

  componentDidUpdate(prevProps) {
    if (this.props.selectedBeerType !== prevProps.selectedBeerType) {
      const { dispatch, selectedBeerType } = this.props
      dispatch(fetchBeersIfNeeded(selectedBeerType))
    }
  }

  handleChange(nextBeerType) {
    this.props.dispatch(selectBeerType(nextBeerType))
    this.props.dispatch(fetchBeersIfNeeded(nextBeerType))
  }

  handleRefreshClick(e) {
    e.preventDefault()

    const { dispatch, selectedBeerType } = this.props
    dispatch(invalidateBeerType(selectedBeerType))
    dispatch(fetchBeersIfNeeded(selectedBeerType))
  }

  render() {
    const { selectedBeerType, beers, isFetching, lastUpdated } = this.props
    return (
      <div>
        <Picker value={selectedBeerType}
                onChange={this.handleChange}
                options={[ 'ale', 'erikoisuus', 'lager', 'pils',
                'stout & porter', 'tumma lager', 'vahva lager', 'vehnäolut']} />
        <p>
          {lastUpdated &&
            <span>
              Last updated at {new Date(lastUpdated).toLocaleTimeString()}.
              {' '}
            </span>
          }
          {!isFetching &&
            <a href='#b'
               onClick={this.handleRefreshClick}>
              Refresh
            </a>
          }
        </p>
        {isFetching && beers.length === 0 &&
          <h2>Loading...</h2>
        }
        {!isFetching && beers.length === 0 &&
          <h2>Empty.</h2>
        }
        {beers.length > 0 &&
          <div style={{ opacity: isFetching ? 0.5 : 1 }}>
            <Beers beers={beers} />
          </div>
        }
      </div>
    )
  }
}

BeerList.propTypes = {
  selectedBeerType: PropTypes.string.isRequired,
  beers: PropTypes.array.isRequired,
  isFetching: PropTypes.bool.isRequired,
  lastUpdated: PropTypes.number,
  dispatch: PropTypes.func.isRequired
}

function mapStateToProps(state) {
  const { selectedBeerType, beersByBeerType } = state
  const {
    isFetching,
    lastUpdated,
    items: beers
  } = beersByBeerType[selectedBeerType] || {
    isFetching: true,
    items: []
  }

  return {
    selectedBeerType,
    beers,
    isFetching,
    lastUpdated
  }
}

export default connect(mapStateToProps)(BeerList)
