import React from 'react';
import { BrowserRouter as Router, Route } from 'react-router-dom';
import {HeaderContainer as Header}  from './Header/HeaderContainer';
import BeerContainer from './BeerList/BeerContainer';
import ViewBeer from './ViewBeer/ViewBeer';
import './App.css';


const App = () => (
  <div>
    <Header className="Header"/>
    <Router>
      <div className="AppWrapper">
        <Route exact path="/" component={BeerContainer}/>
        <Route path="/ViewBeer/:id" component={ViewBeer}/>
      </div>
    </Router>
  </div>
)

export default App;
