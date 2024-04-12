import React, { useState, useEffect } from "react";
import { createProduct } from "../utils/ApiFunctions";
import { getAllCategories } from "../utils/ApiFunctions";

const CreateProduct = () => {
    const [newProduct, setNewProduct] = useState({
        name: "",
        description: "",
        price: "",
        category: { id: "" }
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
            [name]: name === "category" ? { id: value } : value
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await createProduct(newProduct.name, newProduct.description, newProduct.price, newProduct.category);
            if (response) {
                setSuccessMessage("Product has been created successfully");
                setNewProduct({ name: "", description: "", price: "", category: { id: "" } });
            } else {
                setErrorMessage("Error while creating product");
            }
        } catch (error) {
            setErrorMessage("Error while creating product: " + error.message);
        }
    };

    return (
        <div>
            <h2>Create Product</h2>
            <form onSubmit={handleSubmit}>
                <div>
                    <label>Name:</label>
                    <input
                        type="text"
                        name="name"
                        value={newProduct.name}
                        onChange={handleInputChange}
                    />
                </div>
                <div>
                    <label>Description:</label>
                    <textarea
                        name="description"
                        value={newProduct.description}
                        onChange={handleInputChange}
                    />
                </div>
                <div>
                    <label>Price:</label>
                    <input
                        type="number"
                        name="price"
                        value={newProduct.price}
                        onChange={handleInputChange}
                    />
                </div>
                <div>
                    <label>Category:</label>
                    <select
                        name="category"
                        value={newProduct.category.id}
                        onChange={handleInputChange}
                    >
                        <option value="">Select Category</option>
                        {categories.map((category) => (
                            <option key={category.id} value={category.id}>
                                {category.name}
                            </option>
                        ))}
                    </select>
                </div>
                <div>
                    <button type="submit">Create Product</button>
                </div>
            </form>
            {successMessage && <div>{successMessage}</div>}
            {errorMessage && <div>{errorMessage}</div>}
        </div>
    );
};

export default CreateProduct;
