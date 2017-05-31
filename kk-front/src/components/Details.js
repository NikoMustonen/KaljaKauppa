import React, { Component } from 'react'
import ImageLoader from './ImageLoader'
import { capitalizeFirstLetter } from '../Utils'

export default class Details extends Component {
  render() {
    let beer = this.props.details
    return (
      <div className="DetailContainer">
        <div className="DetailImage">
          <ImageLoader source={beer._links.image.href} altText={beer.name}/>
          <div className="priceBase">
            {beer.pricePerLitre &&
              <span>{beer.price}&nbsp;€<br/>{parseFloat(beer.pricePerLitre) + " €/l"}</span>}
            {!beer.pricePerLitre &&
              <span>{beer.price}&nbsp;€</span>}
          </div>
        </div>
        <div className="Details">
          <h1>{beer.name}</h1>
          <h2>{capitalizeFirstLetter(beer.beerType.name)}&nbsp;
          |&nbsp;{capitalizeFirstLetter(beer.country.name)}</h2>
          <p>{beer.description}</p>
          <p>Pakkaus: {beer.packageType}</p>
          <p>PakkausKoko: {beer.volume}</p>
          <p>Oluttyyppi: {beer.beerType.name}</p>
          <p>Valmistusmaa: {beer.country.name}</p>
          <p>Kuvaus: {beer.description}</p>
          <p>Valmistaja: {beer.manufacturer.name}</p>
          <p>Plato: {beer.platoScale} °</p>
          <p>IBU: {beer.ibuScale}</p>
          <p>EBC: {beer.ebc}</p>
          <p>Lisätty: {beer.timeAdded}</p>
        </div>
      </div>
    )
  }
}
