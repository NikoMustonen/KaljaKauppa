import React, {Component} from 'react';
import ImageLoader from '../ImageLoader';
import ReviewList from '../Reviews/ReviewList';
import ReviewForm from '../Reviews/ReviewForm';
import './ViewDetails.css';


class ViewDetails extends Component {
  render() {
    let data = this.props.data,
      updated = {},
      price;

    console.log(data);

    updated.name = data.name;
    updated.description = data.description;

    updated.imgUrl = JSON.stringify(data._links.image.href);
    console.log(updated.imgUrl);

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
      <div className="DetailContainer">
        <div className="DetailImage">
          <ImageLoader source={updated.imgUrl} altText={updated.name} />
          <div className="priceBase">{updated.price}</div>
        </div>
        <h1>{updated.name}</h1>

        <p>Pakkaus: {data.packageType}</p>
        <p>PakkausKoko: {data.volume}</p>
        <p>Oluttyyppi {updated.beerType}</p>
        <p>Valmistusmaa: {updated.country}</p>
        <p>Kuvaus: {updated.description}</p>
        <p>Valmistaja: {updated.manufacturer}</p>
        <p>Plato: {data.platoScale} °</p>
        <p>IBU: {data.ibuScale}</p>
        <p>EBC: {data.ebc}</p>
        <p>Lisätty: {updated.timeAdded}</p>
        <br/>
        <div className="ReviewContainer">
          <ReviewForm/>
          <ReviewList reviews={this.props.reviews} />
        </div>
      </div>
    );
  }
}

export default ViewDetails;
