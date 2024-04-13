import React, { useState } from "react";
import { updateCategory } from "../utils/CategoryApi";

const UpdateCategory = ({ categoryId }) => {
    const [categoryName, setCategoryName] = useState("");
    const [successMessage, setSuccessMessage] = useState("");
    const [errorMessage, setErrorMessage] = useState("");

    const handleInputChange = (e) => {
        setCategoryName(e.target.value);
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await updateCategory(categoryId, { name: categoryName });
            if (response) {
                setSuccessMessage("Category has been updated successfully");
                setCategoryName("");
            } else {
                setErrorMessage("Error while updating category");
            }
        } catch (error) {
            setErrorMessage("Error while updating category: " + error.message);
        }
    };

    return (
        <div>
            <h2>Update Category</h2>
            <form onSubmit={handleSubmit}>
                <div>
                    <label>Category Name:</label>
                    <input
                        type="text"
                        value={categoryName}
                        onChange={handleInputChange}
                    />
                </div>
                <div>
                    <button type="submit">Update Category</button>
                </div>
            </form>
            {successMessage && <div>{successMessage}</div>}
            {errorMessage && <div>{errorMessage}</div>}
        </div>
    );
};

export default UpdateCategory;
