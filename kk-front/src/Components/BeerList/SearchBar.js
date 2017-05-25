import React, {Component} from 'react';

class SearchBar extends Component {
  render() {
    return (
      <div className="SearchBarContainer">
        <div className="SearchBarContent">
          <form>
              <label htmlFor="filter">Haku:</label>
              <input
                  name="filter"
                  type="text"
                  value={this.props.value}
                  onChange={this.props.onChange}/>
          </form>
        </div>
      </div>
    )
  }
}

export default SearchBar;
