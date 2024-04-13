// App.jsx

import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import 'bootstrap/dist/css/bootstrap.min.css';
import AdminProductList from "./components/admin/AdminProductList";
import CreateProduct from "./components/product/createProduct";
import GetAllProducts from "./components/product/getAllProducts";

function App() {
    return (
        <Router>
            <div>
                <Routes>
                    <Route path="/admin/products/new" element={<CreateProduct />} />
                    <Route path="/admin/products" element={<AdminProductList />} />
                    <Route path="/" element={<GetAllProducts />} />
                </Routes>
            </div>
        </Router>
    );
}

export default App;
