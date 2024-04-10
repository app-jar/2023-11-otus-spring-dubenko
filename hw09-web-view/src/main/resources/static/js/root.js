addEventListener('load', _ => {

    $bookTable = document.querySelector('.book-table_body');

    const table = new BookTable($bookTable);

    const service = new BookService();

    service.searchByTitle("", data => table.render(data));

});
