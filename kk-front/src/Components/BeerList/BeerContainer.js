import React, {Component} from 'react';
import BeerList from './BeerList';
import SearchBar from './SearchBar';


class BeerContainer extends Component {
  constructor(props) {
    super(props);
    var host = (!process.env.NODE_ENV || process.env.NODE_ENV === 'development')
      ? 'http://localhost:8888'
      : window.location.origin;

    this.state = {
      'searchLoaded' : false,
      'doneLoading': false,
      'searchBar': null,
      'searchText': "ruohoinen",
      'hostname': host
    };

    this.handleFilterChange = this.handleFilterChange.bind(this);
    this.fetchData = this.fetchData.bind(this);
  };

  componentWillMount() {
    this.fetchData();
  }

  fetchData() {
    var that = this;

    fetch(this.state.hostname + '/kaljakauppa/beers', { method: 'GET'})
    .then(function(response) {
      return response.json();
    }).then(function(myJSON) {
      that.setState({'doneLoading': true, 'beerlist': myJSON._embedded.beers });
    });
  }

  handleFilterChange(event) {
    this.setState({'searchText': event.target.value });
  }

  render() {
    return (
      <div>
        <SearchBar value={this.state.searchText} onChange={this.handleFilterChange}/>
        { this.state.doneLoading && <BeerList data={this.state.beerlist} filter={this.state.searchText}/> }
      </div>
    );
  }
}

export default BeerContainer;
