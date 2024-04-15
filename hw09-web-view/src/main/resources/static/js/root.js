const service = new BookService();

addEventListener('load', _ => {
    $table = document.querySelector('.book-table_body');
    window.table = new BookTable($table);

    $table.addEventListener('click', tableClickListener);

    document.querySelector('.button-search').addEventListener('click', e => {
        const val = document.querySelector('#bookTitleSearch').value;
        updateWithTitleQuery(val);
    });



    updateWithTitleQuery("");
});

function updateWithTitleQuery(titleQuery) {
    service.searchByTitle(titleQuery, data => window.table.render(data));
}

function tableClickListener(e) {
    if(e.target.classList.contains("book-control_delete")) {
        service.deleteBook(e.target.dataset.bookId);
    };
}
