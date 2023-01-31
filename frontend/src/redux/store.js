import { legacy_createStore , combineReducers   } from 'redux'


import { reducer as AuthReducer } from "./AuthReducer/reducer"

const rootReducer = combineReducers({AuthReducer})

const store = legacy_createStore( rootReducer  )

export { store }