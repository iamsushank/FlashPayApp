import { Box, Button, Flex, FormControl, FormHelperText, FormLabel, Input, Text, useToast } from '@chakra-ui/react';
import React, { useState } from 'react';
import axios from 'axios';
import { useDispatch } from 'react-redux'
import { Link, useNavigate } from 'react-router-dom'
import { Login_Request } from '../redux/AuthReducer/action';

function Signup() {

    const toast = useToast()

    const dispatch = useDispatch()

    const navigate = useNavigate()

    const [userData, setUserData] = useState({
        userName: "",
        mobileNo: "",
        password: "",
        email: ""
    })
    console.log(userData);
    const HandleSubmit = () => {

        const { userName, mobileNo, password, email } = userData

        if (userName != "", mobileNo != '', password != "", email != "") {

            axios.post("http://localhost:8889/signUp", userData)
                .then(({ data }) => {
                    console.log(data)
                    // dispatch(Login_Request())

                    navigate('/')

                    toast({
                        title: 'Login Successful.',
                        status: 'success',
                        duration: 1000,
                        isClosable: true,
                    })
                })
                .catch(err => {
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
        <Flex justifyContent={'center'} alignItems='center' w={'100vw'} h={['100vh', '100vh', '100vh', '90vh']}>

            <Box width={["90%", '60%', '40%']} p={['20px']} border='1px' borderRadius={'5px'} borderColor='gray.100'>
                <Flex flexDirection='column' w={['100%']} textAlign='start' gap={['10px']}>
                    <Text fontSize={['3xl']} textAlign='center' display={['none', 'block']} fontWeight='500'> Welcome to SignUp</Text>
                    <Text fontSize={['3xl']} textAlign='center' display={['block', 'none']} fontWeight='500'> SignUp</Text>

                    <FormControl isRequired>
                        <FormLabel>Name </FormLabel>
                        <Input type='text'
                            name='userName'
                            value={userData.userName}
                            onChange={(e) => setUserData({ ...userData, [e.target.name]: e.target.value })}
                        />
                    </FormControl>

                    <FormControl isRequired>
                        <FormLabel>Email ID</FormLabel>
                        <Input type='email'
                            name='email'
                            value={userData.email}
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
                    
                    <FormControl isRequired>
                        <FormLabel>Mobile</FormLabel>
                        <Input type='text'
                            name='mobileNo'
                            value={userData.mobileNo}
                            onChange={(e) => setUserData({ ...userData, [e.target.name]: e.target.value })}
                        />
                    </FormControl>

                    <Flex gap={['10px']} mt={['10px']}>
                        <Button as={Link} to='/' colorScheme='blackAlpha' size={['sm', 'md']}>Back</Button>
                        <Button colorScheme='red' size={['sm', 'md']} onClick={HandleSubmit}>SignUp</Button>
                    </Flex>

                </Flex>
            </Box>

        </Flex>
    );
}

export default Signup;