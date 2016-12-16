CREATE TABLE user_info (
  id int(16) NOT NULL AUTO_INCREMENT,
  username varchar(64) NOT NULL COMMENT '用户名',
  password varchar(128) NOT NULL COMMENT '密码',
  email varchar(128) DEFAULT NULL COMMENT '邮箱',
  PRIMARY KEY (id,username)
) ENGINE=InnoDB AUTO_INCREMENT DEFAULT CHARSET=utf8;