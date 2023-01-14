'use strict';

getTableUser()

function getTableUser() {
    fetch("http://localhost:8080/api/users")
        .then(res => res.json())
        .then(js => {
            js.forEach(item => {
                const users = `$(
                    <tr>
                        <td id="userID">${item.id}</td>
                        <td>${item.firstName}</td>
                        <td>${item.lastName}</td>  
                        <td>${item.age}</td>
                        <td>${item.email}</td>
                        <td>${item.shortRole}</td>
                        <td>
                            <button type="button" class="btn btn-info" data-toggle="modal" data-target="#edit">
                            Edit
                            </button>
                        </td>
                        <td>
                            <button type="button" class="btn btn-danger" data-toggle="modal" data-target="#delete">
                                Delete
                            </button>
                        </td>
                    </tr>)`;
            $('#AllUsers').append(users)
            })
        })
}