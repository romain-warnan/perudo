import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';
import Face from './Face.jsx';
import Draw from './Draw.jsx';

class App extends Component {
  state = {}

  componentDidMount() {
    // this.hello()
  }
/*
  hello = () => {
    fetch('/api/player?name=Romain')
    .then(response => response.json())
    .then(game => console.log(game))
    .then(game => {
      this.setState({game})
    })
  }
*/
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
        <Face value="6" color="RED" shaded />
        <Face value="6" color="PURPLE" />
        <Face value="6" color="GREEN" />
        <Face value="6" color="YELLOW" active="false" />
        <Face value="6" color="BLUE" active="false" />
        <Face value="6" color="ORANGE" active="false" />
        <Draw faces={[1, 2, 3, 4, 5, 6]} color='RED' />
        <Draw faces={[1, 2, 3, 4]} requestedValue={1} color='RED' />
      </div>
    );
  }
}

export default App;
