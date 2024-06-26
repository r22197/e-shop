import React, { useState, useEffect } from "react";
import { getAllCategories } from "../data/CategoryApi";
import {createProduct} from "../data/ProductApi"
import {Link} from "react-router-dom";

const CreateProduct = () => {
    const [newProduct, setNewProduct] = useState({
        name: "",
        description: "",
        price: "",
        category: "",
        imagePath: ""
    });
    const [categories, setCategories] = useState([]);
    const [successMessage, setSuccessMessage] = useState("");
    const [errorMessage, setErrorMessage] = useState("");

    useEffect(() => {
        fetchCategories();
    }, []);

    const fetchCategories = async () => {
        try {
            const data = await getAllCategories();
            setCategories(data);
        } catch (error) {
            console.error("Error fetching categories:", error);
        }
    };

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setNewProduct({
            ...newProduct,
            [name]: value
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            console.log(newProduct)
            const response = await createProduct(newProduct);
            if (response) {
                setSuccessMessage("Product has been created successfully");
                setNewProduct({ name: "", description: "", price: "", category: "", imagePath: "" });
            } else {
                setErrorMessage("Error while creating product");
            }
        } catch (error) {
            setErrorMessage("Error while creating product: " + error.message);
        }
    };

    return (
        <div className="container">
            <h2>Create Product</h2>
            <form onSubmit={handleSubmit}>
                <div className="mb-3">
                    <label className="form-label">
                        Name: <span className="text-danger">*</span>
                    </label>
                    <input
                        type="text"
                        className="form-control"
                        name="name"
                        value={newProduct.name}
                        onChange={handleInputChange}
                        required
                    />
                </div>
                <div className="mb-3">
                    <label className="form-label">Description:</label>
                    <textarea
                        className="form-control"
                        name="description"
                        value={newProduct.description}
                        onChange={handleInputChange}
                    />
                </div>
                <div className="mb-3">
                    <label className="form-label">Price:</label>
                    <input
                        type="number"
                        className="form-control"
                        name="price"
                        value={newProduct.price}
                        onChange={handleInputChange}
                    />
                </div>
                <div className="mb-3">
                    <label className="form-label">Category:</label>
                    <select
                        className="form-select"
                        name="category"
                        value={newProduct.category.id}
                        onChange={handleInputChange}
                        //required
                    >
                        <option value="">Select Category</option>
                        {categories.map((category) => (
                            <option key={category.id} value={category.id}>
                                {category.name}
                            </option>
                        ))}
                    </select>
                </div>
                <div className="mb-3">
                    <label className="form-label">Image Path:</label>
                    <input
                        className="form-control"
                        name="imagePath"
                        value={newProduct.imagePath}
                        onChange={handleInputChange}
                    />
                </div>
                <div>
                    <button type="submit" className="btn btn-primary">Create Product</button>
                </div>
            </form>
            <Link to={`/admin/products`} className="mt-3 btn btn-info me-2">Vrátit se</Link>
            <div className="mt-3 alert alert-info"><strong>Pozor!</strong> Pola označena <span
                className="text-danger">*</span> jsou povinná.
            </div>
            {successMessage && <div className="mt-3 alert alert-success">{successMessage}</div>}
            {errorMessage && <div className="mt-3 alert alert-danger">{errorMessage}</div>}
        </div>
    );
};

export default CreateProduct;