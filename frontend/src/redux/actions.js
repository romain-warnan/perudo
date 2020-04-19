
export const REQUEST_CREATE_PLAYER = 'REQUEST_CREATE_PLAYER'
export function requestCreatePlayer(playerName) {
    return { type: REQUEST_CREATE_PLAYER, playerName }
}

export const RECEIVE_NEW_PLAYER = 'RECEIVE_NEW_PLAYER'
export function receiveNewPlayer(player) {
    return { type: RECEIVE_NEW_PLAYER, player }
}

function fetchAddPlayer(playerName) {
    return dispatch => {
        dispatch(requestCreatePlayer(playerName))
        return fetch(`http://localhost:8080/player?name=${playerName}`)
            .then(response => response.json())
            .then(player => dispatch(receiveNewPlayer(player)))
    }
}

export const ROLL_DICE = 'ROLL_DICE'
export function rollDice(game, player) {
    return { type: ROLL_DICE, game, player }
}

export const START_GAME = 'START_GAME'
export function startGame(game) {
    return { type: START_GAME, game }
}

export const END_GAME = 'END_GAME'
export function endGame(game) {
    return { type: END_GAME, game }
}

export const BET = 'BET'
export function bet(game, player, number, face) {
    return { type: BET, game, player, number, face }
}

export const DUDO = 'DUDO'
export function dudo(game, player) {
    return { type: DUDO, game, player }
}

export const CALZA = 'CALZA'
export function calza(game, player) {
    return { type: CALZA, game, player }
}

export const GAME_STATE = 'GAME_STATE'
export function gameState(game, player) {
    return { type: GAME_STATE, game, player }
}