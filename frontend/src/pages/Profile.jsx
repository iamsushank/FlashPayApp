import { Box, Button, Flex, FormControl, FormHelperText, FormLabel, Input, Text, useDisclosure, useToast } from '@chakra-ui/react';
import {
    Modal,
    ModalOverlay,
    ModalContent,
    ModalHeader,
    ModalFooter,
    ModalBody,
    ModalCloseButton,
} from '@chakra-ui/react'
import axios from 'axios';
import React, { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';

function Profile(props) {

    const { details: { name, email, token } } = useSelector(state => state.AuthReducer)
    console.log(name, email, token)

    return (
        <Flex justifyContent={'center'} alignItems='center' w={'100vw'} h='80vh'>

            <Box width={["90%", '60%', '35%']} p={['20px']} border='1px' borderRadius={'5px'} borderColor='gray.100'>
                <Flex flexDirection='column' w={['100%']} textAlign='start' gap={['10px']}>
                    <Text fontSize={['3xl']} textAlign='center' display={['none', 'block']} fontWeight='500'> Welcome </Text>
                </Flex>

            </Box>

        </Flex>
    );
}



function BasicUsage({ filed }) {

    const toast = useToast()

    const { isOpen, onOpen, onClose } = useDisclosure()

    const [newData , setNewData] = useState('')

    const HandleUpdate = ()=>{
        console.log("Updated")

        if(newData != ''){

            axios.post("" , {
                [filed]:newData
            })
            .then( ({data}) =>{
                onClose()
                toast({
                    title: 'Updated Successful.',
                    status: 'success',
                    duration: 1000,
                    isClosable: true,
                })
    
            } )
            .catch(err=>{
                console.log(err)
                toast({
                    title: 'Error Accure.',
                    status: 'error',
                    duration: 1000,
                    isClosable: true,
                })
    
            })

        }else{
            toast({
                title: 'Fields are required.',
                status: 'error',
                duration: 1000,
                isClosable: true,
            })
        }
       

    }


    useEffect(()=>{

        if(!isOpen){
            setNewData('')
        }
        
    } , [isOpen])




    return (
        <>
            <Button
                size={'xs'}
                onClick={onOpen}
                variant='outline'
            >Edit
            </Button>

            <Modal isOpen={isOpen} onClose={onClose}>
                <ModalOverlay />
                <ModalContent>
                    <ModalHeader>Edit {
                        filed == 'name' ? "Name" : filed == "password" ?
                            "Password" : filed == "phoneno" ?
                                "Phone No" : ""}
                    </ModalHeader>
                    <ModalCloseButton />
                    <ModalBody>
                        <Input 
                        placeholder={'Enter new ' + filed}
                        value={newData}
                        onChange={(e)=>setNewData(e.target.value)}
                        />
                    </ModalBody>

                    <ModalFooter>
                        <Button size={'sm'} colorScheme='red' mr={3} onClick={HandleUpdate}>
                            Submit
                        </Button>
                    </ModalFooter>
                </ModalContent>
            </Modal>
        </>
    )
}

export default Profile;