import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { MDBContainer, MDBInput, MDBBtn } from 'mdb-react-ui-kit';
import { useNavigate } from 'react-router-dom';

function DashboardPage() {
    const [textData, setTextData] = useState('');
    const [texts, setTexts] = useState([]);
    const [searchId, setSearchId] = useState('');
    const [searchText, setSearchText] = useState('');
    const [editState, setEditState] = useState({}); // Для отслеживания редактируемых текстов
    const navigate = useNavigate();

    useEffect(() => {
        fetchTexts();
    }, []);

    const fetchTexts = async () => {
        try {
            const response = await axios.get('http://localhost:8080/api/texts', {
                headers: {
                    'Authorization': `Bearer ${localStorage.getItem('token')}`
                }
            });
            setTexts(response.data);
        } catch (error) {
            console.error('Failed to fetch texts:', error);
        }
    };

    const createText = async () => {
        try {
            await axios.post('http://localhost:8080/api/texts', { data: textData }, {
                headers: {
                    'Authorization': `Bearer ${localStorage.getItem('token')}`
                }
            });
            setTextData('');
            fetchTexts();
        } catch (error) {
            console.error('Failed to create text:', error);
        }
    };

    const deleteText = async (id) => {
        try {
            await axios.delete(`http://localhost:8080/api/texts/${id}`, {
                headers: {
                    'Authorization': `Bearer ${localStorage.getItem('token')}`
                }
            });
            fetchTexts();
        } catch (error) {
            console.error('Failed to delete text:', error);
        }
    };

    const searchTexts = async () => {
        try {
            const response = await axios.get('http://localhost:8080/api/texts/search', {
                headers: {
                    'Authorization': `Bearer ${localStorage.getItem('token')}`
                },
                params: {
                    id: searchId || undefined,
                    data: searchText || undefined,
                },
            });
            setTexts(response.data);
        } catch (error) {
            console.error('Failed to search texts:', error);
        }
    };

    // Функция обновления текста
    const updateText = async (id, newText) => {
        try {
            await axios.put(`http://localhost:8080/api/texts/${id}`, null, {
                headers: {
                    'Authorization': `Bearer ${localStorage.getItem('token')}`
                },
                params: {
                    data: newText
                }
            });
            setEditState((prevState) => ({ ...prevState, [id]: false })); // Скрываем кнопку Save после сохранения
            fetchTexts();
        } catch (error) {
            console.error('Failed to update text:', error);
        }
    };

    const handleLogout = () => {
        localStorage.removeItem('token');
        navigate('/');
    };

    return (
        <div className="d-flex flex-column align-items-center">
            <MDBContainer className="p-3">
                <h2 className="mb-4 text-center">Dashboard</h2>
                <MDBBtn className="mb-4" onClick={handleLogout}>Logout</MDBBtn>
                <MDBInput wrapperClass='mb-4' placeholder='New text' value={textData} onChange={(e) => setTextData(e.target.value)} />
                <MDBBtn className="mb-4" onClick={createText}>Create Text</MDBBtn>
                <MDBInput wrapperClass='mb-4' placeholder='Search by ID' value={searchId} onChange={(e) => setSearchId(e.target.value)} />
                <MDBInput wrapperClass='mb-4' placeholder='Search by Text' value={searchText} onChange={(e) => setSearchText(e.target.value)} />
                <MDBBtn className="mb-4" onClick={searchTexts}>Search</MDBBtn>
                <ul>
                    {texts.map(text => (
                        <li key={text.id} className="d-flex align-items-center mb-3">
                            <div style={{ flexGrow: 1 }}>
                                <span style={{ marginRight: '10px' }}>{text.id}</span>
                                <MDBInput
                                    value={text.data}
                                    onChange={(e) => {
                                        const newTexts = texts.map(t => t.id === text.id ? { ...t, data: e.target.value } : t);
                                        setTexts(newTexts);
                                        setEditState((prevState) => ({ ...prevState, [text.id]: true })); // Показываем кнопку Save при изменении текста
                                    }}
                                />
                            </div>
                            <div>
                                {editState[text.id] && (
                                    <MDBBtn color="success" className="ms-2" onClick={() => updateText(text.id, text.data)}>Save</MDBBtn>
                                )}
                                <MDBBtn color="danger" className="ms-2" onClick={() => deleteText(text.id)}>Delete</MDBBtn>
                            </div>
                        </li>
                    ))}
                </ul>
            </MDBContainer>
        </div>
    );
}

export default DashboardPage;
