const service = new BookService();
const dits = new DictService();

addEventListener('load', _ => {
    $table = document.querySelector('.book-table_body');
    window.table = new BookTable($table);

    $table.addEventListener('click', tableClickListener);

    document.querySelector('.button-search').addEventListener('click', e => {
        updateWithTitleQuery();
    });

    document.querySelector('.book-control_add').addEventListener('click', e => {
        editBook();
    });
    updateWithTitleQuery();
});

function updateWithTitleQuery(titleQuery) {
    if (!titleQuery) {
        titleQuery = document.querySelector('#bookTitleSearch').value;
    }
    service.searchByTitle(titleQuery, data => window.table.render(data));
}

function tableClickListener(e) {
    if(e.target.classList.contains("book-control_delete")) {
        service.deleteBook(e.target.dataset.bookId, () => updateWithTitleQuery());
    };
    if(e.target.classList.contains("book-control_edit")) {
        book = service.getById(e.target.dataset.bookId, editBook);
    };
}

function editBook(book) {
    const $modal = document.querySelector('.book-edit-modal');
    const $id = $modal.querySelector(".id-place");
    const $title =  $modal.querySelector("#book-title-edit");
    const $authors =  $modal.querySelector("#author-select");
    const $genres =  $modal.querySelector("#genre-checkboxes");
    fetchAuthors($authors, () => {
        fetchGenres($genres, () => {
            $id.innerText = '--';
            if (!book) {
                $title.value ="";
            } else {
                $id.innerText = book.id;
                $title.value = book.title;
                $authors.value = book.author.id;
                $genres.querySelectorAll(".genreCheckBox").forEach(x => {
                    book.genres.forEach(g => {
                        if (g.id == x.dataset.genreId) {
                            x.checked = true;
                        }
                    })
                });
            }

            const $content = document.querySelector('.content');
            $content.classList.add('content_blured');
            $modal.classList.add('book-edit-modal_active');
            const btn =  $modal.querySelector(".button-done");
            $modal.querySelector(".button-done").onclick = e => {
                $content.classList.remove('content_blured');
                $modal.classList.remove('book-edit-modal_active');
                save(book);
            }
            $modal.querySelector(".button-cancel").onclick = e => {
                $content.classList.remove('content_blured');
                $modal.classList.remove('book-edit-modal_active');
            }
        });
    });
}

function fetchAuthors($authors, callback) {
    dits.authors().then(resp => resp.json())
    .then(data => {
        $authors.innerHTML = 
        data.map(x => `<option value = "${x.id}">${x.fullName}</option>`).join("\n");
        callback();
    });
}

function fetchGenres($genres, callback) {
    dits.genres().then(resp => resp.json())
    .then(data => {
        $genres.innerHTML = 
        data.map(x => `<input type="checkbox" class="genreCheckBox" data-genre-id="${x.id}" name ="genreCheckBox${x.id}">
        <label for="genreCheckBox${x.id}">${x.name}</label><br>`).join("\n");
        callback();
    });
}

function save(book) {
    const $modal = document.querySelector('.book-edit-modal');
    const $title =  $modal.querySelector("#book-title-edit");
    const $authors =  $modal.querySelector("#author-select");
    const $genres =  $modal.querySelectorAll(".genreCheckBox");
    const genres = [];
    $genres.forEach(x => {
        if (x.checked) {
            genres.push({id: x.dataset.genreId});
        }
    })
    const newBook = {
        id: null,
        title: $title.value,
        author: {
            id: $authors.value
        },
        genres: genres
    };

    if (!book) {
        service.create(newBook, e => updateWithTitleQuery());
    } else {
        newBook.id = book.id;
        service.save(newBook,  e => updateWithTitleQuery());
    }
}
