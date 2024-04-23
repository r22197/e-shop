import apiBaseUrl, {getHeader} from "./apiBaseUrl";

export async function registerUser(registration) {
    try {
        const response = await apiBaseUrl.post("/auth/register-user", registration)
        return response.data
    } catch (error) {
        if (error.reeponse && error.response.data) {
            throw new Error(error.response.data)
        } else {
            throw new Error(`User registration error : ${error.message}`)
        }
    }
}

export async function loginUser(login) {
    try {
        const response = await apiBaseUrl.post("/auth/login", login)
        if (response.status >= 200 && response.status < 300) {
            return response.data
        } else {
            return null
        }
    } catch (error) {
        console.error(error)
        return null
    }
}

export async function getUserProfile(userId, token) {
    try {
        const response = await apiBaseUrl.get(`/auth/users/profile/${userId}`, {
            headers: getHeader()
        })
        return response.data
    } catch (error) {
        throw error
    }
}

export async function deleteUser(userId) {
    try {
        const response = await apiBaseUrl.delete(`/auth/users/delete/${userId}`, {
            headers: getHeader()
        })
        return response.data
    } catch (error) {
        return error.message
    }
}

export async function getUser(userId, token) {
    try {
        const response = await apiBaseUrl.get(`/users/${userId}`, {
            headers: getHeader()
        })
        return response.data
    } catch (error) {
        throw error
    }
}