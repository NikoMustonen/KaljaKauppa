(function() {
    const filterItems = ['name', 'description', 'country.name', 'manufacturer.name', 'beerType.name'];

    function flatten(obj) {
        var root = {};
        (function tree(obj, index) {
            var suffix = toString.call(obj) == "[object Array]" ? "]" : "";
            for (var key in obj) {
                if (!obj.hasOwnProperty(key)) continue;
                root[index + key + suffix] = obj[key];
                if (toString.call(obj[key]) == "[object Array]") tree(obj[key], index + key + suffix + "[");
                if (toString.call(obj[key]) == "[object Object]") tree(obj[key], index + key + suffix + ".");
            }
        })(obj, "");
        return root;
    }

    function capitalizeFirstLetter(string) {
        return string.charAt(0).toUpperCase() + string.slice(1);
    }

    class BeerNode extends React.Component {
        constructor(props) {
            super(props);

            this.mark = this.mark.bind(this);
            this.format = this.format.bind(this);
        }

        format() {

        }

        mark(str, test) {
            var bits = [], node = [], i, visible = false;

            if (test !== "" && test !== null && test !== undefined) {
                bits = str.toString().split(test);

                if (bits.length === 1) {
                    if (bits[0] === test) {
                        node.push(<mark>{test}</mark>);
                    } else {
                        node.push(bits);
                    }
                } else {
                    for (i = 0; i < bits.length - 1; i++) {
                        node.push(bits[i]);
                        node.push(<mark>{test}</mark>);
                    }
                    node.push(bits[bits.length - 1]);
                }

                return node;
            } else {
                return str;
            }
        }

        render() {
            let test = this.props.filter,
                data = this.props.data,
                updated = {},
                price;

            updated.name = data.name;
            updated.description = data.description;

            updated.imgUrl = "images/" + data.imgUrl.match(/^\/\/.*([0-9]{6}).*product.jpg$/)[1] + ".jpg";
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
                <div className="beernode">
                    <div className="beerimage">
                        <img src={updated.imgUrl}/>
                        <div className="priceBase">{updated.price}</div>
                    </div>
                    <h3>{this.mark(updated.name, test)}</h3>
                    <h4>{this.mark(updated.beerType, test)} | {this.mark(updated.country, test)}</h4>
                    <p>{this.mark(updated.description, test)}</p>
                    <p>{this.mark(updated.manufacturer, test)}</p>
                </div>
                )
            }
        }


    class BeerList extends React.Component {
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
                        data={beer}
                        filter={this.props.filter}
                        />)
                }
            }

            return <div className="beerlist">{listed}</div>;
        }
    }

    class SearchBar extends React.Component {
        constructor(props) { super(props); }

        render() {
            return (
                <form>
                    <label>
                      Search:
                        <input
                            type="text"
                            value={this.props.value}
                            onChange={this.props.onChange} />
                    </label>
                </form>
            )
        }
    }

    class MyApp extends React.Component {
        constructor(props) {
            super(props);

            this.state = {'doneLoading': false, 'searchText': ""};

            this.handleSearchChange = this.handleSearchChange.bind(this);
            this.fetchData = this.fetchData.bind(this);
            this.handleModify = this.handleModify.bind(this);
            this.fetchData();
        };

        fetchData() {
            var that = this;

            fetch('http://konelandia.bounceme.net/kaljakauppa/beers', {
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    method: 'GET'
            })
            .then(function(response) {
                return response.json();
            })
            .then(function(myJSON) {
                that.setState({'doneLoading': true, 'data': myJSON._embedded.beers });
            });
        }

        handleSearchChange(event) {
            this.setState({'searchText': event.target.value });
        }

        handleModify(id) {
            var data = this.state.data;

            data = data.filter((el) => {
                return el.id !== id;
            })

            this.setState({'data': data});
        }

        render() {
            if(this.state.doneLoading) {
                return (
                    <div>
                        <h1>Kaljakauppa</h1>
                        <SearchBar value={this.state.searchText} onChange={this.handleSearchChange}/>
                        <BeerList data={this.state.data} filter={this.state.searchText} modify={this.handleModify}/>
                    </div>
                )
            }
            return <h1>Loading...</h1>;
        }
    }


    ReactDOM.render(
        <MyApp/>,
        document.getElementById('root')
    );
}());
