import React from 'react';
import { useAuth } from "./AuthProvider";
import { Link } from 'react-router-dom';

const AuthComponent = () => {
    const { user, handleLogout } = useAuth();

    return (
        <div>
            {user ? (
                <div className="d-flex align-items-center justify-content-end mb-2">
                    <small className="fw-bolder me-2">{user.sub}</small>
                    <div className="btn btn-danger" onClick={handleLogout}>Odhlásit se</div>
                </div>
            ) : (
                <div className="d-flex align-items-center justify-content-end mb-2">
                    <Link to="/login" className="btn btn-success">Přihlásit se</Link>
                </div>
            )}
        </div>
    );
};

export default AuthComponent;
