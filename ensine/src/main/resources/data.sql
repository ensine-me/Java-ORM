INSERT INTO materia
(nome)
VALUES
('Matematica'),
('Fisica'),
('Artes'),
('Filosofia'),
('Sociologia'),
('Lingua Inglesa'),
('Quimica'),
('Biologia'),
('Geografia'),
('Historia'),
('Lingua Portuguesa');

INSERT INTO usuario
(id_usuario, is_professor, nome, email, senha, foto, google_email)
VALUES
(1, true, 'Filipe Filipus', 'filipe.guiraldini@gmail.com', '$2a$10$gdKQIEYE975gYtTsWhDzc.HYSrh3GlbO6pc1xlZ79h5ZnDHpKJ9sa', 'https://i.imgur.com/r4EPYsb.jpg', 'filipe.guiraldini@gmail.com'),
(2, true, 'Alessandra Ribeiro', 'teste1@email.com', '12345', 'https://img.freepik.com/fotos-gratis/jovem-empresaria-afro-americana-sorrindo_74855-4088.jpg?w=740&t=st=1694654957~exp=1694655557~hmac=2909dc5b886365e520fbbace1d5decf774436c4e478fbe5164c7708a85c835e6', ''),
(3, true, 'Alessandra Moura', 'teste2@email.com', '12345', 'https://img.freepik.com/fotos-gratis/jovem-professora-de-retrato_23-2148635365.jpg?w=740&t=st=1694654813~exp=1694655413~hmac=7af75ae6784032cf5a2c482e1d4d7ffbee11374f71891f6b4e906dfdb1953bbf', ''),
(4, true, 'Alessandro Oliveira', 'teste3@email.com', '12345', 'https://img.freepik.com/fotos-gratis/closeup-retrato-de-uma-professora-caucasiana-feliz-de-oculos_74855-9736.jpg?w=740&t=st=1694654846~exp=1694655446~hmac=b2aa97bd25a7f5a705ef448f67ed7b17b4ae198404a50b7233e438ac5ac7e0fa', ''),
(5, true, 'Alessandro Molario', 'teste4@email.com', '12345', 'https://img.freepik.com/fotos-gratis/sorrindo-feliz-homem-de-camisa-olhando-para-o-espaco-da-copia_171337-10785.jpg?w=740&t=st=1694654858~exp=1694655458~hmac=32eae557f112c179258911c17e7857b10279633a166a5a468721c9da482db530', ''),
(6, true, 'Alexandre Carlos', 'teste5@email.com', '12345', 'https://img.freepik.com/fotos-gratis/homem-de-negocios-serio-com-o-rosto-danificado_1098-1085.jpg?w=360&t=st=1694654867~exp=1694655467~hmac=c01dc4217725049996ae6d8178e8f6ba6217e37282c850198e7537d9609fac68', ''),
(7, true, 'Mathilda Cruz', 'teste6@email.com', '12345', 'https://img.freepik.com/fotos-gratis/mulher-de-negocios-no-retrato-dos-vidros_1262-1461.jpg?w=740&t=st=1694654831~exp=1694655431~hmac=ef298914d6aded06e77c17b4d14bd0f001532604c60f3323bc9e7bd9200289a0', ''),
(8, true, 'Matheus Rodrigues', 'teste7@email.com', '12345', 'https://img.freepik.com/fotos-gratis/homem-usando-oculos-redondos-e-camiseta-casual_273609-19641.jpg?w=740&t=st=1694655076~exp=1694655676~hmac=d1edee50e02f8ea4694c100ed8a3ede4ef36ab3cc6fa8ba4102986101f697357', ''),
(9, true, 'Matheus Callegari', 'teste8@email.com', '12345', 'https://img.freepik.com/fotos-gratis/gerente-de-negocios-maduros-no-escritorio_1098-21368.jpg?w=360&t=st=1694654913~exp=1694655513~hmac=121c852a37ef487d8c9334b99d73270e2f0fa1e48a0067d983a5ec5cf9a345d9', ''),
(10, true, 'Carlos Roberto', 'teste9@email.com', '12345', 'https://img.freepik.com/fotos-gratis/cara-de-mundoface-japones-em-um-fundo-branco_53876-31202.jpg?w=740&t=st=1694655100~exp=1694655700~hmac=164f83dcf4e045f1396b4f63dff4ce50112b17d71a7eb16aa86f74b221b79553', '')
ON CONFLICT (id_usuario) DO NOTHING;
SELECT setval('usuario_id_usuario_seq', (SELECT MAX(id_usuario) FROM usuario));

INSERT INTO professor
(descricao, preco_hora_aula, usuario_id)
VALUES
('Gosto de ensinar e ajudar alunos a atingir seus objetivos', 20.00, 1),
('Gosto de ensinar', 15.00, 2),
('Gosto de ensinar', 29.00, 3),
('Gosto de ensinar', 38.00, 4),
('Gosto de ensinar', 16.00, 5),
('Gosto de ensinar', 25.00, 6),
('Gosto de ensinar', 31.00, 7),
('Gosto de ensinar', 12.00, 8),
('Gosto de ensinar', 28.00, 9),
('Gosto de ensinar', 37.00, 10);

INSERT INTO usuario_materia
(usuarios_id_usuario, materias_id)
VALUES
(1, 1),
(2, 1),
(3, 1),
(3, 11),
(4, 2),
(5, 3),
(6, 5),
(6, 10),
(7, 4),
(8, 3),
(9, 1),
(9, 2),
(10, 4);

INSERT INTO disponibilidade
(dia_da_semana, horario_fim, horario_inicio, professor_usuario_id)
VALUES
(5, '23:59:59', '00:00:00', 1),
(1, '23:59:59', '00:00:00', 1),
(2, '23:59:59', '00:00:00', 1),
(5, '23:59:59', '00:00:00', 2),
(5, '23:59:59', '00:00:00', 3),
(5, '23:59:59', '00:00:00', 4),
(5, '23:59:59', '00:00:00', 5),
(5, '23:59:59', '00:00:00', 6),
(5, '23:59:59', '00:00:00', 7),
(5, '23:59:59', '00:00:00', 8),
(5, '23:59:59', '00:00:00', 9),
(5, '23:59:59', '00:00:00', 10);

INSERT INTO aula
(id, data_hora, data_hora_fim, descricao, duracao_segundos, limite_participantes, privacidade, titulo, materia_id, professor_usuario_id, status)
VALUES
(1, '2023-09-16 17:39:01', '2023-09-16 19:39:01', 'descricao', 3600, 5, 0, 'Logaritmo', 1, 1, 4),
(2, '2023-09-16 17:39:01', '2023-09-16 19:39:01', 'descricao', 3600, 4, 0, 'Trigonometria', 1, 2, 4),
(3, '2023-09-16 17:39:01', '2023-09-16 19:39:01', 'descricao', 3600, 5, 0, 'Algebra', 1, 3, 0),
(4, '2023-09-16 17:39:01', '2023-09-16 19:39:01', 'descricao', 3600, 2, 0, 'Equação 2º Grau', 1, 9, 4),
(5, '2023-09-16 17:39:01', '2023-09-16 19:39:01', 'descricao', 3600, 4, 0, 'Karl Marx', 4, 2, 4),
(6, '2023-09-16 17:39:01', '2023-09-16 19:39:01', 'descricao', 3600, 3, 0, 'Mais Valia', 5, 3, 0),
(7, '2023-09-16 17:39:01', '2023-09-16 19:39:01', 'descricao', 3600, 4, 0, 'Valencia e Covalencia', 7, 4, 0),
(8, '2023-09-16 17:39:01', '2023-09-16 19:39:01', 'descricao', 3600, 2, 0, 'Verbo To Be', 6, 5, 0),
(9, '2023-09-16 17:39:01', '2023-09-16 19:39:01', 'descricao', 3600, 5, 0, 'Verbo To Be+', 6, 10, 0),
(10, '2023-09-16 17:39:01', '2023-09-16 19:39:01', 'testando ', 3600, 5, 0, 'Matematicatrie', 1, 1, 2),

-- Matemática mês 9
(11, '2023-09-16 17:39:01', '2023-09-16 19:39:01', 'descricao', 3600, 5, 0, 'Logaritmo', 1, 1, 0),
(12, '2023-09-16 17:39:01', '2023-09-16 19:39:01', 'descricao', 3600, 4, 0, 'Trigonometria', 1, 2, 2),
(13, '2023-09-16 17:39:01', '2023-09-16 19:39:01', 'descricao', 3600, 5, 0, 'Algebra', 1, 3, 0),

-- Matemática mês 10
(14, '2023-10-16 17:39:01', '2023-10-16 19:39:01', 'descricao', 3600, 5, 0, 'Logaritmo', 1, 1, 2),
(15, '2023-10-16 17:39:01', '2023-10-16 19:39:01', 'descricao', 3600, 4, 0, 'Trigonometria', 1, 2, 0),
(16, '2023-10-16 17:39:01', '2023-10-16 19:39:01', 'descricao', 3600, 5, 0, 'Algebra', 1, 3, 2),
(17, '2023-10-16 17:39:01', '2023-10-16 19:39:01', 'descricao', 3600, 2, 0, 'Equação 2º Grau', 1, 9, 0),

-- Matemática mês 11
(18, '2023-11-16 17:39:01', '2023-11-16 19:39:01', 'descricao', 3600, 5, 0, 'Logaritmo', 1, 1, 2),
(19, '2023-11-16 17:39:01', '2023-11-16 19:39:01', 'descricao', 3600, 4, 0, 'Trigonometria', 1, 2, 0),
(20, '2023-11-16 17:39:01', '2023-11-16 19:39:01', 'descricao', 3600, 5, 0, 'Algebra', 1, 3, 2),
(21, '2023-11-16 17:39:01', '2023-11-16 19:39:01', 'descricao', 3600, 2, 0, 'Equação 2º Grau', 1, 9, 0),
(22, '2023-11-16 17:39:01', '2023-11-16 19:39:01', 'descricao', 3600, 2, 0, 'Equação 2º Grau', 1, 9, 2),

-- Lingua Portuguesa mês 9
(23, '2023-09-16 17:39:01', '2023-09-16 19:39:01', 'descricao', 3600, 5, 0, 'Logaritmo', 11, 1, 0),
(24, '2023-09-16 17:39:01', '2023-09-16 19:39:01', 'descricao', 3600, 4, 0, 'Trigonometria', 11, 2, 2),
(25, '2023-09-16 17:39:01', '2023-09-16 19:39:01', 'descricao', 3600, 5, 0, 'Algebra', 11, 3, 0),
(26, '2023-09-16 17:39:01', '2023-09-16 19:39:01', 'descricao', 3600, 2, 0, 'Equação 2º Grau', 11, 9, 2),

-- Lingua Portuguesa mês 10
(27, '2023-10-16 17:39:01', '2023-10-16 19:39:01', 'descricao', 3600, 5, 0, 'Logaritmo', 11, 1, 0),
(28, '2023-10-16 17:39:01', '2023-10-16 19:39:01', 'descricao', 3600, 4, 0, 'Trigonometria', 11, 2, 2),
(29, '2023-10-16 17:39:01', '2023-10-16 19:39:01', 'descricao', 3600, 5, 0, 'Algebra', 11, 3, 0),
(30, '2023-10-16 17:39:01', '2023-10-16 19:39:01', 'descricao', 3600, 2, 0, 'Equação 2º Grau', 11, 9, 2),
(31, '2023-10-16 17:39:01', '2023-10-16 19:39:01', 'descricao', 3600, 2, 0, 'Equação 2º Grau', 11, 9, 0),

-- Lingua Portuguesa mês 11
(32, '2023-11-16 17:39:01', '2023-11-16 19:39:01', 'descricao', 3600, 5, 0, 'Logaritmo', 11, 1, 4),
(33, '2023-11-16 17:39:01', '2023-11-16 22:39:01', 'descricao', 3600, 4, 0, 'Trigonometria', 11, 2, 4),
(34, '2023-11-16 17:39:01', '2023-11-16 19:39:01', 'descricao', 3600, 5, 0, 'Algebra', 11, 3, 2),

-- Biologia mês 9
(35, '2023-09-16 17:39:01', '2023-09-16 19:39:01', 'descricao', 3600, 5, 0, 'Logaritmo', 8, 1, 0),
(36, '2023-09-16 17:39:01', '2023-09-16 19:39:01', 'descricao', 3600, 4, 0, 'Trigonometria', 8, 2, 2),
(37, '2023-09-16 17:39:01', '2023-09-16 19:39:01', 'descricao', 3600, 5, 0, 'Algebra', 8, 3, 0),
(38, '2023-09-16 17:39:01', '2023-09-16 19:39:01', 'descricao', 3600, 2, 0, 'Equação 2º Grau', 8, 9, 2),

-- Biologia mês 10
(39, '2023-10-16 17:39:01', '2023-10-16 19:39:01', 'descricao', 3600, 5, 0, 'Logaritmo', 8, 1, 0),
(40, '2023-10-16 17:39:01', '2023-10-16 19:39:01', 'descricao', 3600, 4, 0, 'Trigonometria', 8, 2, 2),
(41, '2023-10-16 17:39:01', '2023-10-16 19:39:01', 'descricao', 3600, 5, 0, 'Algebra', 8, 3, 0),
(42, '2023-10-16 17:39:01', '2023-10-16 19:39:01', 'descricao', 3600, 2, 0, 'Equação 2º Grau', 8, 9, 2),
(43, '2023-10-16 17:39:01', '2023-10-16 19:39:01', 'descricao', 3600, 2, 0, 'Equação 2º Grau', 8, 9, 0),

-- Biologia mês 11
(44, '2023-11-16 17:39:01', '2023-11-16 19:39:01', 'descricao', 3600, 5, 0, 'Logaritmo', 8, 1, 2),
(45, '2023-11-16 17:39:01', '2023-11-16 19:39:01', 'descricao', 3600, 4, 0, 'Trigonometria', 8, 2, 0),
(46, '2023-11-16 17:39:01', '2023-11-16 19:39:01', 'descricao', 3600, 5, 0, 'Algebra', 8, 3, 2),

-- Geografia mês 9
(47, '2023-09-16 17:39:01', '2023-09-16 19:39:01', 'descricao', 3600, 5, 0, 'Logaritmo', 9, 1, 0),
(48, '2023-09-16 17:39:01', '2023-09-16 19:39:01', 'descricao', 3600, 4, 0, 'Trigonometria', 9, 2, 2),
(49, '2023-09-16 17:39:01', '2023-09-16 19:39:01', 'descricao', 3600, 5, 0, 'Algebra', 9, 2, 0),
(50, '2023-09-16 17:39:01', '2023-09-16 19:39:01', 'descricao', 3600, 5, 0, 'Logaritmo', 9, 1, 0),
(51, '2023-09-16 17:39:01', '2023-09-16 19:39:01', 'descricao', 3600, 4, 0, 'Trigonometria', 9, 2, 2),
(52, '2023-09-16 17:39:01', '2023-09-16 19:39:01', 'descricao', 3600, 5, 0, 'Algebra', 9, 3, 0),
(53, '2023-09-16 17:39:01', '2023-09-16 19:39:01', 'descricao', 3600, 2, 0, 'Equação 2º Grau', 9, 9, 2),

-- Historia mês 10
(54, '2023-10-16 17:39:01', '2023-10-16 19:39:01', 'descricao', 3600, 5, 0, 'Logaritmo', 9, 1, 0),
(55, '2023-10-16 17:39:01', '2023-10-16 19:39:01', 'descricao', 3600, 4, 0, 'Trigonometria', 9, 2, 2),
(56, '2023-10-16 17:39:01', '2023-10-16 19:39:01', 'descricao', 3600, 5, 0, 'Algebra', 9, 3, 0),
(57, '2023-10-16 17:39:01', '2023-10-16 19:39:01', 'descricao', 3600, 2, 0, 'Equação 2º Grau', 9, 9, 2),
(58, '2023-10-16 17:39:01', '2023-10-16 19:39:01', 'descricao', 3600, 2, 0, 'Equação 2º Grau', 9, 9, 0),

-- Historia mês 11
(59, '2023-11-16 17:39:01', '2023-11-16 19:39:01', 'descricao', 3600, 5, 0, 'Logaritmo', 9, 1, 2),
(60, '2023-11-16 17:39:01', '2023-11-16 19:39:01', 'descricao', 3600, 4, 0, 'Trigonometria', 9, 2, 0),
(61, '2023-11-16 17:39:01', '2023-11-16 19:39:01', 'descricao', 3600, 5, 0, 'Algebra', 9, 3, 2),

-- Aulas Hoje
(62, '2023-11-11 17:39:01', '2023-11-11 19:39:01', 'descricao', 3600, 5, 0, 'Algebra', 9, 3, 0),
(63, '2023-11-11 17:39:01', '2023-11-11 19:39:01', 'descricao', 3600, 4, 0, 'Trigonometria', 9, 2, 2),
(64, '2023-11-11 17:39:01', '2023-11-11 19:39:01', 'descricao', 3600, 5, 0, 'Logaritmo', 8, 1, 0),
(65, '2023-11-11 17:39:01', '2023-10-11 19:39:01', 'descricao', 3600, 5, 0, 'Logaritmo', 9, 1, 2),

-- Aulas Semana
(66, '2023-11-10 17:39:01', '2023-11-10 19:39:01', 'descricao', 3600, 5, 0, 'Algebra', 9, 3, 0),
(67, '2023-11-10 17:39:01', '2023-11-10 19:39:01', 'descricao', 3600, 4, 0, 'Trigonometria', 9, 2, 2),
(68, '2023-11-10 17:39:01', '2023-11-10 19:39:01', 'descricao', 3600, 5, 0, 'Algebra', 9, 3, 0),
(69, '2023-11-10 17:39:01', '2023-11-10 19:39:01', 'descricao', 3600, 5, 0, 'Logaritmo', 8, 1, 0),
(70, '2023-11-10 17:39:01', '2023-11-10 19:39:01', 'descricao', 3600, 5, 0, 'Logaritmo', 9, 1, 2)
ON CONFLICT (id) DO NOTHING;
SELECT setval('aula_id_seq', (SELECT MAX(id) FROM aula));

INSERT INTO formacao
(id, dt_inicio, dt_termino, instituicao, nome_curso, tipo_formacao, professor_usuario_id)
VALUES
(1, '2000-09-16', '2004-09-10', 'USP', 'Engenharia Aplicada', 1, 1),
(2, '1996-09-16', '1999-12-01', 'PUC-SP', 'Matemática', 0, 1);
SELECT setval('formacao_id_seq', (SELECT MAX(id) FROM formacao));
