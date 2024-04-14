import React from "react";
import { Link } from "react-router-dom";

const Sidebar = () => {
    return (
        <div className="border rounded position-fixed top-0 start-0 bottom-0">
            <div className="p-4">
                <h3 className="text-center mb-4">Administrační panel</h3>
                <ul className="nav flex-column">
                    <li className="nav-item mb-3 btn bg-secondary-subtle">
                        <Link to="/admin/categories" className="nav-link text-black fw-bold">Kategorie</Link>
                    </li>
                    <li className="nav-item mb-3 btn bg-secondary-subtle">
                        <Link to="/admin/products" className="nav-link text-black fw-bold">Produkty</Link>
                    </li>
                    <li className="nav-item mt-5 btn bg-warning-subtle">
                        <Link to="/" className="nav-link text-black fw-bold">Zpět na e-shop</Link>
                    </li>
                </ul>
            </div>
        </div>
    );
};

export default Sidebar;
