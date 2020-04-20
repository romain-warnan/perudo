import React from 'react'
import PropTypes from 'prop-types'
import ExtraPropTypes from 'react-extra-prop-types'
import RegiteredPlayer from './RegisteredPlayer'
import UnregiteredPlayer from './UnregisteredPlayer'
import colors from '../colors'
import './Player.scss'

class Player extends React.Component {
  static propTypes = {
    id: ExtraPropTypes.uuid,
    name: PropTypes.string,
    color: PropTypes.oneOf(colors),
  }

  render() {
    const {id, name, color} = this.props
    return id ? <RegiteredPlayer id={id} color={color} name={name} /> : <UnregiteredPlayer />
  }
}

export default Player