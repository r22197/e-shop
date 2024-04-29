import React, { useState } from 'react';
import { createCategory } from '../data/CategoryApi';

const CreateCategory = () => {
    const [newCategory, setNewCategory] = useState({ name: '', parent: { id: '' } });
    const [successMessage, setSuccessMessage] = useState('');
    const [errorMessage, setErrorMessage] = useState('');

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await createCategory(newCategory);
            if (response) {
                setSuccessMessage('Category has been created successfully');
                setNewCategory({ name: '', parent: { id: '' } });
            } else {
                setErrorMessage('Error creating category');
            }
        } catch (error) {
            setErrorMessage(error.message);
        }
    };

    return (
        <div>
            <h2>Create Category</h2>
            {errorMessage && <div className="error-message">{errorMessage}</div>}
            <form onSubmit={handleSubmit}>
                <div className="mb-3">
                    <label htmlFor="name" className="form-label">Category Name</label>
                    <input
                        type="text"
                        className="form-control"
                        id="name"
                        name="name"
                        value={newCategory.name}
                        onChange={(e) => setNewCategory({ ...newCategory, name: e.target.value })}
                        required
                    />
                </div>
                <div className="mb-3">
                    <label htmlFor="parent" className="form-label">Parent Category</label>
                    <select
                        className="form-select"
                        id="parent"
                        name="parent"
                        value={newCategory.parent.id}
                        onChange={(e) => setNewCategory({ ...newCategory, parent: { id: e.target.value } })}
                    >
                        <option value="">None</option>
                    </select>
                </div>
                <div className="mb-3">
                    <button type="submit" className="btn btn-primary">Create Category</button>
                </div>
            </form>
            {successMessage && <div className="success-message">{successMessage}</div>}
        </div>
    );
};

export default CreateCategory;
