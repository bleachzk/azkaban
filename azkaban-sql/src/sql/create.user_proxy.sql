CREATE TABLE user_proxy (
  id int(16) NOT NULL AUTO_INCREMENT,
  userid int(16) NOT NULL COMMENT '用户id',
  proxyuserid int(16) NOT NULL COMMENT '代理用户id',
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT DEFAULT CHARSET=utf8;