import React, { Component } from 'react'
import { Link } from 'react-router-dom'
import { PropTypes } from 'prop-types'
import ImageLoader from './ImageLoader'
import { capitalizeFirstLetter } from '../Utils'

export default class Beers extends Component {

  render() {
    return (
      <div className="BeerList">
        {this.props.beers.map((beer, i) => (
          <div className="BeerNode" key={i}>
            <h3>{beer.name}</h3>
            <div className="beerimage">
              <ImageLoader source={beer._links.image.href} altText={beer.name}/>
              <div className="priceBase">
                {beer.pricePerLitre &&
                  <span>{beer.price}&nbsp;€<br/>{parseFloat(beer.pricePerLitre) + " €/l"}</span>}
                {!beer.pricePerLitre &&
                  <span>{beer.price}&nbsp;€</span>}
              </div>
            </div>
            <h4>{capitalizeFirstLetter(beer.beerType.name)}&nbsp;
            |&nbsp;{capitalizeFirstLetter(beer.country.name)}</h4>
            <p>{beer.description}</p>
            <p>{beer.manufacturer.name}</p>
            <Link to={"/ViewBeer/"+beer.id}>Lisätietoja</Link>
          </div>
        ))}
      </div>
    )
  }
}

Beers.propTypes = {
  beers: PropTypes.array.isRequired
}
