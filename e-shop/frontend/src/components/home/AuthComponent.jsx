import React, { useState, useEffect } from 'react';

const AuthComponent = () => {
    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const [username, setUsername] = useState('');

    useEffect(() => {
        const token = localStorage.getItem('token');
        if (token) {
            setIsLoggedIn(true);
            setUsername('User');
        }
    }, []);

    const handleLogout = () => {
        localStorage.removeItem('token');
        setIsLoggedIn(false);
        setUsername('');
    };

    const handleLogoutClick = () => {
        handleLogout();
    };

    return (
        <div>
            {!isLoggedIn ? (
                <>
                    <a href="/login">Přihlásit se</a>
                </>
            ) : (
                <>
                    <p>Welcome, {username}!</p>
                    <button onClick={handleLogoutClick}>Odhlásit se</button>
                </>
            )}
        </div>
    );
};

export default AuthComponent;
