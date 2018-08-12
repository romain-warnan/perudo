import { ADD_PLAYER } from './actions'

const initialState = {
  game: {}
}

const perudoReducer = (state = initialState, action) => {
    console.log(action)
    switch (action.type) {
      case ADD_PLAYER:
        return state
      default:
        return state
    }
}

export default perudoReducer;
