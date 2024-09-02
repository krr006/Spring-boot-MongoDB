import React, { useState, useEffect } from 'react';
import axios from 'axios';
import {
    MDBContainer,
    MDBInput,
    MDBBtn,
    MDBTable,
    MDBTableBody,
    MDBTableHead
} from 'mdb-react-ui-kit';

function DashboardPage() {
    const [textRecords, setTextRecords] = useState([]);
    const [newText, setNewText] = useState('');
    const [editText, setEditText] = useState('');
    const [selectedRecordId, setSelectedRecordId] = useState(null);

    useEffect(() => {
        loadTextRecords();
    }, []);

    const loadTextRecords = async () => {
        try {
            const response = await axios.get('http://localhost:8080/api/texts');
            console.log('Data loaded:', response.data);  // Логирование загруженных данных
            setTextRecords(response.data);
        } catch (error) {
            console.error('Failed to load text records:', error);
        }
    };

    const handleCreateText = async () => {
        try {
            const response = await axios.post('http://localhost:8080/api/texts', { data: newText });
            console.log('Text created:', response.data);  // Логирование созданного текста
            setTextRecords([...textRecords, response.data]);
            setNewText('');
        } catch (error) {
            console.error('Failed to create text record:', error);
        }
    };

    const handleUpdateText = async (id) => {
        try {
            const response = await axios.put(`http://localhost:8080/api/texts/${id}`, null, {
                params: { data: editText }
            });
            console.log('Text updated:', response.data);  // Логирование обновленного текста
            setTextRecords(textRecords.map(record => record.id === id ? response.data : record));
            setSelectedRecordId(null);
            setEditText('');
        } catch (error) {
            console.error('Failed to update text record:', error);
        }
    };

    const handleDeleteText = async (id) => {
        try {
            await axios.delete(`http://localhost:8080/api/texts/${id}`);
            console.log('Text deleted:', id);  // Логирование удаленного текста
            setTextRecords(textRecords.filter(record => record.id !== id));
        } catch (error) {
            console.error('Failed to delete text record:', error);
        }
    };

    return (
        <MDBContainer>
            <h2 className="mb-4">Text Records</h2>
            <MDBTable>
                <MDBTableHead>
                    <tr>
                        <th>ID</th>
                        <th>Text</th>
                        <th>Actions</th>
                    </tr>
                </MDBTableHead>
                <MDBTableBody>
                    {textRecords.length > 0 ? (
                        textRecords.map(record => (
                            <tr key={record.id}>
                                <td>{record.id}</td>
                                <td>
                                    {selectedRecordId === record.id ? (
                                        <MDBInput
                                            value={editText}
                                            onChange={(e) => setEditText(e.target.value)}
                                        />
                                    ) : (
                                        record.data
                                    )}
                                </td>
                                <td>
                                    {selectedRecordId === record.id ? (
                                        <>
                                            <MDBBtn onClick={() => handleUpdateText(record.id)}>Save</MDBBtn>
                                            <MDBBtn color="danger" onClick={() => setSelectedRecordId(null)}>Cancel</MDBBtn>
                                        </>
                                    ) : (
                                        <>
                                            <MDBBtn onClick={() => {
                                                setSelectedRecordId(record.id);
                                                setEditText(record.data);
                                            }}>Edit</MDBBtn>
                                            <MDBBtn color="danger" onClick={() => handleDeleteText(record.id)}>Delete</MDBBtn>
                                        </>
                                    )}
                                </td>
                            </tr>
                        ))
                    ) : (
                        <tr>
                            <td colSpan="3" className="text-center">No records found.</td>
                        </tr>
                    )}
                </MDBTableBody>
            </MDBTable>

            <h4 className="mt-4">Add New Text</h4>
            <MDBInput
                wrapperClass='mb-3'
                placeholder='Enter new text'
                value={newText}
                onChange={(e) => setNewText(e.target.value)}
            />
            <MDBBtn onClick={handleCreateText}>Add Text</MDBBtn>
        </MDBContainer>
    );
}

export default DashboardPage;
