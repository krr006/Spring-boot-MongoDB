import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';
import { MDBContainer, MDBTable, MDBTableHead, MDBTableBody } from 'mdb-react-ui-kit';

function DashboardPage() {
    const [texts, setTexts] = useState([]);

    useEffect(() => {
        async function fetchTexts() {
            try {
                const response = await axios.get('http://localhost:8080/api/texts');
                setTexts(response.data);
            } catch (error) {
                console.error('Failed to fetch texts:', error);
            }
        }
        fetchTexts();
    }, []);

    const handleDelete = async (id) => {
        try {
            await axios.delete(`http://localhost:8080/api/texts/${id}`);
            setTexts(texts.filter(text => text.id !== id));
            console.log('Text record deleted');
        } catch (error) {
            console.error('Failed to delete text record:', error);
        }
    };

    return (
        <MDBContainer className="p-3">
            <h2 className="mb-4 text-center">Dashboard</h2>
            <Link to="/create" className="btn btn-primary mb-4">Create New Text Record</Link>
            <MDBTable>
                <MDBTableHead>
                    <tr>
                        <th>ID</th>
                        <th>Data</th>
                        <th>Actions</th>
                    </tr>
                </MDBTableHead>
                <MDBTableBody>
                    {texts.map(text => (
                        <tr key={text.id}>
                            <td>{text.id}</td>
                            <td>{text.data}</td>
                            <td>
                                <Link to={`/texts/${text.id}`} className="btn btn-info btn-sm">View</Link>
                                <Link to={`/texts/edit/${text.id}`} className="btn btn-warning btn-sm mx-2">Edit</Link>
                                <button className="btn btn-danger btn-sm" onClick={() => handleDelete(text.id)}>Delete</button>
                            </td>
                        </tr>
                    ))}
                </MDBTableBody>
            </MDBTable>
        </MDBContainer>
    );
}

export default DashboardPage;