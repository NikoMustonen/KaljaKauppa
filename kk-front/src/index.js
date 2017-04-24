import React from 'react';
import ReactDOM from 'react-dom';
import { BrowserRouter as Router, Route } from 'react-router-dom';
import './index.css';
import BeerContainer from './Components/BeerList/BeerContainer';
import ViewBeer from './Components/ViewBeer/ViewBeer';

ReactDOM.render((
  <Router>
    <div>
      <Route exact path="/" component={BeerContainer}/>
      <Route path="/ViewBeer/:id" component={ViewBeer}/>
    </div>
  </Router>
  ),document.getElementById('root')
);
