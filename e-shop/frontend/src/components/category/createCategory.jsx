import React, { useState } from "react";
import { createCategory } from "../utils/CategoryApi";

export const CreateCategory = () => {
    const [newCategory, setNewCategory] = useState({
        name: "",
        parentCategory: null
    });
    const [successMessage, setSuccessMessage] = useState("");
    const [errorMessage, setErrorMessage] = useState("");

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setNewCategory({
            ...newCategory,
            [name]: value
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await createCategory(newCategory.name, newCategory.parentCategory);
            if (response) {
                setSuccessMessage("Category has been created successfully");
                setNewCategory({ name: "", parentCategory: null });
                setErrorMessage("");
            } else {
                setErrorMessage("Error creating category");
            }
        } catch (error) {
            setErrorMessage(error.message);
        }
    };

    return (
        <div>
            <h2>Create Category</h2>
            {errorMessage && <div className="alert alert-danger">{errorMessage}</div>}
            {successMessage && <div className="alert alert-success">{successMessage}</div>}
            <form onSubmit={handleSubmit}>
                <div className="mb-3">
                    <label htmlFor="name" className="form-label">Category Name</label>
                    <input type="text" className="form-control" id="name" name="name" value={newCategory.name} onChange={handleInputChange} required />
                </div>
                <div className="mb-3">
                    <button type="submit" className="btn btn-primary">Create Category</button>
                </div>
            </form>
        </div>
    );
};