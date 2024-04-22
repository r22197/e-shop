import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import 'bootstrap/dist/css/bootstrap.min.css';
import CreateCategory from "../category/createCategory";
import AdminCategoryList from "../admin/AdminCategoryList";
import CreateProduct from "../product/createProduct";
import UpdateProduct from "../product/updateProduct";
import AdminProductList from "../admin/AdminProductList";
import GetAllProducts from "../product/getAllProducts";
import UpdateCategory from "../category/updateCategory";


function Paths() {
    return (
        <Router>
            <Routes>
                <Route path="/admin/products" element={<AdminProductList />} />
                <Route path="/admin/products/:id" element={<UpdateProduct />} />
                <Route path="/admin/products/new" element={< CreateProduct />} />

                <Route path="/admin/categories/new" element={< CreateCategory />}/>
                <Route path="admin/categories" element={<AdminCategoryList/>} />
                <Route path="/admin/categories/:id" element={<UpdateCategory />} />
                <Route path="/" element={<GetAllProducts />} />
            </Routes>
        </Router>
    );
}

export default Paths;
