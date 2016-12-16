CREATE TABLE role_info (
  id int(16) NOT NULL AUTO_INCREMENT,
  rolename varchar(64) NOT NULL COMMENT '角色名称',
  permission varchar(64) NOT NULL COMMENT '权限名称',
  PRIMARY KEY (id,rolename)
) ENGINE=InnoDB AUTO_INCREMENT DEFAULT CHARSET=utf8;