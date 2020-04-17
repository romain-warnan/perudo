export const ADD_PLAYER = 'ADD_PLAYER'
export const ROLL_DICE = 'ROLL_DICE'
export const START_GAME = 'START_GAME'
export const END_GAME = 'END_GAME'
export const BET = 'BET'
export const DUDO = 'DUDO'
export const CALZA = 'CALZA'
export const GAME_STATE = 'GAME_STATE'

export function addPlayer(playerName) {
    return { type: ADD_PLAYER, playerName }
}

export function rollDice(game, player) {
    return { type: ROLL_DICE, game, player }
}

export function startGame(game) {
    return { type: START_GAME, game }
}

export function endGame(game) {
    return { type: END_GAME, game }
}

export function bet(game, player, number, face) {
    return { type: BET, game, player, number, face }
}

export function dudo(game, player) {
    return { type: DUDO, game, player }
}

export function calza(game, player) {
    return { type: CALZA, game, player }
}

export function gameState(game, player) {
    return { type: GAME_STATE, game, player }
}