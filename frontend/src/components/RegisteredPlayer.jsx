import React from 'react'
import PropTypes from 'prop-types'
import ExtraPropTypes from 'react-extra-prop-types'
import Face from './Face'
import './Player.scss'
import colors from '../colors'

class RegisteredPlayer extends React.Component {
  static propTypes = {
    id: ExtraPropTypes.uuid,
    name: PropTypes.string,
    color: PropTypes.oneOf(colors),
  }

  render() {
    const {id, name, color} = this.props
    return (
      <div className='registered-player' data-id={id}>
        <Face color={color} />
        <label className='player-name'>{name}</label>
      </div>
    )
  }
}

export default RegisteredPlayer