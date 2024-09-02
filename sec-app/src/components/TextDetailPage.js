import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useParams, Link } from 'react-router-dom';  // Добавлен импорт Link

function TextDetailPage() {
    const { id } = useParams();
    const [text, setText] = useState(null);

    useEffect(() => {
        const fetchText = async () => {
            try {
                const response = await axios.get(`/api/texts/${id}`);
                setText(response.data);
            } catch (error) {
                console.error('Failed to fetch text:', error);
            }
        };
        fetchText();
    }, [id]);

    if (!text) {
        return <div>Loading...</div>;
    }

    return (
        <div>
            <h1>Text Detail</h1>
            <p>{text.data}</p>
            <Link to="/dashboard">Back to Dashboard</Link>
        </div>
    );
}

export default TextDetailPage;
