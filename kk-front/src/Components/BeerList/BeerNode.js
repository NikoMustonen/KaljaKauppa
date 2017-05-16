import React, {Component} from 'react';
import { Link } from 'react-router-dom';
import {mark} from '../Utils';
import './BeerNode.css';

class BeerNode extends Component {
  render() {
    let test = this.props.filter,
      data = this.props.data,
      updated = {},
      price;

    updated.viewlink = '/ViewBeer/' + data.id;

    updated.name = data.name;
    updated.description = data.description;

    updated.imgUrl = data.imgUrl;
    updated.timeAdded = (new Date(data.timeAdded)).toString();
    updated.beerType = data.beerType.name;
    updated.country = data.country.name;
    updated.manufacturer = data.manufacturer.name;
    price = data.price + " €";

    if (data.pricePerLitre !== null && data.pricePerLitre !== undefined) {
      updated.price = <span>{price}<br/>{parseFloat(data.pricePerLitre) + " €/l"}</span>;
    } else {
      updated.price = <span>{price}</span>;
    }

    return (
      <div className="beernode">
        <div className="beerimage">
          <img src={updated.imgUrl} alt={updated.name}/>
          <div className="priceBase">{updated.price}</div>
        </div>
        <h3>{mark(updated.name, test)}</h3>
        <h4>{mark(updated.beerType, test)}
          | {mark(updated.country, test)}</h4>
        <p>{mark(updated.description, test)}</p>
        <p>{mark(updated.manufacturer, test)}</p>
        <Link to={updated.viewlink}>Lisätietoja</Link>
      </div>
    )
  }
}

export default BeerNode;
