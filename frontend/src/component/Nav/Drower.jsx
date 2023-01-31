import { Avatar, Link, Drawer, DrawerBody, DrawerCloseButton, DrawerContent, DrawerHeader, DrawerOverlay, Flex, Text, useDisclosure, Box } from '@chakra-ui/react';
import React from 'react';
import { useSelector } from 'react-redux';
import { NavLink } from 'react-router-dom';

function Drower() {

    const {isAuth} = useSelector(state=>state.AuthReducer)

    const { isOpen, onOpen, onClose } = useDisclosure()
    const btnRef = React.useRef()

    return (
        <div>
            <Avatar display={['block', 'block', 'none', 'none']} src='#' onClick={onOpen} size='md' bg='gray.200'> </Avatar>

            <Drawer
                isOpen={isOpen}
                placement='right'
                onClose={onClose}
                finalFocusRef={btnRef}
            >
                <DrawerOverlay />
                <DrawerContent>
                    <DrawerCloseButton />
                    <DrawerHeader>
                        <Flex alignItems='center' gap={['10px']}>
                            <Avatar src='#' onClick={onOpen} size='md' bg='gray.200'> </Avatar>
                            <Text>Guest</Text>
                        </Flex>
                    </DrawerHeader>

                    <DrawerBody p='0'>

                        <Flex flexDirection='column'>

                            {!isAuth ? (
                                <>
                                    <Link _activeLink={{ color: "red" }} as={NavLink} onClick={onClose} to='/'><Box borderTop='1px solid' borderColor="gray.100" p='15px 30px'>  Login </Box> </Link>
                                    <Link as={NavLink} _activeLink={{ color: "red" }} onClick={onClose} to='/signup' > <Box borderTop='1px solid' borderColor="gray.100" p='15px 30px'>  SignUp </Box> </Link>
                                </>
                            ) : (
                                <>
                                    <Link as={NavLink} _activeLink={{ color: "red" }} onClick={onClose} to='/profile' > <Box borderTop='1px solid' borderColor="gray.100" p='15px 30px'>  Profile </Box> </Link>
                                    <Link as={NavLink} _activeLink={{ color: "red" }} onClick={onClose} to='/dashboard' > <Box borderTop='1px solid' borderColor="gray.100" p='15px 30px'>  Dashboard </Box> </Link>
                                    <Link as={NavLink} _activeLink={{ color: "red" }} onClick={onClose} to='/payment' > <Box border='1px solid' borderColor='gray.100' p='15px 30px'>  Payment </Box> </Link>
                                </>
                            )}


                        </Flex>

                    </DrawerBody>

                </DrawerContent>
            </Drawer>
        </div>
    );
}

export default Drower;