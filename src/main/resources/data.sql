INSERT INTO authors (name, birth_date) VALUES
                                           ('Leo Tolstoy', '1828-09-09'),
                                           ('Fyodor Dostoevsky', '1821-11-11'),
                                           ('Jane Austen', '1775-12-16');

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