import React from 'react';
import PropTypes from 'prop-types';
import Face from './Face.jsx';

class Draw extends React.Component {
  static propTypes = {
    faces: PropTypes.arrayOf(PropTypes.number).isRequired,
    color: PropTypes.oneOf(['BLUE', 'YELLOW', 'ORANGE', 'PURPLE', 'GREEN', 'RED']).isRequired,
    requestedValue: PropTypes.number,
  }

  render() {
    return (
    <p>
      {
        this.props.faces.map((value, index) => <Face key={index} value={value} color={this.props.color} shaded={this.props.requestedValue && this.props.requestedValue !== value} />)
      }
    </p>
    )
  }
}

export default Draw;
