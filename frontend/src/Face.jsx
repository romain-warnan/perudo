import React from 'react';
import PropTypes from 'prop-types';

class Face extends React.Component {
  static propTypes = {
    value: PropTypes.number,
    color: PropTypes.oneOf(['BLUE', 'YELLOW', 'ORANGE', 'PURPLE', 'GREEN', 'RED']).isRequired,
    shaded: PropTypes.bool,
  }

  render() {
    const {shaded, color, value} = this.props
    const letter = shaded ? 's' : color.substring(0, 1).toLowerCase()
    const imageName = value + letter
    const imagePath = require(`./img/faces/${imageName}.png`)
    return <img src={imagePath} />
  }
}

export default Face;
