import React, { Component } from 'react'
import './App.css'
import Face from './components/Face.jsx'
import Draw from './components/Draw.jsx'
import Player from './components/Player'

class App extends Component {
  state = {}

  render() {
    return (
      <div className="App">
        <Player/>
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

export default App
