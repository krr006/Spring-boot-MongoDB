import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate, useParams } from 'react-router-dom';

function TextEditPage() {
    const { id } = useParams();
    const [data, setData] = useState('');
    const navigate = useNavigate();

    useEffect(() => {
        const fetchText = async () => {
            const response = await axios.get(`/api/texts/${id}`);
            setData(response.data.data);
        };
        fetchText();
    }, [id]);

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            await axios.put(`/api/texts/${id}`, { data });
            navigate('/dashboard');
        } catch (error) {
            console.error('Failed to update text:', error);
        }
    };

    return (
        <div>
            <h1>Edit Text</h1>
            <form onSubmit={handleSubmit}>
                <textarea
                    value={data}
                    onChange={(e) => setData(e.target.value)}
                />
                <button type="submit">Save</button>
            </form>
        </div>
    );
}

export default TextEditPage;
