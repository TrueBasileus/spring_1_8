INSERT INTO authors (name) VALUES
                                           ('Leo Tolstoy'),
                                           ('Fyodor Dostoevsky'),
                                           ('Jane Austen');

INSERT INTO genres (name) VALUES
                              ('Novel'),
                              ('Science Fiction'),
                              ('Fantasy'),
                              ('Classic');

INSERT INTO books (title, author_id, genre_id, publication_year) VALUES
                                                                     ('War and Peace', 1, 4, 1869),
                                                                     ('Anna Karenina', 1, 4, 1877),
                                                                     ('Crime and Punishment', 2, 4, 1866),
                                                                     ('Pride and Prejudice', 3, 4, 1813);