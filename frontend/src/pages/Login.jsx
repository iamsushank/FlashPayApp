import { Box, Button, Flex, FormControl, FormHelperText, FormLabel, Input, Text, useToast } from '@chakra-ui/react';
import React, { useState } from 'react';
import axios from 'axios';
import { useDispatch , useSelector } from 'react-redux'
import { useNavigate } from 'react-router-dom';
import { Login_Failure, Login_Request, Login_Success } from '../redux/AuthReducer/action';

function Login() {

    const toast = useToast()

    const {isLoading} = useSelector(state=>state.AuthReducer)

    const dispatch = useDispatch()

    const navigate = useNavigate()

    const [userData, setUserData] = useState({
        mobileNo: "",
        password: ""
    })

    const HandleSubmit = () => {

      
        const { mobileNo, password } = userData

        if ( mobileNo != '', password != "") {

            dispatch(Login_Request())


            axios.post("http://localhost:8889/login", userData)
                .then(({ data }) => {
                    try {
                        console.log(data)
                        dispatch(Login_Success(data))
                        navigate('/')
                        toast({
                            title: 'Login Successful.',
                            status: 'success',
                            duration: 1000,
                            isClosable: true,
                        })
                    } catch (error) {
                        console.log(error)
                        dispatch(Login_Failure())
                        toast({
                            title: 'Login Failure.',
                            status: 'error',
                            duration: 1000,
                            isClosable: true,
                        })
                    }
                })
                .catch(err => {
                    dispatch(Login_Failure())
                    toast({
                        title: 'Login Failure.',
                        status: 'error',
                        duration: 1000,
                        isClosable: true,
                    })
                })


        } else {

            toast({
                title: 'Fill all the fields.',
                status: 'error',
                duration: 1000,
                isClosable: true,
            })
        }


    }



    return (

        <Flex justifyContent={'center'} alignItems='center' w={'100vw'} h='90vh'>

            <Box width={["90%", '60%', '40%']} p={['20px']} border='1px' borderRadius={'5px'} borderColor='gray.100'>
                <Flex flexDirection='column' w={['100%']} textAlign='start' gap={['10px']}>
                    <Text fontSize={['3xl']} textAlign='center' display={['none', 'block']} fontWeight='500'> Welcome to Login</Text>
                    <Text fontSize={['3xl']} textAlign='center' display={['block', 'none']} fontWeight='500'> Login</Text>
                    
                    <FormControl isRequired>
                        <FormLabel>Mobile No</FormLabel>
                        <Input type='text' 
                         name='mobileNo'
                         value={userData.mobileNo}
                         onChange={(e) => setUserData({ ...userData, [e.target.name]: e.target.value })}
                        />
                        <FormHelperText>We'll never share your Student ID.</FormHelperText>
                    </FormControl>

                    <FormControl isRequired>
                        <FormLabel>Password</FormLabel>
                        <Input type='password' 
                         name='password'
                         value={userData.password}
                         onChange={(e) => setUserData({ ...userData, [e.target.name]: e.target.value })}
                        />
                    </FormControl>


                    <Flex gap={['10px']} mt={['10px']}>
                        <Button isLoading={isLoading} colorScheme='red' size={['sm', 'md']} onClick={HandleSubmit}>Login</Button>

                    </Flex>

                </Flex>

            </Box>

        </Flex>

    );
}

export default Login;