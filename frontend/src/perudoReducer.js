import { ADD_PLAYER } from './actions'

const initialState = {
  game: {},
}

const perudoReducer = (state = initialState, action) => {
    switch (action.type) {
      case ADD_PLAYER:
        // console.log(action)
        return state
      default:
        return state
    }
}

export default perudoReducer;
