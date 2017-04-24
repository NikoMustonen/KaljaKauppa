import React, {Component} from 'react';

class SearchBar extends Component {
  render() {
    return (
      <form>
        <label>
          Search:
          <input type="text" value={this.props.value} onChange={this.props.onChange}/>
        </label>
      </form>
    )
  }
}

export default SearchBar;
