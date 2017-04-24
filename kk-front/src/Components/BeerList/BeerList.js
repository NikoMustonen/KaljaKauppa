import React, {Component} from 'react';
import { flatten } from '../Utils';
import BeerNode from './BeerNode';

const filterItems = ['name', 'description', 'country.name', 'manufacturer.name', 'beerType.name'];

class BeerList extends Component {
    constructor(props) {
        super(props);
        this.checkFilter = this.checkFilter.bind(this);
    }

    checkFilter(node) {
        let filter = this.props.filter,
            flat,
            found = false;

        if (filter === "") return true;

        flat = flatten(node);

        for (let key of filterItems) {
            if (flat.hasOwnProperty(key)) {
                if (flat[key].toString().search(filter) !== -1 ) {
                    found = true;
                    break;
                }
            }
        }

        return found;
    }

    render() {
        let data = this.props.data,
            listed = [],
            update;

        listed = [];

        for (let beer of data) {
            update = this.checkFilter(beer);
            if(update) {
                console.log()
                listed.push(<BeerNode
                    key={beer.id}
                    data={beer}
                    filter={this.props.filter}
                    />)
            }
        }

        return <div className="beerlist">{listed}</div>;
    }
}

export default BeerList;
