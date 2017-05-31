import React, { Component } from 'react'
import { Provider } from 'react-redux'
import { BrowserRouter as Router, Route } from 'react-router-dom';
import configureStore from '../configureStore'
import BeerList from './BeerList'
import ViewDetails from './ViewDetails'
import Header from '../components/Header'

const store = configureStore()

export default class Root extends Component {
  render() {
    return (
      <div>
      <Header />
      <Provider store={store}>
        <Router>
          <div className="AppWrapper">
            <Route exact path="/" component={BeerList}/>
            <Route path="/ViewBeer/:id" component={ViewDetails}/>
          </div>
        </Router>
      </Provider>
      </div>
    )
  }
}
