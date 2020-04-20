import React from 'react'
import PropTypes from 'prop-types'
import Face from './Face.jsx'
import colors from '../colors'

class Draw extends React.Component {
  static propTypes = {
    faces: PropTypes.arrayOf(PropTypes.number).isRequired,
    color: PropTypes.oneOf(colors).isRequired,
    requestedValue: PropTypes.number,
    palifico: PropTypes.bool,
  }

  isShaded(value) {
    const {requestedValue, palifico} = this.props
    const palificoCase = requestedValue && requestedValue !== value
    const regularCase = requestedValue && palificoCase && value !== 1
    return (palifico && palificoCase) || (!palifico && regularCase)
  }

  render() {
    const {faces, color} = this.props
    return (
    <p className='draw'>
      {
        faces.map((value, index) => <Face key={index} value={value} color={color} shaded={this.isShaded(value)} />)
      }
    </p>
    )
  }
}

export default Draw;
