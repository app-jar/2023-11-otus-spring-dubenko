class BookTable {

    constructor($root) {
        this.$root = $root;
    }
 
    render(data) {
        this.$root.innerHTML = '';
        data.forEach(book => this.renderBook(book));
    }
 
    renderBook(book) {
        const $row = document.createElement('tr');
        $row.innerHTML = `
            <td>${book.id}</td>
            <td>${book.title}</td>
            <td>${book.author.fullName}</td>
            <td><ul>${book.genres.map(x => `<li>${x.name}</li>`).join("")}</ul></td>
            <td><img src="/icon/pen-svgrepo-com.svg" class="book-control book-control_edit", data-book-id="${book.id}"></img></td>
            <td><img src="/icon/delete-left-svgrepo-com.svg" class="book-control book-control_delete", data-book-id="${book.id}"></img></td>
            <td><a href="/books/comments"><img src="/icon/comment-dots-svgrepo-com.svg" 
            class="book-control", data-book-id="${book.id}"></img></a></td>
        `;
        this.$root.appendChild($row);
    }
}

class BookService {
    searchByTitle(title, callback) {
        return fetch("api/v1/books/?" + new URLSearchParams({title: title}), {
                method: 'GET',
                headers: {
                  'Accept': 'application/json',
                  'Content-Type': 'application/json'
                }
            })
            .then(resp => resp.json())
            .then(data => callback(data));
    }

    getById(bookId, callback) {
        return fetch("api/v1/books/" + bookId, {
                method: 'GET',
                headers: {
                  'Accept': 'application/json',
                  'Content-Type': 'application/json'
                }
            })
            .then(resp => resp.json())
            .then(data => callback(data));
    }

    deleteBook(id, callback) {
        return fetch(`api/v1/books/${id}`, {
            method: 'DELETE'
        })
        .then(resp => callback())
    }

    save(book, callback) {
        return fetch(`api/v1/books/`, {
            method: 'PUT',
            body: JSON.stringify(book),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
              }
        })
        .then(resp => resp.json())
        .then(data => callback(data));
    }

    create(book, callback) {
        return fetch(`api/v1/books/`, {
            method: 'POST',
            body: JSON.stringify(book),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
              }
        })
        .then(resp => resp.json())
        .then(data => callback(data));
    }
}

class DictService {
    async authors() {
        return await fetch("api/v1/authors", {
                method: 'GET',
                headers: {
                  'Accept': 'application/json',
                  'Content-Type': 'application/json'
                }
            });
    }

    async genres() {
        return await fetch("api/v1/genres", {
                method: 'GET',
                headers: {
                  'Accept': 'application/json',
                  'Content-Type': 'application/json'
                }
            });
    }
}