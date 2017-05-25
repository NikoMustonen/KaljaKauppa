import React, {Component} from 'react';

class ReviewNode extends Component {
  render() {
    let data = this.props.data;

    return (
      <div className="ReviewNode">
        <h3>{data.user.username} - {data.title}</h3>
        <h4>{data.rating} tähteä</h4>
        <p>{data.text}</p>
      </div>
  )
  }
}

export default ReviewNode;
