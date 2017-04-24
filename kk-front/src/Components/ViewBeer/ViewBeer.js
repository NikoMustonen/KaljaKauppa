import React, {Component} from 'react';
import ViewDetails from './ViewDetails';

class ViewBeer extends Component {
  constructor(props) {
    super(props);
    let host = (!process.env.NODE_ENV || process.env.NODE_ENV === 'development')
      ? 'http://localhost:8888'
      : window.location.origin;

    this.state = {
      'loadedData': false,
      'loadedReviews': false,
      'hostname': host
    };

    this.fetchData = this.fetchData.bind(this);
    this.fetchReviews = this.fetchReviews.bind(this);
    this.fetchData();
    this.fetchReviews();
  };

  fetchData() {
    var that = this;

    fetch(this.state.hostname + '/kaljakauppa/beers/' + this.props.match.params.id, { method: 'GET'})
    .then(function(response) {
      return response.json();
    }).then(function(myJSON) {
      that.setState({'loadedData': true, 'data': myJSON});
    });
  }

  fetchReviews() {
    var that = this;

    fetch(this.state.hostname + '/kaljakauppa/beers/' + this.props.match.params.id + '/reviews', { method: 'GET'})
    .then(function(response) {
      return response.json();
    }).then(function(myJSON) {
      console.log(myJSON);
      that.setState({'loadedReviews': true, 'reviews': myJSON});
    });
  }

  render() {
    if (this.state.loadedData && this.state.loadedReviews) {
      return (
        <ViewDetails data={this.state.data} reviews={this.state.reviews}/>
      );
    }
    return <h2>Haetaan olutta {this.props.match.params.id}</h2>;
  }
}

export default ViewBeer;
