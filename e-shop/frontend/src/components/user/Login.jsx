import React, { useState } from "react"
import { Link, useNavigate } from "react-router-dom"
import { useAuth } from "./AuthProvider";
import { loginUser } from "../data/UserApi";

const Login = () => {
    const [errorMessage, setErrorMessage] = useState("")
    const [login, setLogin] = useState({
        email: "",
        password: ""
    })

    const navigate = useNavigate()
    const auth = useAuth()

    const handleInputChange = (e) => {
        setLogin({ ...login, [e.target.name]: e.target.value })
    }

    const handleSubmit = async (e) => {
        e.preventDefault()
        const success = await loginUser(login)
        if (success) {
            const token = success.token
            auth.handleLogin(token)
            navigate(`/`)

        } else {
            setErrorMessage("Invalid username or password. Please try again.")
        }
    }

    return (
        <section className="container col-6">
            {errorMessage && <p className="alert alert-danger">{errorMessage}</p>}
            <h2 className="text-center">Přihlásit se</h2>
            <form onSubmit={handleSubmit}>
                <div className="row mb-3">
                    <div>
                        <input
                            placeholder="Email"
                            id="email"
                            name="email"
                            type="email"
                            className="form-control"
                            value={login.email}
                            onChange={handleInputChange}
                        />
                    </div>
                </div>

                <div className="row mb-3">
                    <div>
                        <input
                            placeholder="Heslo"
                            id="password"
                            name="password"
                            type="password"
                            className="form-control"
                            value={login.password}
                            onChange={handleInputChange}
                        />
                    </div>
                </div>

                <div className="mb-3">
                    <button className="mb-4 d-block btn btn-primary" type="submit"
                            style={{height: '50px', width: '100%'}}>Přihlásit
                    </button>
                    <div className="text-center">
                        <p>Nemáš účet? <Link to={"/register"}>Zaregistrovat se</Link></p>
                    </div>
                </div>
            </form>
        </section>
    )
}

export default Login
