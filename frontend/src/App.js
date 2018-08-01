import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';

class App extends Component {
  state = {}

  componentDidMount() {
    this.hello()
  }

  hello = () => {
    fetch('/api/player?name=Romain')
    .then(response => response.json())
    .then(game => console.log(game))
    .then(game => {
      this.setState({game})
    })
  }

  render() {
    return (
      <div className="App">
        <header className="App-header">
          <img src={logo} className="App-logo" alt="logo" />
          <h1 className="App-title">Welcome to React</h1>
        </header>
        <p className="App-intro">
          To get started, edit <code>src/App.js</code> and save to reload.
        </p>
      </div>
    );
  }
}

export default App;
