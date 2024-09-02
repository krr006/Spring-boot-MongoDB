import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

function TextCreatePage() {
    const [data, setData] = useState('');
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            await axios.post('/api/texts', { data });
            navigate('/dashboard');
        } catch (error) {
            console.error('Failed to create text:', error);
        }
    };

    return (
        <div>
            <h1>Create New Text</h1>
            <form onSubmit={handleSubmit}>
                <textarea
                    value={data}
                    onChange={(e) => setData(e.target.value)}
                />
                <button type="submit">Create</button>
            </form>
        </div>
    );
}

export default TextCreatePage;
