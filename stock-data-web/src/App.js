import React, {Component} from 'react';
import logo from './logo.svg';
import './App.css';

import {Sidebar} from './sidebar/Sidebar.css';
import {Route, Link} from 'react-router-dom';
import Indexes from './trending/indexes/indexes';
import Analysis from './trending/analysis/analysis';
import About from './about/about';

class App extends Component {
    render() {
        return (
            <div className="all">
                <div className="sidebar">
                    <ul>
                        <li><Link to="/"> About </Link></li>
                        <li><Link to="/indexes"> trending indexes </Link></li>
                        <li><Link to="/analysis"> data analysis </Link></li>
                    </ul>
                </div>
                <div className="page-area">
                    <Route path="/" exact={true} component={About}></Route>
                    <Route path="/indexes" component={Indexes}></Route>
                    <Route path="/analysis" component={Analysis}></Route>
                </div>
            </div>
        );
    }
}

export default App;
