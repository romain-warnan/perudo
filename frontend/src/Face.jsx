import React from 'react';
import PropTypes from 'prop-types';

class Face extends React.Component {
  static propTypes = {
    value: PropTypes.number,
    color : PropTypes.oneOf(['BLUE', 'YELLOW', 'ORANGE', 'PURPLE', 'GREEN', 'RED']).isRequired,
  }

  render() {
    const imageName = this.props.value + this.props.color.substring(0, 1).toLowerCase()
    const imagePath = require(`./img/faces/${imageName}.png`)
    return <img src={imagePath} />
  }
}

export default Face;
