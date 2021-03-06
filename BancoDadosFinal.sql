-- SQL Manager for PostgreSQL 5.9.4.51539
-- ---------------------------------------
-- Host      : localhost
-- Database  : Biblioteca
-- Version   : PostgreSQL 11.1, compiled by Visual C++ build 1914, 64-bit



SET check_function_bodies = false;
--
-- Definition for sequence estante_codigo_seq (OID = 16402) : 
--
SET search_path = public, pg_catalog;
CREATE SEQUENCE public.estante_codigo_seq
    START WITH 1
    INCREMENT BY 1
    MAXVALUE 2147483647
    NO MINVALUE
    CACHE 1;
--
-- Structure for table usuario (OID = 16414) : 
--
CREATE TABLE public.usuario (
    id integer DEFAULT nextval(('public.usuario_id_seq'::text)::regclass) NOT NULL,
    nome varchar NOT NULL,
    senha varchar NOT NULL
)
WITH (oids = false);
--
-- Structure for table livro (OID = 16417) : 
--
CREATE TABLE public.livro (
    id integer DEFAULT nextval(('public.livro_id_seq'::text)::regclass) NOT NULL,
    titulo varchar NOT NULL,
    autor varchar NOT NULL,
    genero varchar NOT NULL,
    ano_lancamento integer NOT NULL,
    numero_paginas integer NOT NULL,
    id_estante integer NOT NULL,
    idioma varchar NOT NULL
)
WITH (oids = false);
--
-- Structure for table estante (OID = 16420) : 
--
CREATE TABLE public.estante (
    id integer DEFAULT nextval(('public.estante_id_seq'::text)::regclass) NOT NULL,
    nome varchar NOT NULL,
    coordenadax integer DEFAULT 0,
    coordenaday integer DEFAULT 0,
    vertical boolean
)
WITH (oids = false);
--
-- Structure for table terminalpesquisa (OID = 16457) : 
--
CREATE TABLE public.terminalpesquisa (
    id integer DEFAULT nextval(('public.terminalpesquisa_id_seq'::text)::regclass) NOT NULL,
    coordenadax integer,
    coordenaday integer
)
WITH (oids = false);
--
-- Definition for sequence estante_id_seq (OID = 16463) : 
--
CREATE SEQUENCE public.estante_id_seq
    START WITH 1
    INCREMENT BY 1
    MAXVALUE 2147483647
    NO MINVALUE
    CACHE 1;
--
-- Definition for sequence livro_id_seq (OID = 16468) : 
--
CREATE SEQUENCE public.livro_id_seq
    START WITH 1
    INCREMENT BY 1
    MAXVALUE 2147483647
    NO MINVALUE
    CACHE 1;
--
-- Definition for sequence usuario_id_seq (OID = 16473) : 
--
CREATE SEQUENCE public.usuario_id_seq
    START WITH 1
    INCREMENT BY 1
    MAXVALUE 2147483647
    NO MINVALUE
    CACHE 1;
--
-- Definition for sequence terminalpesquisa_id_seq (OID = 16478) : 
--
CREATE SEQUENCE public.terminalpesquisa_id_seq
    START WITH 1
    INCREMENT BY 1
    MAXVALUE 2147483647
    NO MINVALUE
    CACHE 1;
--
-- Data for table public.usuario (OID = 16414) (LIMIT 0,2)
--
INSERT INTO usuario (id, nome, senha)
VALUES (6, '123', '123');

INSERT INTO usuario (id, nome, senha)
VALUES (7, '44', '44');

--
-- Data for table public.livro (OID = 16417) (LIMIT 0,51)
--
INSERT INTO livro (id, titulo, autor, genero, ano_lancamento, numero_paginas, id_estante, idioma)
VALUES (1, 'A Catedral e o Bazar', 'Eric Steven', 'Não-ficção', 1999, 241, 1, 'Português');

INSERT INTO livro (id, titulo, autor, genero, ano_lancamento, numero_paginas, id_estante, idioma)
VALUES (2, 'Free Culture', 'Lawrence Lessig', 'Não-ficção', 2004, 231, 2, 'Inglês');

INSERT INTO livro (id, titulo, autor, genero, ano_lancamento, numero_paginas, id_estante, idioma)
VALUES (3, 'Inteligência Artificial', 'Peter Norvig', 'Informática', 1994, 1152, 1, 'Português');

INSERT INTO livro (id, titulo, autor, genero, ano_lancamento, numero_paginas, id_estante, idioma)
VALUES (5, 'Algoritmos: Teoria e Prática', 'Thomas Cormen', 'Informática', 1989, 179, 1, 'Português');

INSERT INTO livro (id, titulo, autor, genero, ano_lancamento, numero_paginas, id_estante, idioma)
VALUES (4, 'Logica em C. da Computação', 'Michael Huth', 'Informática', 2000, 427, 1, 'Português');

INSERT INTO livro (id, titulo, autor, genero, ano_lancamento, numero_paginas, id_estante, idioma)
VALUES (6, 'The C Programming', 'Dennis Ritchie', 'Não-ficção', 1978, 321, 1, 'Inglês');

INSERT INTO livro (id, titulo, autor, genero, ano_lancamento, numero_paginas, id_estante, idioma)
VALUES (7, 'The Art of Computer Programming', 'Donald Knuth', 'Não-ficção', 1968, 516, 1, 'Inglês');

INSERT INTO livro (id, titulo, autor, genero, ano_lancamento, numero_paginas, id_estante, idioma)
VALUES (8, 'A Ciência da informação', 'Yves-François Le Coadic', 'Informática', 1996, 279, 2, 'Português');

INSERT INTO livro (id, titulo, autor, genero, ano_lancamento, numero_paginas, id_estante, idioma)
VALUES (9, 'Arquivos Modernos', 'Theodore Roosevelt', 'Não-ficção', 1998, 322, 2, 'Português');

INSERT INTO livro (id, titulo, autor, genero, ano_lancamento, numero_paginas, id_estante, idioma)
VALUES (10, 'Arquivo: teoria e prática', 'Marilena Leite Paes', 'Informática', 1996, 333, 2, 'Português');

INSERT INTO livro (id, titulo, autor, genero, ano_lancamento, numero_paginas, id_estante, idioma)
VALUES (11, 'Estudos de linguagem', 'Vera Regina', 'Não-ficção', 2011, 213, 2, 'Português');

INSERT INTO livro (id, titulo, autor, genero, ano_lancamento, numero_paginas, id_estante, idioma)
VALUES (12, 'Aprendizagem, Linguagem e Pensamento', 'Vera Regina', 'Literatura', 2001, 412, 2, 'Português');

INSERT INTO livro (id, titulo, autor, genero, ano_lancamento, numero_paginas, id_estante, idioma)
VALUES (14, 'Orgulho e Preconceito', 'Jane Austen', 'Romance', 1813, 898, 3, 'Português');

INSERT INTO livro (id, titulo, autor, genero, ano_lancamento, numero_paginas, id_estante, idioma)
VALUES (15, 'Admirável Mundo Novo', 'Aldous Huxley', 'Ficção', 1932, 732, 3, 'Português');

INSERT INTO livro (id, titulo, autor, genero, ano_lancamento, numero_paginas, id_estante, idioma)
VALUES (16, 'O Grande Gatsby', 'F. Scott Fitzgerald', 'Ficção', 1925, 788, 3, 'Português');

INSERT INTO livro (id, titulo, autor, genero, ano_lancamento, numero_paginas, id_estante, idioma)
VALUES (18, 'The Curious Case of Benjamin Button', 'F. Scott Fitzgerald', 'Conto', 1922, 1019, 3, 'Inglês');

INSERT INTO livro (id, titulo, autor, genero, ano_lancamento, numero_paginas, id_estante, idioma)
VALUES (19, 'The Crack-Up ', 'F. Scott Fitzgerald', 'Conto', 1936, 347, 3, 'Inglês');

INSERT INTO livro (id, titulo, autor, genero, ano_lancamento, numero_paginas, id_estante, idioma)
VALUES (20, 'O Alienista', 'Machado de Assis', 'Conto', 1882, 729, 3, 'Português');

INSERT INTO livro (id, titulo, autor, genero, ano_lancamento, numero_paginas, id_estante, idioma)
VALUES (21, 'Esaú e Jacó ', 'Machado de Assis', 'Ficção', 1904, 378, 3, 'Português');

INSERT INTO livro (id, titulo, autor, genero, ano_lancamento, numero_paginas, id_estante, idioma)
VALUES (22, 'Dom Casmurro', 'Machado de Assis', 'Conto', 1899, 762, 3, 'Português');

INSERT INTO livro (id, titulo, autor, genero, ano_lancamento, numero_paginas, id_estante, idioma)
VALUES (23, 'Conto de escola', 'Machado de Assis', 'Ficção', 2002, 178, 4, 'Português');

INSERT INTO livro (id, titulo, autor, genero, ano_lancamento, numero_paginas, id_estante, idioma)
VALUES (25, 'A Borboleta Azul', 'Lenira Almeida Heck', 'Conto', 2015, 202, 4, 'Português');

INSERT INTO livro (id, titulo, autor, genero, ano_lancamento, numero_paginas, id_estante, idioma)
VALUES (26, 'A Bruxa e o Caldeirão', 'José Leon Machado', 'Conto', 2013, 78, 4, 'Português');

INSERT INTO livro (id, titulo, autor, genero, ano_lancamento, numero_paginas, id_estante, idioma)
VALUES (27, 'O Gato de Botas', 'Charles Perrault', 'Ficção', 1697, 279, 4, 'Português');

INSERT INTO livro (id, titulo, autor, genero, ano_lancamento, numero_paginas, id_estante, idioma)
VALUES (28, 'Cinderela', 'Charles Perrault', 'Conto', 1697, 378, 4, 'Português');

INSERT INTO livro (id, titulo, autor, genero, ano_lancamento, numero_paginas, id_estante, idioma)
VALUES (29, 'Branca de Neve', 'Trina Schart Hyman', 'Ficção', 1817, 321, 4, 'Português');

INSERT INTO livro (id, titulo, autor, genero, ano_lancamento, numero_paginas, id_estante, idioma)
VALUES (30, 'Alice no País das Maravilhas', 'Lewis Carroll', 'Ficção', 1865, 218, 4, 'Português');

INSERT INTO livro (id, titulo, autor, genero, ano_lancamento, numero_paginas, id_estante, idioma)
VALUES (31, 'João e Maria ', 'Engelbert Humperdinck', 'Conto', 1971, 461, 4, 'Português');

INSERT INTO livro (id, titulo, autor, genero, ano_lancamento, numero_paginas, id_estante, idioma)
VALUES (32, 'Chapeuzinho Vermelho', 'Charles Perrault', 'Conto', 1701, 461, 4, 'Português');

INSERT INTO livro (id, titulo, autor, genero, ano_lancamento, numero_paginas, id_estante, idioma)
VALUES (13, 'Dom Quixote', 'Miguel de Cervantes', 'Romance', 1615, 618, 3, 'Português');

INSERT INTO livro (id, titulo, autor, genero, ano_lancamento, numero_paginas, id_estante, idioma)
VALUES (17, 'Este Lado do Paraíso', 'F. Scott Fitzgerald', 'Romance', 1920, 189, 3, 'Português');

INSERT INTO livro (id, titulo, autor, genero, ano_lancamento, numero_paginas, id_estante, idioma)
VALUES (24, 'A Cartomante', 'Machado de Assis', 'Romance', 1884, 210, 4, 'Português');

INSERT INTO livro (id, titulo, autor, genero, ano_lancamento, numero_paginas, id_estante, idioma)
VALUES (33, 'O Suicídio', 'Émile Durkheim', 'Conto', 1897, 887, 5, 'Português');

INSERT INTO livro (id, titulo, autor, genero, ano_lancamento, numero_paginas, id_estante, idioma)
VALUES (34, 'A Ética Protestante', 'Max Weber', 'Economia', 1905, 199, 5, 'Português');

INSERT INTO livro (id, titulo, autor, genero, ano_lancamento, numero_paginas, id_estante, idioma)
VALUES (35, 'Da Divisão do Trabalho Social', 'Émile Durkheim', 'Economia', 1893, 471, 5, 'Português');

INSERT INTO livro (id, titulo, autor, genero, ano_lancamento, numero_paginas, id_estante, idioma)
VALUES (36, 'Lições De Sociologia', 'Émile Durkheim', 'Conto', 1904, 675, 5, 'Português');

INSERT INTO livro (id, titulo, autor, genero, ano_lancamento, numero_paginas, id_estante, idioma)
VALUES (37, 'O Eu e o Id', 'Sigmund Freud', 'Ficção', 1923, 693, 5, 'Português');

INSERT INTO livro (id, titulo, autor, genero, ano_lancamento, numero_paginas, id_estante, idioma)
VALUES (38, 'Jenseits des Lustprinzips', 'Sigmund Freud', 'Monografia', 1920, 712, 5, 'Alemão');

INSERT INTO livro (id, titulo, autor, genero, ano_lancamento, numero_paginas, id_estante, idioma)
VALUES (39, 'Totem e Tabu', 'Sigmund Freud', 'Conto', 1923, 451, 5, 'Português');

INSERT INTO livro (id, titulo, autor, genero, ano_lancamento, numero_paginas, id_estante, idioma)
VALUES (40, 'O inquietante', 'Sigmund Freud', 'Biografia', 1919, 217, 5, 'Português');

INSERT INTO livro (id, titulo, autor, genero, ano_lancamento, numero_paginas, id_estante, idioma)
VALUES (41, 'Luto e melancolia', 'Sigmund Freud', 'Conto', 1917, 333, 6, 'Português');

INSERT INTO livro (id, titulo, autor, genero, ano_lancamento, numero_paginas, id_estante, idioma)
VALUES (42, 'Modernidade Líquida', 'Zygmunt Bauman', 'Não-ficção', 1999, 632, 5, 'Português');

INSERT INTO livro (id, titulo, autor, genero, ano_lancamento, numero_paginas, id_estante, idioma)
VALUES (43, 'Vigiar e Punir', 'Michel Foucault', 'Monografia', 318, 117, 5, 'Português');

INSERT INTO livro (id, titulo, autor, genero, ano_lancamento, numero_paginas, id_estante, idioma)
VALUES (44, 'Os Donos do Poder', 'Raimundo Faoro', 'Monografia', 1958, 319, 5, 'Português');

INSERT INTO livro (id, titulo, autor, genero, ano_lancamento, numero_paginas, id_estante, idioma)
VALUES (45, 'O Demônio do Meio-Dia', 'Andrew Solomon', 'Conto', 2011, 269, 6, 'Português ');

INSERT INTO livro (id, titulo, autor, genero, ano_lancamento, numero_paginas, id_estante, idioma)
VALUES (46, 'Em Busca de Sentido', 'Viktor Frankl', 'Monografia', 1946, 211, 6, 'Português ');

INSERT INTO livro (id, titulo, autor, genero, ano_lancamento, numero_paginas, id_estante, idioma)
VALUES (47, 'Mindset', 'Carol Dweck', 'Auto-ajuda', 2006, 212, 6, 'Inglês ');

INSERT INTO livro (id, titulo, autor, genero, ano_lancamento, numero_paginas, id_estante, idioma)
VALUES (48, 'A Interpretação dos Sonhos', 'Sigmund Freud', 'Biografia', 1900, 969, 6, 'Português ');

INSERT INTO livro (id, titulo, autor, genero, ano_lancamento, numero_paginas, id_estante, idioma)
VALUES (50, 'Rápido e Devagar', 'Daniel Kahneman', 'Não-ficção', 2011, 401, 6, 'Português ');

INSERT INTO livro (id, titulo, autor, genero, ano_lancamento, numero_paginas, id_estante, idioma)
VALUES (51, 'O Mal-estar na Civilização', 'Sigmund Freud', 'Economia', 1930, 127, 6, 'Português ');

INSERT INTO livro (id, titulo, autor, genero, ano_lancamento, numero_paginas, id_estante, idioma)
VALUES (52, 'O Poder do Hábito', 'Charles Duhigg', 'Auto-ajuda', 2012, 189, 6, 'Português');

--
-- Data for table public.estante (OID = 16420) (LIMIT 0,7)
--
INSERT INTO estante (id, nome, coordenadax, coordenaday, vertical)
VALUES (7, 'teste', 545, 283, false);

INSERT INTO estante (id, nome, coordenadax, coordenaday, vertical)
VALUES (1, 'C. da Computação', 536, 440, false);

INSERT INTO estante (id, nome, coordenadax, coordenaday, vertical)
VALUES (2, 'C. da Informação', 544, 377, false);

INSERT INTO estante (id, nome, coordenadax, coordenaday, vertical)
VALUES (3, 'Literatura', 780, 382, false);

INSERT INTO estante (id, nome, coordenadax, coordenaday, vertical)
VALUES (4, 'Literatura Infantil', 779, 448, false);

INSERT INTO estante (id, nome, coordenadax, coordenaday, vertical)
VALUES (5, 'Sociologia', 687, 254, true);

INSERT INTO estante (id, nome, coordenadax, coordenaday, vertical)
VALUES (6, 'Psicologia', 811, 245, true);

--
-- Data for table public.terminalpesquisa (OID = 16457) (LIMIT 0,1)
--
INSERT INTO terminalpesquisa (id, coordenadax, coordenaday)
VALUES (5, 668, 535);

--
-- Definition for index estante_pkey (OID = 16465) : 
--
ALTER TABLE ONLY estante
    ADD CONSTRAINT estante_pkey
    PRIMARY KEY (id);
--
-- Definition for index livros_pkey (OID = 16470) : 
--
ALTER TABLE ONLY livro
    ADD CONSTRAINT livros_pkey
    PRIMARY KEY (id);
--
-- Definition for index usuario_pkey (OID = 16475) : 
--
ALTER TABLE ONLY usuario
    ADD CONSTRAINT usuario_pkey
    PRIMARY KEY (id);
--
-- Definition for index terminalPesquisa_pkey (OID = 16480) : 
--
ALTER TABLE ONLY terminalpesquisa
    ADD CONSTRAINT "terminalPesquisa_pkey"
    PRIMARY KEY (id);
--
-- Data for sequence public.estante_codigo_seq (OID = 16402)
--
SELECT pg_catalog.setval('estante_codigo_seq', 1, false);
--
-- Data for sequence public.estante_id_seq (OID = 16463)
--
SELECT pg_catalog.setval('estante_id_seq', 7, true);
--
-- Data for sequence public.livro_id_seq (OID = 16468)
--
SELECT pg_catalog.setval('livro_id_seq', 52, true);
--
-- Data for sequence public.usuario_id_seq (OID = 16473)
--
SELECT pg_catalog.setval('usuario_id_seq', 7, true);
--
-- Data for sequence public.terminalpesquisa_id_seq (OID = 16478)
--
SELECT pg_catalog.setval('terminalpesquisa_id_seq', 5, true);
--
-- Comments
--
COMMENT ON SCHEMA public IS 'standard public schema';
