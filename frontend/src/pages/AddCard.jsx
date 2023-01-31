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

    const uuid = useSelector(state => state.AuthReducer.details.uuid)

    const toast = useToast()

    const { isOpen, onOpen, onClose } = useDisclosure()

    const [newData, setNewData] = useState({
        accountNumber: "",
        mobileNumber: "",
        ifscCode: "",
        bankName: "",
        bankBalance: "",
        walletId: ""
    })

    const HandleUpdate = () => {
        console.log("Add Card")

        const { accountNumber, mobileNumber, ifscCode, bankName, bankBalance, walletId } = newData

        if (accountNumber != "" && mobileNumber != "" && ifscCode != "" && bankName != "" && bankBalance != "" && walletId != "") {

            console.log(`http://localhost:8889/bank/${ uuid }`, newData)
            axios.post(`http://localhost:8889/bank/${ uuid }`, newData)
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
                                placeholder={'Account Number'}
                                name="accountNumber"
                                value={newData.accountNumber}
                                onChange={(e) => setNewData({ ...newData, [e.target.name]: e.target.value })}
                            />
                            <Input
                                placeholder={'Mobile Number'}
                                name="mobileNumber"
                                value={newData.mobileNumber}
                                onChange={(e) => setNewData({ ...newData, [e.target.name]: e.target.value })}
                            />
                            <Input
                                placeholder={'IFSC Code'}
                                name="ifscCode"
                                value={newData.ifscCode}
                                onChange={(e) => setNewData({ ...newData, [e.target.name]: e.target.value })}
                            />
                            <Input
                                placeholder={'Bank Name'}
                                name="bankName"
                                value={newData.bankName}
                                onChange={(e) => setNewData({ ...newData, [e.target.name]: e.target.value })}
                            />
                            <Input
                                placeholder={'Bank Balance'}
                                name="bankBalance"
                                value={newData.bankBalance}
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