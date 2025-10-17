import React, {useEffect, useState} from 'react'
import BookTable from "./BookTable.component";


function App() {
    let [state, setState] = useState({books: []});

    useEffect(() => {
        updateBooks()
    }, []);

    function updateBooks() {
        fetch('/api/books')
            .then(response => response.json())
            .then(books => setState({books}));
        fetch('/api/genres')
            .then(response => response.json())
            .then(books => setState({books}));
    }

    return (
        <div className="container">
            <h1>Бибилиотека имени котика</h1>

            <BookTable books={state.books} rerender={updateBooks}/>
        </div>
    )

};

export default App;
