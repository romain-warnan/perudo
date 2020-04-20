import React from 'react'
import Select from 'react-select'
import colors from '../colors'
import './Player.scss'

class UnregisteredPlayer extends React.Component {

  render() {
    
    const options = colors.map(color => ({
      value: color,
      label: <img src={require(`../img/faces/1${color.substring(0, 1).toLowerCase()}.png`)} alt={color} />
    }))
    const defaultValue = {
      value: undefined,
      label: <img src={require('../img/faces/1s.png')} alt='NONE' />
    }
    return (
      <div className='unregistered-player'>
        <input type='text' className='player-name' />
        <Select defaultValue={defaultValue} options={options} className='select-color' />
      </div>
    )
  }
}

export default UnregisteredPlayer