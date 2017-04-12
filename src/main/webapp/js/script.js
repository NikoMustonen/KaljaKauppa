(function() {
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

            class ProductRow extends React.Component {
                constructor(props) {
                    super(props);
                    //this.modifying = this.modifying.bind(this);
                    this.mark = this.mark.bind(this);
                }

                mark(str) {
                    var bits = [], node = [], i;

                    bits = str.toString().split(this.props.filter);

                    if (bits.length === 1) {
                        if (bits[0] === this.props.filter) {
                            node.push(<mark>{this.props.filter}</mark>);
                        } else {
                            node.push(bits);
                        }
                    } else {
                        for (i = 0; i < bits.length - 1; i++) {
                            node.push(bits[i]);
                            node.push(<mark>{this.props.filter}</mark>);
                        }

                        node.push(bits[bits.length - 1]);
                    }

                    return <p>{node}</p>;
                }

                render() {
                    var row = [], imageRegex = /^\/\/.*([0-9]{6}).*product.jpg$/, dateobj, dateRegex = /^[0-9]{10,}$/g;

                    row = this.props.data.map((str) => {

                        if (str === null) {
                            return;
                        }

                        if (typeof str === 'object') {
                            if (str.hasOwnProperty('name') && str.hasOwnProperty('description')) {
                                return <td>{this.mark(str.name)}<br/>{this.mark(str.description)}</td>
                            }
                            return;
                        }

                        if(typeof str === 'string' && str.match(imageRegex) !== null) {
                            return <img src={"images/" + str.match(imageRegex)[1] + ".jpg"}/>;
                        }

                        if (typeof str === 'number' && str.toString().match(dateRegex) !== null) {
                            dateobj = new Date(str);
                            return <td><p>{dateobj.toString()}</p></td>
                        }

                        if (this.props.filter === "") {
                            return <td><p>{str}</p></td>;
                        }

                        return <td><p>{this.mark(str)}</p></td>;
                    });

                    return <tr>{row}</tr>;
                }
            }

            class ProductTable extends React.Component {
                constructor(props) { super(props); }

                render() {
                    var rows = [], test, content = [], hasFilter, data = [], headers = [];

                    test = this.props.filter;
                    data = this.props.data;

                    headers = Object.keys(data[0]).map((str) => {
                        return <th>{str}</th>;
                    });

                    headers.pop();

                    for (let rowData of data) {
                        content = Object.values(rowData);
                        hasFilter = false;

                        for (let columnData of content) {
                            if (typeof columnData === 'string') {
                                if (columnData.toString().search(test) !== -1 ) {
                                    hasFilter = true;
                                }
                            }
                            if (typeof columnData === 'object' && columnData !== null) {
                                if (columnData.hasOwnProperty('name') && columnData.hasOwnProperty('description')) {
                                    if (columnData.name.toString().search(test) !== -1 || columnData.description.toString().search(test) !== -1) {
                                        hasFilter = true;
                                    }
                                }
                            }
                        }

                        if (hasFilter) {
                            rows.push(<ProductRow data={content} filter={test} modify={this.props.modify}/>);
                        }
                    }

                    return <table class="shoptable"><thead><tr>{headers}</tr></thead><tbody>{rows}</tbody></table>;
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
                    fetch('http://konelandia.bounceme.net/beers', {
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
                                <ProductTable data={this.state.data} filter={this.state.searchText} modify={this.handleModify}/>
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
