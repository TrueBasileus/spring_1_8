INSERT INTO authors (name) VALUES
                            ('Leo Tolstoy');


MERGE INTO genres (name) KEY(name) VALUES ('Novel');



INSERT INTO books (title, author_id, genre_id, publication_year) VALUES
    ('War and Peace', 1, 1, 1869);
