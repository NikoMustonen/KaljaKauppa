import React, {Component} from 'react';
import BeerNode from './BeerNode';

class BeerList extends Component {
    constructor(props) {
        super(props);

        let data = this.props.data, beernodes = [];

        for (let beer of data) {
            let filteritems = [],
                beernode = {};

            filteritems.push(beer.name);
            filteritems.push(beer.description);
            filteritems.push(beer.manufacturer.name);

            beernode.strfilter = filteritems.join('~');

            beernode.data = beer;
            beernodes.push(beernode);
        }

        this.state = {
          'filter': '',
          'beers': beernodes
        };
    }

    render() {
      let visibleNodes = this.state.beers.map((beernode, index) => {
        return (beernode.strfilter.indexOf(this.props.filter) !== -1) ?
          <BeerNode key={index} data={beernode.data} filter={this.props.filter}/> :
          null;
      });

      return <div className="beerlist">{visibleNodes}</div>;
    }
}

export default BeerList;
