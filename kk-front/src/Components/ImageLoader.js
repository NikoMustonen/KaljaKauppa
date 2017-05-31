import React, {Component} from 'react';

class ImageLoader extends Component {

  constructor(props) {
    super(props);

    this.state = {
      'doneLoading': false
    };

    this.fetchData = this.fetchData.bind(this);
    this.fetchData();
  }

  fetchData() {
    let that = this;

    fetch(this.props.source)
    .then(response => {
      if(response.ok) {
        return response.blob();
      }
      throw new Error('Network response was not ok.');
    })
    .then(myBlob => {
      that.setState({
        'doneLoading': true,
        'imageData': myBlob
      });
    })
    .catch(error => {console.log('ERROR: ' + error.message)});
  }

  render() {
    return this.state.doneLoading ?
    (<img src={URL.createObjectURL(this.state.imageData)} alt={this.props.altText} />)
    : (<span>{this.props.altText}</span>);
  }
}

export default ImageLoader;
