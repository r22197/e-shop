import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import { getCategoryById, updateCategory } from "../utils/CategoryApi";

const EditCategory = () => {
    const { id } = useParams();
    const [name, setName] = useState("");

    useEffect(() => {
        const fetchData = async () => {
            try {
                const category = await getCategoryById(id);
                setName(category.name);
            } catch (error) {
                console.error("Error fetching category:", error);
            }
        };

        fetchData();
    }, [id]);

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            await updateCategory(id, { name });
            console.log("Category updated successfully");
            // Zde můžete provést přesměrování nebo aktualizaci dat
        } catch (error) {
            console.error("Error updating category:", error);
        }
    };

    return (
        <div>
            <h2>Edit Category</h2>
            <form onSubmit={handleSubmit}>
                <div>
                    <label htmlFor="name">Name:</label>
                    <input
                        type="text"
                        id="name"
                        value={name}
                        onChange={(e) => setName(e.target.value)}
                        required
                    />
                </div>
                <button type="submit">Update</button>
            </form>
        </div>
    );
};

export default EditCategory;
