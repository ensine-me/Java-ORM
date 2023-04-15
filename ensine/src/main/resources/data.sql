INSERT INTO usuario
(is_professor, nome, email, senha, data_nasc)
VALUES
(false, 'Pedro', 'pedro@email.com', 'senha1234', '2000-07-07'),
(false,'Filipe', 'filipe@email.com', 'senha1234', '2000-07-07');

INSERT INTO materia
(nome, usuario_id)
VALUES
('Matematica', 1),
('Matematica', 2);