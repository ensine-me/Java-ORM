INSERT INTO materia
(nome)
VALUES
('Matematica'),
('Fisica'),
('Portugues');

INSERT INTO usuario
(is_professor, nome, email, senha, data_nasc)
VALUES

(false, 'Pedro', 'pedro@email.com', '$2a$10$0/TKTGxdREbWaWjWYhwf6e9P1fPOAMMNqEnZgOG95jnSkHSfkkIrC', '2000-07-07'),
(false, 'Filipe', 'filipe@email.com', 'senha1234', '2000-07-07');


--INSERT INTO aula
--(titulo, data_hora, qtd_alunos, status)
--values
--('Aula1', '2023-08-25', 5, 'programada');

