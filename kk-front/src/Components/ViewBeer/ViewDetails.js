import React, {Component} from 'react';

class ViewDetails extends Component {
  render() {
    let data = this.props.data,
      updated = {},
      price;

    console.log(data);
    updated.name = data.name;
    updated.description = data.description;

    updated.imgUrl = "/images/" + data.imgUrl.match(/^\/\/.*([0-9]{6}).*product.jpg$/)[1] + ".jpg";
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
      <div>
        <div>
          <img src={updated.imgUrl} alt={updated.name}/>
        </div>
        <h3>{updated.name}</h3>
        <div>{updated.price}</div>
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
        <h5>{JSON.stringify(this.props.reviews)}</h5>
      </div>
    );
  }
}

export default ViewDetails;
