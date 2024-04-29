import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import 'bootstrap/dist/css/bootstrap.min.css';
import CreateCategory from "../category/createCategory";
import AdminCategoryList from "../category/AdminCategoryList";
import CreateProduct from "../product/createProduct";
import UpdateProduct from "../product/updateProduct";
import AdminProductList from "../product/AdminProductList";
import UpdateCategory from "../category/updateCategory";
import {AuthProvider} from "../user/AuthProvider";
import Sidebar from "./Sidebar";
import CategoriesNavigation from "./CategoriesNavigation";
import Login from "../user/Login";
import Register from "../user/Register";
import ProductsProvider from "../product/ProductsProvider";
import GetCart from "../cart/getCart";


function Paths() {
    return (
        <Router>
            <AuthProvider>
                <div className="row">
                    <div className="col-md-3">
                        {localStorage.getItem("userRole") === "ROLE_ADMIN" &&
                            <Sidebar/>
                        }
                    </div>
                    <div className="col-md-8">
                        <CategoriesNavigation/>
                        <Routes>
                            <Route path="/login" element={<Login/>}/>
                            <Route path="/register" element={<Register/>}/>
                            <Route path="/admin/categories/new" element={<CreateCategory/>}/>
                            <Route path="/admin/categories" element={<AdminCategoryList/>}/>
                            <Route path="/admin/products/new" element={<CreateProduct/>}/>
                            <Route path="/admin/products/update/:id" element={<UpdateProduct/>}/>
                            <Route path="/admin/categories/update/:id" element={<UpdateCategory/>}/>
                            <Route path="/category/:id" element={<ProductsProvider/>}/>
                            <Route path="/admin/products" element={<AdminProductList/>}/>
                            <Route path="/cart" element={<GetCart/>}/>
                        </Routes>
                    </div>
                </div>
            </AuthProvider>
        </Router>
    );
}

export default Paths;
