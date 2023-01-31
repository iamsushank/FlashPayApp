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


function AddCard({ filed }) {

    const toast = useToast()

    const { isOpen, onOpen, onClose } = useDisclosure()

    const [newData, setNewData] = useState({
        bankName: "",
        accountNo: "",
        valid: "",
        customerName: "",
        cvv: ""
    })

    const HandleUpdate = () => {
        console.log("Add Card")

        const { bankName, accountNo, valid, customerName, cvv } = newData

        if (bankName != '' && accountNo != "" && valid != "" && customerName != "" && cvv != '') {

            axios.post("", {
                bankName,
                accountNo,
                valid,
                customerName,
                cvv
            })
                .then(({ data }) => {
                    onClose()
                    toast({
                        title: 'Updated Successful.',
                        status: 'success',
                        duration: 1000,
                        isClosable: true,
                    })

                })
                .catch(err => {
                    console.log(err)
                    toast({
                        title: 'Error Accure.',
                        status: 'error',
                        duration: 1000,
                        isClosable: true,
                    })

                })

        } else {
            toast({
                title: 'Fields are required.',
                status: 'error',
                duration: 1000,
                isClosable: true,
            })
        }


    }


    useEffect(() => {

        if (!isOpen) {
            setNewData({
                bankName: "",
                accountNo: "",
                valid: "",
                customerName: "",
                cvv: ""
            })
        }

    }, [isOpen])



    return (
        <>
            <Button
                onClick={onOpen}
                variant='ghost'
                colorScheme={'red'}
            >Add Card
            </Button>

            <Modal isOpen={isOpen} onClose={onClose}>
                <ModalOverlay />
                <ModalContent>
                    <ModalHeader>
                        Add New Card
                    </ModalHeader>
                    <ModalCloseButton />
                    <ModalBody>
                        <Flex flexDirection={'column'} gap='10px'>

                            <Input
                                placeholder={'Bank Name'}
                                name="bankName"
                                value={newData.bankName}
                                onChange={(e) => setNewData({ ...newData, [e.target.name]: e.target.value })}
                            />
                            <Input
                                placeholder={'Account No'}
                                name="accountNo"
                                value={newData.accountNo}
                                onChange={(e) => setNewData({ ...newData, [e.target.name]: e.target.value })}
                            />
                            <Input
                                placeholder={'Valid Thus'}
                                name="valid"
                                value={newData.valid}
                                onChange={(e) => setNewData({ ...newData, [e.target.name]: e.target.value })}
                            />
                            <Input
                                placeholder={'Customer Name'}
                                name="customerName"
                                value={newData.customerName}
                                onChange={(e) => setNewData({ ...newData, [e.target.name]: e.target.value })}
                            />
                            <Input
                                placeholder={'CVV'}
                                name="cvv"
                                value={newData.cvv}
                                onChange={(e) => setNewData({ ...newData, [e.target.name]: e.target.value })}
                            />

                        </Flex>
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


export default AddCard