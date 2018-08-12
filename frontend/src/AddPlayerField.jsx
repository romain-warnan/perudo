import React from 'react'
import { connect } from 'react-redux'
import PropTypes from 'prop-types'
import { addPlayer } from './actions'

class AddPlayerField extends React.Component {
  static propTypes = {
    color: PropTypes.oneOf(['BLUE', 'YELLOW', 'ORANGE', 'PURPLE', 'GREEN', 'RED']),
    name: PropTypes.string,
    addPlayer: PropTypes.func,
  }

  constructor(props) {
     super(props);
     this.state = {
       name: '',
      }

     this.handleChange = this.handleChange.bind(this);
     this.keyPress = this.keyPress.bind(this);
  }

  handleChange(e) {
     this.setState({
       name: e.target.value,
     });
  }

  keyPress(e) {
     if(e.keyCode === 13){
       this.props.addPlayer(e.target.value)
     }
  }

  render(){
    return(
        <input value={this.state.name} onKeyDown={this.keyPress} onChange={this.handleChange} />
    )
  }
}

const mapStateToProps = (state) => ({
  ...state
})

const mapDispatchToProps = (dispatch) =>( {
  addPlayer: (name) => dispatch(addPlayer(name))
})

export default connect(mapStateToProps, mapDispatchToProps) (AddPlayerField);
