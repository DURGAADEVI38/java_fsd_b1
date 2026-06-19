import axios from "axios";
import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
const UserList = () => {
    const [users, setUsers] = useState([])
    const getApi = "https://jsonplaceholder.typicode.com/users";
    useEffect(() => {
        const getUsers = async () => {
            try {
                const resp = await axios.get(getApi)
                setUsers(resp.data)
            }
            catch (err) {
                console.log(err)
            }

        }
        getUsers()

    }, [])
    const deleteId = async (id) => {
        
        const dApi = "https://jsonplaceholder.typicode.com/users/${id}"
        try {
            await axios.delete(dApi);
            const dl = users.filter(u => u.id !== id)
            setUsers(dl);
        }
        catch (err) {
            console.log(err);
        }

    }
    return (
        <div>
            <div className="conatiner">
                <h1> List</h1>
                <Link to="/add" className="btn btn-primary mb-3">
                    Add User
                </Link>
                <table className="table table-bordered">
                    <thead>
                        <tr>
                            <th scope="col">Id</th>
                            <th scope="col">Name</th>
                            <th scope="col">Email</th>
                            <th scope="col">Phone</th>
                            <th scop="col">Company Name</th>
                            <th scope="col">Action</th>
                        </tr>
                    </thead>
                    <tbody>{
                        users

                            .map((user, index) =>
                            (
                                <tr key={index}>
                                    <td>{index + 1}</td>
                                    <td>
                                        {user.name}
                                    </td>
                                    <td>
                                        {user.email}
                                    </td>
                                    <td>
                                        {user.phone}
                                    </td>
                                    <td>
                                        {user.company.name}
                                    </td>
                                    <td>
                                        <button className="btn btn-danger"
                                            onClick={() => deleteId(user.id)}
                                        >
                                            Delete
                                        </button>

                                    </td>
                                </tr>

                            ))
                    }

                    </tbody>
                </table>
            </div>

        </div>
    )
}
export default UserList;