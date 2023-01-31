import React from 'react';
import {useSelector} from 'react-redux'
import { Navigate } from 'react-router-dom';

function PrivateRoute({children}) {

    const {isAuth} = useSelector(state => state.AuthReducer)
    console.log("Auth Status:-" , isAuth)

    if(!isAuth){
        return <Navigate to={'/'}/>
    }

    return children
}

export default PrivateRoute;