package ru.otus.hw.models.view;


public interface CommentView {

    long getId();

    String getText();

    BookView getBook();

    interface BookView {

        String getTitle();
    }

}
