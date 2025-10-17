import React from 'react'

function BookTableHeader() {
    return (
        <tr>
            <th>ID</th>
            <th>Название</th>
            <th>Автор</th>
            <th>Жанры</th>
            <th>Действия</th>
        </tr>
    )
}

function deleteBookHandler(bookId, rerender) {
    return _ =>
        fetch(`/api/books/${bookId}`, {
            method: 'DELETE',
        }).then(_ => {
            rerender();
        })
}


function BookTableIteButton({book, action, rerender, editBook}) {
    if (action === 'edit') {
        return <button onClick={_ => editBook(book)}>✏</button>
    }
    if (action === 'delete') {
        return (<button onClick={deleteBookHandler(book.id, rerender)}>🗑</button>)
    }
}

function BookTableItem({book, rerender, editBook}) {
    return (<tr>
        <td>{book.id}</td>
        <td>{book.title}</td>
        <td>{book.author.fullName}</td>
        <td>{book.genres.map(x => x.name).join(', ')}</td>
        <td className="actions">
            <BookTableIteButton book={book} action={'edit'} rerender={rerender} editBook={editBook}/>
            <BookTableIteButton book={book} action={'delete'} rerender={rerender}/>
        </td>
    </tr>)
}

function BookEditModal({book, open}) {

    if (!open) {
        return <></>
    }


    function editBookHandle(book) {
        setBookForEdit(book);
        setBookEdit(true);
    }

    return (
        <table>
            <BookTableHeader/>
            <tbody id="book-list">
            {books.map(book => <BookTableItem key={book.id} book={book} rerender={rerender}
                                              editBook={editBookHandle}/>)}
            </tbody>
        </table>
    <BookEditModal book={bookForEdit} open={bookEdit}/>
)
}

export default BookTable;


/*

 */
