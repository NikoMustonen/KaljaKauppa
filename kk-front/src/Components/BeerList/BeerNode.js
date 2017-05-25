import React, {Component} from 'react';
import { Link } from 'react-router-dom';
import ImageLoader from '../ImageLoader';
import {mark, capitalizeFirstLetter } from '../Utils';
import './BeerNode.css';

class BeerNode extends Component {
  constructor(props) {
    super(props);

    let data = this.props.data,
      updated = {},
      price,
      image;

    updated.viewlink = '/ViewBeer/' + data.id;

    updated.name = data.name;
    updated.description = data.description;

    updated.imgUrl = JSON.stringify(data._links.image.href);

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

    image = <ImageLoader source={updated.imgUrl} altText={updated.name}/>;

    this.state = {
      'updated': updated,
      'image': image
    }
  }

  render() {
    let test = this.props.filter,
    updated = this.state.updated;

    return (
      <div className="beernode">
        <div className="beerimage">
          {this.state.image}
          <div className="priceBase">{updated.price}</div>
        </div>
        <h3>{mark(updated.name, test)}</h3>
        <h4>{capitalizeFirstLetter(updated.beerType)}&nbsp;
        |&nbsp;{capitalizeFirstLetter(updated.country)}</h4>
        <p>{mark(updated.description, test)}</p>
        <p>{updated.manufacturer}</p>
        <Link to={updated.viewlink}>Lisätietoja</Link>
      </div>
    )
  }
}

export default BeerNode;
