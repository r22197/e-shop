import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import 'bootstrap/dist/css/bootstrap.min.css';
import UpdateProduct from "./components/product/updateProduct";
import AdminProductList from "./components/admin/AdminProductList";
import GetAllProducts from "./components/product/getAllProducts";
import AdminCategoryList from "./components/admin/AdminCategoryList";
import CreateCategory from "./components/category/createCategory";
import CreateProduct from "./components/product/createProduct";
import Sidebar from "./components/layout/Sidebar";

function App() {
    return (
        <Router>
            <div className="row">
                <div className="col-md-3">
                    <Sidebar/>
                </div>
                <div className="col-md-9">
                    <Routes>
                        <Route path="/admin/categories/new" element={CreateCategory()}/>
                        <Route path="/admin/categories" element={AdminCategoryList()} />
                        <Route path="/admin/products/new" element={CreateProduct()} />
                        <Route path="/admin/products/update/:id" element={<UpdateProduct />} />

                        <Route path="/admin/products" element={AdminProductList()} />
                        <Route path="/" element={GetAllProducts()} />
                    </Routes>
                </div>
            </div>
        </Router>
    );
}

export default App;
