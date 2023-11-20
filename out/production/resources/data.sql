INSERT INTO user (id, username, nome, cognome, email, avatar, telefono) VALUES
(1, 'test1', 'test', 'uno', 'test1@nerds.almaviva.it', 'http://nerds.almaviva.it/assets/img/test1.jpg', '111/11111'),
(2, 'test2', 'test', 'due', 'test2@nerds.almaviva.it', 'http://nerds.almaviva.it/assets/img/test2.jpg', '222/22222'),
(3, 'test3', 'test', 'tre', 'test3@nerds.almaviva.it', 'http://nerds.almaviva.it/assets/img/test3.jpg', '333/33333'),
(4, 'test4', 'test', 'quattro', 'test4@nerds.almaviva.it', 'http://nerds.almaviva.it/assets/img/test4.jpg', '444/44444');

-- INSERT INTO `intervento` (`id`,`descrizione`,`fine`,`inizio`,`tipo`,`utenti`) VALUES (1,'test allarme','2019-02-17 17:40:22','2019-02-17 17:39:57','ALLARME',?);
-- INSERT INTO `intervento` (`id`,`descrizione`,`fine`,`inizio`,`tipo`,`utenti`) VALUES (2,'test treno','2019-02-17 17:41:03','2019-02-17 17:40:49','TRENO',?);
-- --
-- --
-- INSERT INTO `intervento_user` (`id`,`assegnato`,`data_assegnazione`,`data_lettura`,`motivazione`,`user_id`,`intervento_id`) VALUES (1,'0',NULL,NULL,NULL,'admin@nerds.almaviva.it',1);
-- INSERT INTO `intervento_user` (`id`,`assegnato`,`data_assegnazione`,`data_lettura`,`motivazione`,`user_id`,`intervento_id`) VALUES (2,'0',NULL,'2019-02-17 17:40:03','test','test1@nerds.almaviva.it',1);
-- INSERT INTO `intervento_user` (`id`,`assegnato`,`data_assegnazione`,`data_lettura`,`motivazione`,`user_id`,`intervento_id`) VALUES (3,'1','2019-02-17 17:40:22','2019-02-17 17:40:22','test','test2@nerds.almaviva.it',1);
-- INSERT INTO `intervento_user` (`id`,`assegnato`,`data_assegnazione`,`data_lettura`,`motivazione`,`user_id`,`intervento_id`) VALUES (4,'0',NULL,'2019-02-17 17:41:18','test','test4@nerds.almaviva.it',2);
-- INSERT INTO `intervento_user` (`id`,`assegnato`,`data_assegnazione`,`data_lettura`,`motivazione`,`user_id`,`intervento_id`) VALUES (5,'1','2019-02-17 17:41:03','2019-02-17 17:41:03','test','test3@nerds.almaviva.it',2);
