import React from 'react';
import { render, fireEvent, screen } from '@testing-library/react';
import CreateCategory from "../category/createCategory";

jest.mock('../data/CategoryApi', () => ({
    createCategory: jest.fn(),
    getAllCategories: jest.fn().mockResolvedValue([
        { id: 1, name: 'Category 1', parent: { id: null } },
        { id: 2, name: 'Category 2', parent: { id: null } },
    ]),
}));

describe('CreateCategory Component', () => {
    test('renders form fields correctly', async () => {
        render(<CreateCategory />);

        expect(screen.getByLabelText('Category Name')).toBeInTheDocument();
        expect(screen.getByLabelText('Parent Category')).toBeInTheDocument();
        expect(screen.getByRole('button', { name: 'Create Category' })).toBeInTheDocument();
    });

    test('updates input fields correctly', async () => {
        render(<CreateCategory />);

        const nameInput = screen.getByLabelText('Category Name');
        const parentInput = screen.getByLabelText('Parent Category');

        fireEvent.change(nameInput, { target: { value: 'Test Category' } });
        fireEvent.change(parentInput, { target: { value: '1' } });

        expect(nameInput.value).toBe('Test Category');
        expect(parentInput.value).toBe('');
    });

    test('submits form correctly', async () => {
        render(<CreateCategory />);

        const nameInput = screen.getByLabelText('Category Name');
        const parentInput = screen.getByLabelText('Parent Category');
        const submitButton = screen.getByRole('button', { name: 'Create Category' });

        fireEvent.change(nameInput, { target: { value: 'Test Category' } });
        fireEvent.change(parentInput, { target: { value: '1' } });
        fireEvent.click(submitButton);

        expect(nameInput.value).toBe('Test Category');
        expect(parentInput.value).toBe('');
    });
});
