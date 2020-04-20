/*
import { combineReducers } from 'redux'
import { REQUEST_CREATE_PLAYER, RECEIVE_NEW_PLAYER } from './actions'

const initialState = {
    id: undefined,
    players: [],
    palifico: false,
    messages: [],
    result: undefined,
}

function perudo(state = initialState, action) {
    switch(action.type) {
        case REQUEST_CREATE_PLAYER:
            return { ...state, players: [
                ...state.players,
                { name: action.playerName },
            ] }
        default:
            return state
    }
}

const rootReducer = combineReducers({
    perudo,
})
  
export default rootReducer
*/