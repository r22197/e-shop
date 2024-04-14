import React, { useState, useEffect } from "react";
import { createCategory, getAllCategories } from "../utils/CategoryApi";

export const CreateCategory = () => {
    const [newCategory, setNewCategory] = useState({
        name: "",
        parent: ""
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
            setErrorMessage("Error fetching categories: " + error.message);
        }
    };

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
            console.log(newCategory)
            const response = await createCategory(newCategory);
            if (response) {
                setSuccessMessage("Category has been created successfully");
                setNewCategory({ name: "", parent: {id: ""} });
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
                    <label htmlFor="parent" className="form-label">Parent Category</label>
                    <select
                        className="form-select"
                        name="parent"
                        value={newCategory.parent.id}
                        onChange={handleInputChange}>

                        <option value="">None</option>
                        {categories.map((category) => (
                            <option key={category.id} value={category.id}>
                                {category.name}
                            </option>
                        ))}
                    </select>
                </div>
                <div className="mb-3">
                    <button type="submit" className="btn btn-primary">Create Category</button>
                </div>
            </form>
        </div>
    );
};

export default CreateCategory;
