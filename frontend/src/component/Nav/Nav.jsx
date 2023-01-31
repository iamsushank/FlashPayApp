import React from 'react';
import { Avatar, Box, Flex, Heading, Link, Spacer } from "@chakra-ui/react";
import { NavLink } from 'react-router-dom';
import Drower from './Drower';
import { useSelector } from 'react-redux';
const favicon = 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQM4grpFnLQO-9dup7aw7G3uMjB6_ckEpS4Og&usqp=CAU'

function Nav() {

    const {isAuth} = useSelector(state=>state.AuthReducer)


    return (
        <Box shadow='sm' position='fixed' width='100%' backgroundColor='white'>

            <Flex alignItems='center' gap='2' py={['10px', '13px', '15px']} w='95%' m='auto' >

                <Flex alignItems='center' gap={'10px'}>
                    <Avatar src={favicon} size='md'> </Avatar>
                    <Heading size='md' display={['none', 'block']}> OnePay</Heading>
                    <Heading size='md' display={['block', 'none']}>OnePay</Heading>
                </Flex>

                <Spacer />

                <Flex alignItems='center' gap={['20px', '30px']} fontWeight='bold' pr={['0px', '0xp', '5px', '20px']} display={['none', 'none', 'flex']}>

                    {!isAuth ? (
                        <>

                            <Link as={NavLink} to='/' _activeLink={{ color: "red", textDecoration: 'none' }}>Login</Link>
                            <Link as={NavLink} to='/signup' _activeLink={{ color: "red", textDecoration: 'none' }}>SignUp</Link>
                        </>
                    ) : (
                        <>
                            <Link as={NavLink} to='/profile' _activeLink={{ color: "red", textDecoration: 'none' }}>Profile</Link>
                            <Link as={NavLink} to='/dashboard' _activeLink={{ color: "red", textDecoration: 'none' }}>Dashboard</Link>
                            <Link as={NavLink} to='/payment' _activeLink={{ color: "red", textDecoration: 'none' }}>Payment</Link>
                        </>
                    )}

                </Flex>


                <Drower />


            </Flex>

        </Box>
    );
}

export default Nav;