import { createStore, applyMiddleware } from 'redux'
import thunk from 'redux-thunk'
import combineReducers from './combinedReducers'

export default function configureStore() {
 return createStore(
  combineReducers,
  applyMiddleware(thunk)
 )
}
