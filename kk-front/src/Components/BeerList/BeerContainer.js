import React, {Component} from 'react';
import BeerList from './BeerList';
import SearchBar from './SearchBar';
import './BeerContainer.css';

class BeerContainer extends Component {
  constructor(props) {
    super(props);
    var host = (!process.env.NODE_ENV || process.env.NODE_ENV === 'development')
      ? 'http://localhost:8888'
      : window.location.origin;

    this.state = {
      'doneLoading': false,
      'searchText': "",
      'hostname': host
    };

    this.handleSearchChange = this.handleSearchChange.bind(this);
    this.fetchData = this.fetchData.bind(this);
    this.handleModify = this.handleModify.bind(this);
    this.fetchData();
  };

  fetchData() {
    var that = this;

    fetch(this.state.hostname + '/kaljakauppa/beers', { method: 'GET'})
    .then(function(response) {
      return response.json();
    }).then(function(myJSON) {
      that.setState({'doneLoading': true, 'data': myJSON._embedded.beers});
    });
  }

  handleSearchChange(event) {
    this.setState({'searchText': event.target.value});
  }

  handleModify(id) {
    var data = this.state.data;

    data = data.filter((el) => {
      return el.id !== id;
    })

    this.setState({'data': data});
  }

  render() {
    if (this.state.doneLoading) {
      return (
        <div className="AppWrapper">
          <SearchBar value={this.state.searchText} onChange={this.handleSearchChange}/>
          <BeerList data={this.state.data} filter={this.state.searchText} modify={this.handleModify}/>
        </div>
      );
    }
    return <h1>Loading...</h1>;
  }
}

export default BeerContainer;
